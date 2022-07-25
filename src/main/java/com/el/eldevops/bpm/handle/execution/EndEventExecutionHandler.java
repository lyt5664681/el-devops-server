package com.el.eldevops.bpm.handle.execution;

import com.el.eldevops.bpm.handle.ExecutionHandler;
import com.el.eldevops.service.IPlaybookInstanceService;
import com.el.eldevops.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionEntity;
import org.camunda.bpm.engine.impl.pvm.runtime.ActivityInstanceState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * @author YunTao.Li
 * @description:
 * @date 2022/5/20 17:29
 */
@Slf4j
@Component
public class EndEventExecutionHandler implements ExecutionHandler {

    @Lazy
    @Autowired
    private IPlaybookInstanceService playbookInstanceService;

    @Override
    public void execute(DelegateExecution execution) {
        String eventName = execution.getEventName();

        if (ExecutionListener.EVENTNAME_END.equals(eventName)) { // 结束
            this.end(execution);
        }
    }

    @Override
    public void start(DelegateExecution execution) {

    }

    @Override
    public void end(DelegateExecution execution) {
        ExecutionEntity executionEntity = (ExecutionEntity) execution;

        // 判断事件步骤,不是开始步骤则直接返回不处理
        int activityState = executionEntity.getActivityInstanceState();
        if (!(ActivityInstanceState.DEFAULT.getStateCode() == activityState)) {
            return;
        }

        // step2 :获得流程信息
        String processInstID = execution.getProcessInstanceId();

        // step3 :判断是否结束
        boolean isEnd = executionEntity.isEnded();
        if (isEnd) {
            // 终止剧本实例
            playbookInstanceService.instStatusChange(processInstID, Constants.PLAYBOOK_INST_STATUS_FINISHED);
        }
    }

    @Override
    public void take(DelegateExecution execution) {

    }
}
