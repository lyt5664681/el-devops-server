package com.el.eldevops.bpm.executor.impl;

import com.el.eldevops.bpm.executor.ServiceExecutorInterface;
import com.el.eldevops.bpm.model.TaskExecuteVO;
import com.el.eldevops.model.ELServiceEntity;
import com.el.eldevops.model.ParamsInstanceEntity;
import com.el.eldevops.model.PlaybookDefineEntity;
import com.el.eldevops.util.Result;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author YunTao.Li
 * @description:
 * @date 2022/5/24 13:34
 */
@Component
public class PythonServiceExecutor implements ServiceExecutorInterface {
    @Override
    public Result execute(DelegateTask delegateTask, PlaybookDefineEntity playbookDefineEntity, ELServiceEntity serviceEntity) {
        return null;
    }

    @Override
    public Object call(List<ParamsInstanceEntity> soarParamsEntityList, ELServiceEntity serviceEntity, TaskExecuteVO taskExecuteVO) throws Exception {
        return null;
    }
//
//    @Lazy
//    @Autowired
//    private ISoarParamsService soarParamsService;
//
//    @Lazy
//    @Autowired
//    private ISoarParamsDefService soarParamsDefService;
//
//    @Lazy
//    @Autowired
//    private ISoarServiceOnActivityService iSoarServiceOnActivityService;
//
//    @Lazy
//    @Autowired
//    private IAppInstService appInstService;
//
//    @Resource
//    private SoarParamAdapter soarParamAdapter;
//
//    @Resource
//    private ISoarActivityInstService soarActivityInstService;
//
//    @Lazy
//    @Autowired
//    private IPlaybookInstService playbookInstService;


//    @Override
//    public Result execute(DelegateTask delegateTask, SoarPlaybookEntity playbook, SoarServiceEntity serviceEntity) {
//        Gson gson = new Gson();
//
//        //step1: 获得所有参数，包括剧本信息、活动信息、服务信息
//        DelegateExecution execution = delegateTask.getExecution();
//        ExecutionEntity executionEntity = (ExecutionEntity) execution;
//        String script = serviceEntity.getPythonScript();
//        String bookId = playbook.getBookId();
//        String activityId = executionEntity.getActivityId();
//        String activityInstId = executionEntity.getActivityInstanceId();
//        String serviceId = serviceEntity.getServiceId();
//        String processInstId = executionEntity.getProcessInstanceId();
//        String taskId = delegateTask.getId();
//
//
//        // step2 : 查询服务所绑定的参数列表
//        List<SoarParamsEntity> soarParamsEntityList = this.soarParamsService.listParams(bookId, activityId, serviceId);
//        List<ParamInstPOJO> paramInstPOJOList = new ArrayList<>();
//
//        // step2.1 : 查看服务是否绑定过应用实例
//        SoarServiceOnActivityEntity soarServiceOnActivityEntity = iSoarServiceOnActivityService.get(bookId, activityId);
//        String appInstId = soarServiceOnActivityEntity.getAppInstId();
//
//        // step2.2 : 设置相关参数
//        TaskExecuteVO taskExecuteVO = new TaskExecuteVO();
//        taskExecuteVO.setProcessInstId(processInstId);
//        taskExecuteVO.setAppInstId(appInstId);
//
//        try {
//            /***
//             * step3 : 对参数进行适配与赋值，并获得适配后的参数列表
//             * （这里要重复调用，因为call方法没办法把结构化以后的入参传递出来）
//             * step4 : 执行脚本
//             * step5 : 获得脚本执行结果
//             */
//            paramInstPOJOList = soarParamAdapter.paramAdapter(soarParamsEntityList, processInstId);
//            String result = this.call(soarParamsEntityList, serviceEntity, taskExecuteVO).toString();
//
//            // step6 : 解析执行结果设置出参
//            ParamInstPOJO outputParamInstPOJO = soarParamAdapter.outputParamAdapter(soarParamsEntityList, processInstId, result);
//            paramInstPOJOList.add(outputParamInstPOJO);
//
//            String paramJSONStr = gson.toJson(paramInstPOJOList);
//
//            // 根据taskid更新活动执行记录
//            soarActivityInstService.fillingByTaskId(taskId, serviceId, paramJSONStr);
//
//            return Result.succeed();
//        } catch (Exception e) {
//            String errorMessage = e.getMessage();
//            String paramJSONStr = gson.toJson(paramInstPOJOList);
//            // 记录活动执行错误记录
//            soarActivityInstService.feedBackError(taskId, serviceId, paramJSONStr, errorMessage);
//            // 剧本实例状态改为异常
//            playbookInstService.instStatusChange(processInstId, Constants.PLAYBOOK_INST_STATUS_EXCEPTION);
//            return Result.failed(errorMessage);
//        }
//    }
//
//    @Override
//    public Object call(List<SoarParamsEntity> soarParamsEntityList, SoarServiceEntity serviceEntity, TaskExecuteVO taskExecuteVO) throws Exception {
//        String processInstId = taskExecuteVO.getProcessInstId();
//        String appInstId = taskExecuteVO.getAppInstId();
//        String script = serviceEntity.getPythonScript();
//
//        // step1 : 对参数进行适配与赋值，并获得适配后的参数列表
//        List<ParamInstPOJO> paramInstPOJOList = soarParamAdapter.paramAdapter(soarParamsEntityList, processInstId);
//
//        // step2 : 获得应用实例参数
//        Map<String, String> appInstParams = new HashMap();
//        if (StringUtils.isNotBlank(appInstId)) {
//            SoarAppInstEntity appInstEntity = appInstService.get(appInstId);
//            String deviceIp = appInstEntity.getDeviceIp();
//            appInstParams.put("device_ip", deviceIp);
//
//            List<SoarParamsDefEntity> params = soarParamsDefService.listByAppInst(appInstId);
//            for (SoarParamsDefEntity param : params) {
//                String name = param.getParamName();
//                String value = param.getParamValue();
//                appInstParams.put(name, value);
//            }
//        }
//
//        // step3 : 执行脚本
//        String result = PythonExecutorUtil.scriptExecute(script, paramInstPOJOList, appInstParams);
//        return result;
//    }
}
