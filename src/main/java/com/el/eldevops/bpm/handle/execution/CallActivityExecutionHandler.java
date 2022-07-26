package com.el.eldevops.bpm.handle.execution;

import com.el.eldevops.bpm.handle.ExecutionHandler;
import com.el.eldevops.config.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;

/**
 * @author YunTao.Li
 * @description:
 * @date 2022/7/15 16:16
 */
@Slf4j
@Component
public class CallActivityExecutionHandler implements ExecutionHandler {
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
        execution.getVariables();
    }

    @Override
    public void end(DelegateExecution execution) {
        execution.getVariables();
    }

    @Override
    public void take(DelegateExecution execution) {

    }
}
