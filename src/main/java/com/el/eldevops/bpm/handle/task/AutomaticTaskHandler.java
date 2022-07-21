package com.el.eldevops.bpm.handle.task;

import com.central.common.model.Result;
import com.central.msargus.soar.impl.bpm.executor.SoarServiceExecutor;
import com.central.msargus.soar.impl.bpm.executor.SoarServiceExecutorFactory;
import com.central.msargus.soar.impl.model.SoarPlaybookEntity;
import com.central.msargus.soar.impl.model.SoarServiceEntity;
import com.central.msargus.soar.impl.service.IPlaybookService;
import com.central.msargus.soar.impl.service.ISoarActivityInstService;
import com.central.msargus.soar.impl.service.ISoarServiceService;
import com.central.msargus.soar.impl.util.SpringContextUtils;
import com.el.eldevops.bpm.handle.AbstractTaskHandler;
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
    private SoarServiceExecutorFactory soarServiceExecutorFactory;

    @Lazy
    @Autowired
    private RuntimeService runtimeService;

    @Lazy
    @Autowired
    private ISoarActivityInstService soarActivityInstService;


    @Override
    public void execute(DelegateTask delegateTask) {
        switch (delegateTask.getEventName()) {
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

    @Override
    public void create(DelegateTask delegateTask) {
        DelegateExecution execution = delegateTask.getExecution();
        String taskId = delegateTask.getId();
        String activityId = execution.getCurrentActivityId();
        String activityInstId = execution.getActivityInstanceId();
        String activityName = execution.getCurrentActivityName();
        String processInstID = delegateTask.getProcessInstanceId();

        // 增加活动记录信息
        soarActivityInstService.add(processInstID, activityId, activityInstId, activityName, taskId);
    }

    @Override
    public void assignment(DelegateTask delegateTask) {
        // step1 : 获得当前节点信息,包括获得流程信息
        DelegateExecution execution = delegateTask.getExecution();
        String processInstID = delegateTask.getProcessInstanceId();
        String processDefId = delegateTask.getProcessDefinitionId();
        String activityId = execution.getCurrentActivityId();

        // step2 : 查询剧本信息，查询当前节点关联的服务信息
        IPlaybookService playbookService = SpringContextUtils.getBean("playbookServiceImpl");
        SoarPlaybookEntity playbook = playbookService.getPlaybookByProcessDefId(processDefId);
        String bookId = playbook.getBookId();
        // 查询服务信息,此处查出的服务信息应该是唯一的
        ISoarServiceService soarServiceService = SpringContextUtils.getBean("soarServiceServiceImpl");
        List<SoarServiceEntity> soarServiceEntities = soarServiceService.getActivityService(bookId, activityId);

        if (soarServiceEntities.size() <= 0) {
            // 未查询到绑定的服务
            log.info("=====================未查询到绑定的服务=====================");
            delegateTask.complete();
            return;
//            throw new BusinessException("未查询到绑定的服务");
        }

        // step3 : 调用服务
        SoarServiceEntity serviceEntity = soarServiceService.get(soarServiceEntities.get(0).getServiceId());
        String protocal = serviceEntity.getProtocol();
        SoarServiceExecutor serviceExecutor = soarServiceExecutorFactory.newInstance(protocal);
        Result result = serviceExecutor.execute(delegateTask, playbook, serviceEntity);

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
