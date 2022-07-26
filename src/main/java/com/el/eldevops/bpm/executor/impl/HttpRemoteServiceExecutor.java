package com.el.eldevops.bpm.executor.impl;

import com.el.eldevops.bpm.executor.ServiceExecutorInterface;
import com.el.eldevops.bpm.model.TaskExecuteVO;
import com.el.eldevops.model.ELServiceEntity;
import com.el.eldevops.model.ParamsInstanceEntity;
import com.el.eldevops.model.PlaybookDefineEntity;
import com.el.eldevops.util.Result;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author YunTao.Li
 * @description:
 * @date 2022/5/24 13:38
 */
@Component
public class HttpRemoteServiceExecutor implements ServiceExecutorInterface {
    @Override
    public Result execute(DelegateTask delegateTask, PlaybookDefineEntity playbookDefineEntity, ELServiceEntity serviceEntity) {
        return null;
    }

    @Override
    public Object call(List<ParamsInstanceEntity> soarParamsEntityList, ELServiceEntity serviceEntity, TaskExecuteVO taskExecuteVO) throws Exception {
        return null;
    }
}
