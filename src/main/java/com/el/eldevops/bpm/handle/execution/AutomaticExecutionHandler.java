package com.el.eldevops.bpm.handle.execution;

import com.central.msargus.soar.impl.bpm.executor.SoarServiceExecutorFactory;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author YunTao.Li
 * @description:
 * @date 2022/5/20 17:57
 */
@Slf4j
@Component
public class AutomaticExecutionHandler implements ExecutionHandler {

    @Autowired
    private SoarServiceExecutorFactory soarServiceExecutorFactory;

    @Override
    public void execute(DelegateExecution execution) {
        String eventName = execution.getEventName();

        switch (eventName) {
            case ExecutionListener.EVENTNAME_START:
                start(execution);
                break;
            case ExecutionListener.EVENTNAME_END:
                end(execution);
                break;
            case ExecutionListener.EVENTNAME_TAKE:
                take(execution);
                break;
            default:
                //throw new BusinessException("eventName is not supported");
                break;
        }
    }
    
    @Override
    public void start(DelegateExecution execution) {
    }

    @Override
    public void end(DelegateExecution execution) {
    }

    @Override
    public void take(DelegateExecution execution) {

    }


}
