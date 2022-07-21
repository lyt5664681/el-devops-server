package com.el.eldevops.bpm.executor.impl;

import com.central.common.model.Result;
import com.central.msargus.soar.impl.bpm.adapter.SoarParamAdapter;
import com.central.msargus.soar.impl.bpm.model.TaskExecuteVO;
import com.central.msargus.soar.impl.model.SoarParamsEntity;
import com.central.msargus.soar.impl.model.SoarPlaybookEntity;
import com.central.msargus.soar.impl.model.SoarServiceEntity;
import com.central.msargus.soar.impl.model.pojo.ParamInstPOJO;
import com.central.msargus.soar.impl.service.IPlaybookInstService;
import com.central.msargus.soar.impl.service.ISoarActivityInstService;
import com.central.msargus.soar.impl.service.ISoarParamsService;
import com.central.msargus.soar.impl.util.Constants;
import com.el.eldevops.bpm.executor.ServiceExecutorInterface;
import com.el.eldevops.util.Result;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author YunTao.Li
 * @description:
 * @date 2022/5/24 13:36
 */
@Component
public class BuiltInJavaServiceExecutor implements ServiceExecutorInterface {

    @Autowired
    private ApplicationContext applicationContext;

    @Lazy
    @Autowired
    private ISoarParamsService soarParamsService;

    @Resource
    private SoarParamAdapter soarParamAdapter;

    @Resource
    private ISoarActivityInstService soarActivityInstService;

    @Lazy
    @Autowired
    private IPlaybookInstService playbookInstService;

    @Override
    public Result execute(DelegateTask delegateTask, SoarPlaybookEntity playbook, SoarServiceEntity serviceEntity) {
        Gson gson = new Gson();

        //step1: 获得所有参数，包括剧本信息、活动信息、服务信息
        DelegateExecution execution = delegateTask.getExecution();
        ExecutionEntity executionEntity = (ExecutionEntity) execution;
        String script = serviceEntity.getPythonScript();
        String bookId = playbook.getBookId();
        String activityId = executionEntity.getActivityId();
        String activityInstId = executionEntity.getActivityInstanceId();
        String serviceId = serviceEntity.getServiceId();
        String processInstId = executionEntity.getProcessInstanceId();
        String serviceUrl = serviceEntity.getUrl();
        String method = serviceEntity.getMethod();
        String taskId = delegateTask.getId();


        // step2 : 查询服务所绑定的参数列表
        List<SoarParamsEntity> soarParamsEntityList = this.soarParamsService.listParams(bookId, activityId, serviceId);
        List<ParamInstPOJO> paramInstPOJOList = new ArrayList<>();

        // step2.2 : 设置相关参数
        TaskExecuteVO taskExecuteVO = new TaskExecuteVO();
        taskExecuteVO.setProcessInstId(processInstId);
        try {

            /***
             * step3 : 对参数进行适配与赋值，并获得适配后的参数列表
             * （这里要重复调用，因为call方法没办法把结构化以后的入参传递出来）
             * step4 : 执行脚本
             * step5 : 获得脚本执行结果
             */
            paramInstPOJOList = soarParamAdapter.paramAdapter(soarParamsEntityList, processInstId);
            Object result = this.call(soarParamsEntityList, serviceEntity, taskExecuteVO);

            // step5 : 拿到执行结果设置出参
            ParamInstPOJO outputParamInstPOJO = soarParamAdapter.outputParamAdapter(soarParamsEntityList, processInstId, result);
            paramInstPOJOList.add(outputParamInstPOJO);

            String paramJSONStr = gson.toJson(paramInstPOJOList);
            soarActivityInstService.fillingByTaskId(taskId, serviceId, paramJSONStr);

            return Result.succeed();
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            try {
                if (StringUtils.isBlank(errorMessage)) {
                    errorMessage = e.getCause().getMessage();
                }
            } catch (Exception e2) {
            }
            String paramJSONStr = gson.toJson(paramInstPOJOList);
            soarActivityInstService.feedBackError(taskId, serviceId, paramJSONStr, errorMessage);
//            soarActivityInstService.add(activityInstId, processInstId, errorMessage, Constants.ACTIVITY_INST_STATUS_ERROR, serviceId, paramJSONStr);
            // 剧本实例状态改为异常
            playbookInstService.instStatusChange(processInstId, Constants.PLAYBOOK_INST_STATUS_EXCEPTION);
            return Result.failed(errorMessage);
        }
    }

    /**
     * @param soarParamsEntityList 服务参数列表
     * @param serviceEntity        服务实体
     * @param taskExecuteVO        任务参数
     * @return java.lang.Object
     * @description 调用服务
     * @author YunTao.Li
     * @date 2022/6/7 10:46
     */
    @Override
    public Object call(List<SoarParamsEntity> soarParamsEntityList, SoarServiceEntity serviceEntity, TaskExecuteVO taskExecuteVO) throws Exception {
        String serviceId = serviceEntity.getServiceId();
        String serviceUrl = serviceEntity.getUrl();
        String method = serviceEntity.getMethod();
        String processInstId = taskExecuteVO.getProcessInstId();

        // step3 : 对参数进行适配与赋值，并获得适配后的参数列表
        List<ParamInstPOJO> paramInstPOJOList = soarParamAdapter.paramAdapter(soarParamsEntityList, processInstId);

        if ("bannedIP".equals(method) || "bannedIPByTime".equals(method)) {
            ParamInstPOJO tempPOJO = new ParamInstPOJO();
            tempPOJO.setParamValue(String.valueOf(processInstId));
            paramInstPOJOList.add(tempPOJO);
        }

        Object[] inputParamsValues = paramInstPOJOList.stream().map(ParamInstPOJO::getParamValue).collect(Collectors.toList()).toArray();
        Class[] inputParamClasses = new Class[paramInstPOJOList.size()];
        for (int i = 0; i < paramInstPOJOList.size(); i++) {
            ParamInstPOJO paramInstPOJO = paramInstPOJOList.get(i);
            String paramType = paramInstPOJO.getDataType();
            if (Constants.PARAMS_DATA_TYPE_INTEGER.equalsIgnoreCase(paramType)) {
                inputParamClasses[i] = Integer.class;
                inputParamsValues[i] = Integer.parseInt(inputParamsValues[i].toString());
            } else if (Constants.PARAMS_DATA_TYPE_BOOLEAN.equalsIgnoreCase(paramType)) {
                inputParamClasses[i] = Boolean.class;
                inputParamsValues[i] = Boolean.parseBoolean(inputParamsValues[i].toString());
            } else {
                inputParamClasses[i] = String.class;
            }
        }

        Class cls1 = Class.forName(serviceUrl);
        Object bean = applicationContext.getBean(cls1);
        // 额外的属性注入，将serviceId也注入到方法里去，如果类上没有相关set方法说明业务constructor不需要serviceId
        try {
            Method setServiceMethod = cls1.getDeclaredMethod("setServiceId", String.class);
            setServiceMethod.invoke(bean, serviceId);
        } catch (Exception e1) {
            // do nothing 额外的属性注入 没有也没关系
        }

        Method m = cls1.getDeclaredMethod(method, inputParamClasses); //传类型
        Object result = m.invoke(bean, inputParamsValues); //传值

        return result;
    }
}
