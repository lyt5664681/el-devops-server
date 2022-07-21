package com.el.eldevops.bpm.handle.execution;

import com.central.common.exception.BusinessException;
import com.central.msargus.soar.impl.model.SoarParamsDefEntity;
import com.central.msargus.soar.impl.model.SoarPlaybookEntity;
import com.central.msargus.soar.impl.service.IPlaybookInstService;
import com.central.msargus.soar.impl.service.IPlaybookService;
import com.central.msargus.soar.impl.service.ISoarParamsDefService;
import com.central.msargus.soar.impl.service.ISoarParamsService;
import com.central.msargus.soar.impl.util.ParamsUtil;
import com.central.msargus.soar.impl.util.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionEntity;
import org.camunda.bpm.engine.impl.pvm.runtime.ActivityInstanceState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author YunTao.Li
 * @description:
 * @date 2022/5/20 14:54
 */
@Slf4j
@Component
public class StartEventExecutionHandler implements ExecutionHandler {

    @Lazy
    @Autowired
    private RuntimeService runtimeService;

    @Lazy
    @Autowired
    private IPlaybookService playbookService;

    @Lazy
    @Autowired
    private ISoarParamsService soarParamsService;

    @Lazy
    @Autowired
    private ISoarParamsDefService soarParamsDefService;

    @Override
    public void execute(DelegateExecution execution) {
        String eventName = execution.getEventName();

        if (ExecutionListener.EVENTNAME_START.equals(eventName)) { // 开始
            this.start(execution);
        } else if (ExecutionListener.EVENTNAME_END.equals(eventName)) { // 结束

        } else if (ExecutionListener.EVENTNAME_TAKE.equals(eventName)) { // task

        } else {
            throw new BusinessException("不支持的事件名称：" + eventName);
        }
    }


    /**
     * @param execution
     * @return void
     * @description 事件开始动作，start
     * @author YunTao.Li
     * @date 2022/5/20 16:32
     */
    @Override
    public void start(DelegateExecution execution) {

        ExecutionEntity executionEntity = (ExecutionEntity) execution;

        // step1 : 判断excution状态,如果不是开始状态，则不执行
        int activityState = executionEntity.getActivityInstanceState();
        if (!(ActivityInstanceState.STARTING.getStateCode() == activityState)) {
            return;
        }

        // step2 :获得流程信息
        String processInstID = execution.getProcessInstanceId();
        String processDefId = execution.getProcessDefinitionId();
        String superExcutionId = executionEntity.getSuperExecutionId();

        // step3 :获得剧本信息
        SoarPlaybookEntity playbook = playbookService.getPlaybookByProcessDefId(processDefId);
        String bookId = playbook.getBookId();
        int bookType = playbook.getBookType();
        String bookName = playbook.getBookName();
        String bookXML = playbook.getBookXml();
        String bookJson = playbook.getBookJson();

        // step4 : 处理子流程
        String rootProcessInstId = "";
        String superProcessInstId = "";
        if (StringUtils.isNotBlank(superExcutionId)) { // 有子流程
            rootProcessInstId = executionEntity.getRootProcessInstanceId();

            superProcessInstId = executionEntity.getSuperExecution().getProcessInstanceId();

            // 获得剧本参数
            List<SoarParamsDefEntity> soarParamsEntities = soarParamsDefService.listGlobalParamsByBookId(bookId);
            // 定义流程参数
            Map<String, Object> processParams = new HashMap<>();
            ParamsUtil paramsUtil = new ParamsUtil();
            for (SoarParamsDefEntity param : soarParamsEntities) {
                String paramName = param.getParamName();
                Object paramValue = param.getParamValue();
                // 处理参数，上层参数通过name传递，以value作为变量储存在变量池中
                Object variable = execution.getProcessInstance().getVariable(paramName);
                String variableKey = paramsUtil.paramConvert(paramValue.toString());
                processParams.put(variableKey, variable);
            }
            execution.getProcessInstance().setVariables(processParams);
        }

        // step5 : 创建剧本实例
        Map<String, Object> variables = execution.getVariables();
        String createBy = (String) Optional.ofNullable(variables.get("create_by")).orElse("");
        String execWay = (String) Optional.ofNullable(variables.get("exec_way")).orElse("规则触发");

        IPlaybookInstService playbookInstService = SpringContextUtils.getBean("playbookInstServiceImpl");
        playbookInstService.createPlayBookInst(bookId, processInstID, rootProcessInstId, bookName, bookType, bookXML, bookJson, createBy, execWay);
        log.info("创建剧本实例成功，processInstID={},bookId={},bookName={}", processInstID, bookId, bookName);
    }

    @Override
    public void end(DelegateExecution execution) {

    }

    @Override
    public void take(DelegateExecution execution) {

    }

}
