package com.el.eldevops.bpm.handle.execution;

import com.central.common.exception.BusinessException;
import com.central.msargus.soar.impl.service.ISoarActivityInstService;
import com.el.eldevops.bpm.handle.ExecutionHandler;
import com.el.eldevops.config.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * @author YunTao.Li
 * @description:
 * @date 2022/7/4 14:55
 */
@Slf4j
@Component
public class ExclusiveGatewayExecutionHandler implements ExecutionHandler {
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


    /**
     * @param execution
     * @return void
     * @description
     * @author YunTao.Li
     * @date 2022/5/20 16:32
     */
    @Override
    public void start(DelegateExecution execution) {

    }

    @Override
    public void end(DelegateExecution execution) {
        String processInstID = execution.getProcessInstanceId();
        String activityId = execution.getCurrentActivityId();
        String activityInstId = execution.getActivityInstanceId();
        String activityName = execution.getCurrentActivityName();
        String executionId = execution.getId();
        soarActivityInstService.add(processInstID, activityId, activityInstId, activityName, executionId);

        // 如果需要条件数据则这样取
//        ((TransitionImpl) ((ExecutionEntityReferencer) ((ExecutionEntity) execution).variableStore.observers.get(0)).execution.transitionsToTake.get(0)).properties.properties.get("conditionText").toString();
    }

    @Override
    public void take(DelegateExecution execution) {

    }
}
