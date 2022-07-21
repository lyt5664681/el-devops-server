package com.el.eldevops.bpm.handle.task;

import com.central.msargus.soar.impl.service.IPlaybookInstService;
import com.central.msargus.soar.impl.service.ISoarActivityInstService;
import com.el.eldevops.bpm.handle.AbstractTaskHandler;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * @author YunTao.Li
 * @description:
 * @date 2022/5/25 10:19
 */
@Component
public class UserTaskHandler extends AbstractTaskHandler {

    @Lazy
    @Autowired
    private ISoarActivityInstService soarActivityInstService;

    @Lazy
    @Autowired
    private IPlaybookInstService playbookInstService;

    @Override
    public void create(DelegateTask delegateTask) {
        delegateTask.getAssignee();

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
        String taskId = delegateTask.getId();
        String assignee = delegateTask.getAssignee();
        soarActivityInstService.entrustByTaskId(taskId, assignee); // 增加环节处理人
    }

    @Override
    public void complete(DelegateTask delegateTask) {
        // TODO: 待办转已办
        // TODO: 增加执行(审批)记录
        // TODO: 修改流程实例状态
        // TODO: 删除待办任务
    }
}
