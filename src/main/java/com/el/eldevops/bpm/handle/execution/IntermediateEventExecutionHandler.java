package com.el.eldevops.bpm.handle.execution;

import com.central.common.exception.BusinessException;
import com.central.msargus.soar.impl.service.ISoarActivityInstService;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * @author YunTao.Li
 * @description: 延迟
 * @date 2022/7/5 15:47
 */
@Slf4j
@Component
public class IntermediateEventExecutionHandler implements ExecutionHandler {
    @Lazy
    @Autowired
    private ISoarActivityInstService soarActivityInstService;

    @Override
    public void execute(DelegateExecution execution) {
        String eventName = execution.getEventName();

        try {
            if (ExecutionListener.EVENTNAME_START.equals(eventName)) { // 开始
                this.start(execution);
            } else if (ExecutionListener.EVENTNAME_END.equals(eventName)) { // 结束
                this.end(execution);
            } else if (ExecutionListener.EVENTNAME_TAKE.equals(eventName)) { // task

            } else {
                throw new BusinessException("不支持的事件名称：" + eventName);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void start(DelegateExecution execution) {
        execution.getProcessInstanceId();
        String processInstID = execution.getProcessInstanceId();
        String activityId = execution.getCurrentActivityId();
        String activityInstId = execution.getActivityInstanceId();
        String activityName = execution.getCurrentActivityName();
        String executionId = execution.getId();
        soarActivityInstService.add(processInstID, activityId, activityInstId, activityName, executionId, "开始");
    }

    @Override
    public void end(DelegateExecution execution) {
        execution.getVariableScopeKey();
        String processInstID = execution.getProcessInstanceId();
        String activityId = execution.getCurrentActivityId();
        String activityInstId = execution.getActivityInstanceId();
        String activityName = execution.getCurrentActivityName();
        String executionId = execution.getId();
        soarActivityInstService.add(processInstID, activityId, activityInstId, activityName, executionId, "结束");
    }

    @Override
    public void take(DelegateExecution execution) {

    }
}
