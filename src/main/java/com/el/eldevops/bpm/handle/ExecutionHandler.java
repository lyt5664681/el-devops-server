package com.el.eldevops.bpm.handle;

import org.camunda.bpm.engine.delegate.DelegateExecution;

/**
 * @author YunTao.Li
 * @description:
 * @date 2022/5/20 14:54
 */
public interface ExecutionHandler {
    public void execute(DelegateExecution execution);

    public void start(DelegateExecution execution);

    public void end(DelegateExecution execution);

    public void take(DelegateExecution execution);

}
