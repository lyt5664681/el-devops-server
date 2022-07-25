package com.el.eldevops.bpm.handle;

import org.camunda.bpm.engine.delegate.DelegateTask;

/**
 * @author YunTao.Li
 * @description:
 * @date 2022/7/20 13:58
 */
public abstract class AbstractTaskHandler {

    public void execute(DelegateTask delegateTask) {
        String eventName = delegateTask.getEventName();

        switch (eventName) {
            case "create":
                create(delegateTask);
                break;
            case "assignment":
                assignment(delegateTask);
                break;
            case "complete":
                complete(delegateTask);
                break;
            default:
                break;
        }
    }

    public void create(DelegateTask delegateTask) {
    }

    public void assignment(DelegateTask delegateTask) {
    }

    public void complete(DelegateTask delegateTask) {
    }
}
