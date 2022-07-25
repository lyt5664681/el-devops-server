package com.el.eldevops.bpm.executor;

import com.el.eldevops.model.ELServiceEntity;
import com.el.eldevops.model.PlaybookDefineEntity;
import com.el.eldevops.util.Result;
import org.camunda.bpm.engine.delegate.DelegateTask;

/**
 * @author YunTao.Li
 * @description:
 * @date 2022/7/20 15:37
 */
public interface ServiceExecutorInterface {
    public Result execute(DelegateTask delegateTask, PlaybookDefineEntity playbookDefineEntity, ELServiceEntity serviceEntity);

    public Object call(List<SoarParamsEntity> soarParamsEntityList, SoarServiceEntity serviceEntity, TaskExecuteVO taskExecuteVO) throws Exception;
}
