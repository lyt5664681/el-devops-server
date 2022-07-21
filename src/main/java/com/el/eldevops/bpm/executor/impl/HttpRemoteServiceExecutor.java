package com.el.eldevops.bpm.executor.impl;

import com.central.common.model.Result;
import com.central.msargus.soar.impl.bpm.model.TaskExecuteVO;
import com.central.msargus.soar.impl.model.SoarParamsEntity;
import com.central.msargus.soar.impl.model.SoarPlaybookEntity;
import com.central.msargus.soar.impl.model.SoarServiceEntity;
import com.el.eldevops.bpm.executor.ServiceExecutorInterface;
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
    public Result execute(DelegateTask delegateTask, SoarPlaybookEntity playbook, SoarServiceEntity serviceEntity) {
        return Result.succeed();
    }

    @Override
    public Object call(List<SoarParamsEntity> soarParamsEntityList, SoarServiceEntity serviceEntity, TaskExecuteVO taskExecuteVO) throws Exception {
        return null;
    }
}
