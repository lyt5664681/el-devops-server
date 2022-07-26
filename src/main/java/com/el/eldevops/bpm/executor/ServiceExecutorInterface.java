package com.el.eldevops.bpm.executor;

import com.el.eldevops.bpm.model.TaskExecuteVO;
import com.el.eldevops.model.ELServiceEntity;
import com.el.eldevops.model.ParamsInstanceEntity;
import com.el.eldevops.model.PlaybookDefineEntity;
import com.el.eldevops.util.Result;
import org.camunda.bpm.engine.delegate.DelegateTask;

import java.util.List;

/**
 * @author YunTao.Li
 * @description:
 * @date 2022/7/20 15:37
 */
public interface ServiceExecutorInterface {
    public Result execute(DelegateTask delegateTask, PlaybookDefineEntity playbookDefineEntity, ELServiceEntity serviceEntity);

    public Object call(List<ParamsInstanceEntity> soarParamsEntityList, ELServiceEntity serviceEntity, TaskExecuteVO taskExecuteVO) throws Exception;
}
