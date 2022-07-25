package com.el.eldevops.bpm.handle.task;

import com.el.eldevops.bpm.executor.ServiceExecutorFactory;
import com.el.eldevops.bpm.executor.ServiceExecutorInterface;
import com.el.eldevops.bpm.handle.AbstractTaskHandler;
import com.el.eldevops.model.ELServiceEntity;
import com.el.eldevops.model.PlaybookDefineEntity;
import com.el.eldevops.service.IELServiceService;
import com.el.eldevops.service.IExecutionRecordService;
import com.el.eldevops.service.IPlaybookDefineService;
import com.el.eldevops.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.runtime.ProcessInstanceQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author YunTao.Li
 * @description:
 * @date 2022/5/25 10:19
 */
@Slf4j
@Component
public class AutomaticTaskHandler extends AbstractTaskHandler {

    @Autowired
    private ServiceExecutorFactory executorFactory;

    @Lazy
    @Autowired
    private IExecutionRecordService executionRecordService;

    @Lazy
    @Autowired
    private IPlaybookDefineService playbookDefineService;

    @Lazy
    @Autowired
    private IELServiceService elServiceService;

    @Lazy
    @Autowired
    private RuntimeService runtimeService;

    @Override
    public void create(DelegateTask delegateTask) {
        DelegateExecution execution = delegateTask.getExecution();
        String taskId = delegateTask.getId();
        String activityId = execution.getCurrentActivityId();
        String activityInstId = execution.getActivityInstanceId();
        String activityName = execution.getCurrentActivityName();
        String processInstID = delegateTask.getProcessInstanceId();

        // 增加活动记录信息
        executionRecordService.record(processInstID, activityInstId, taskId, null);
    }

    @Override
    public void assignment(DelegateTask delegateTask) {
        // step1 : 获得当前节点信息,包括获得流程信息
        DelegateExecution execution = delegateTask.getExecution();
        String processInstID = delegateTask.getProcessInstanceId();
        String processDefId = delegateTask.getProcessDefinitionId();
        String activityId = execution.getCurrentActivityId();

        // step2 : 查询剧本信息，查询当前节点关联的服务信息
        PlaybookDefineEntity playbookDefineEntity = playbookDefineService.getPlaybookDefineByProcessDefId(processDefId);
        String bookDefId = playbookDefineEntity.getBookDefId();
        // 查询服务信息,此处查出的服务信息应该是唯一的
        List<ELServiceEntity> serviceEntities = elServiceService.getActivityService(bookDefId, activityId);

        if (serviceEntities.size() <= 0) {
            log.info("=====================未查询到绑定的服务=====================");
            delegateTask.complete();
            return;
        }

        // step3 : 调用服务
        ELServiceEntity serviceEntity = serviceEntities.get(0);
        String protocal = serviceEntity.getProtocol();
        ServiceExecutorInterface serviceExecutor = executorFactory.newInstance(protocal);
        Result result = serviceExecutor.execute(delegateTask, playbookDefineEntity, serviceEntity);

        Integer resCode = result.getResp_code();
        if (resCode == 0) { // 成功,完成工作项
            delegateTask.complete();
        } else {
            // 挂起流程
            ProcessInstanceQuery processInstanceQuery = runtimeService.createProcessInstanceQuery();
            processInstanceQuery.processInstanceId(processInstID);
            runtimeService.updateProcessInstanceSuspensionState().byProcessInstanceQuery(processInstanceQuery).suspendAsync();
        }
    }

    @Override
    public void complete(DelegateTask delegateTask) {
    }
}
