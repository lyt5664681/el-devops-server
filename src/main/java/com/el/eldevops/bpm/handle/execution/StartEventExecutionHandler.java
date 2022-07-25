package com.el.eldevops.bpm.handle.execution;

import com.el.eldevops.bpm.handle.ExecutionHandler;
import com.el.eldevops.config.exception.BusinessException;
import com.el.eldevops.model.PlaybookDefineEntity;
import com.el.eldevops.model.PlaybookInstEntity;
import com.el.eldevops.service.IPlaybookDefineService;
import com.el.eldevops.service.IPlaybookInstanceService;
import com.el.eldevops.util.Constants;
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
    private IPlaybookDefineService playbookDefineService;

    @Lazy
    @Autowired
    private IPlaybookInstanceService playbookInstanceService;

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
        PlaybookDefineEntity playbookDefine = playbookDefineService.getPlaybookDefineByProcessDefId(processDefId);
        String bookDefId = playbookDefine.getBookDefId();
        String bookName = playbookDefine.getBookName();

        // step4 : 处理子流程
        String rootProcessInstId = "";
        String superProcessInstId = "";
        if (StringUtils.isNotBlank(superExcutionId)) { // 有子流程
            rootProcessInstId = executionEntity.getRootProcessInstanceId();

            superProcessInstId = executionEntity.getSuperExecution().getProcessInstanceId();

//            // 获得剧本参数
//            List<SoarParamsDefEntity> soarParamsEntities = soarParamsDefService.listGlobalParamsByBookId(bookId);
//            // 定义流程参数
//            Map<String, Object> processParams = new HashMap<>();
//            ParamsUtil paramsUtil = new ParamsUtil();
//            for (SoarParamsDefEntity param : soarParamsEntities) {
//                String paramName = param.getParamName();
//                Object paramValue = param.getParamValue();
//                // 处理参数，上层参数通过name传递，以value作为变量储存在变量池中
//                Object variable = execution.getProcessInstance().getVariable(paramName);
//                String variableKey = paramsUtil.paramConvert(paramValue.toString());
//                processParams.put(variableKey, variable);
//            }
//            execution.getProcessInstance().setVariables(processParams);
        }

        // step5 : 创建剧本实例
        Map<String, Object> variables = execution.getVariables();
        String bookInstName = (String) Optional.ofNullable(variables.get("bookInstName")).orElse("");

        PlaybookInstEntity playbookInstEntity = new PlaybookInstEntity();
        playbookInstEntity.setProcessInstId(processInstID);
        playbookInstEntity.setBookDefName(bookName);
        playbookInstEntity.setBookInstName(bookInstName);
        playbookInstEntity.setBookDefId(bookDefId);
        playbookInstEntity.setStatus(Constants.PLAYBOOK_INST_STATUS_RUNNING);
        playbookInstanceService.insert(playbookInstEntity);
        log.info("创建剧本实例成功，processInstID={},bookId={},bookName={}", processInstID, bookDefId, bookName);
    }

    @Override
    public void end(DelegateExecution execution) {

    }

    @Override
    public void take(DelegateExecution execution) {

    }

}
