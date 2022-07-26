package com.el.eldevops.bpm.listener;

import com.el.eldevops.bpm.handle.task.AutomaticTaskHandler;
import com.el.eldevops.bpm.handle.task.UserTaskHandler;
import com.el.eldevops.service.IPlaybookInstanceService;
import com.el.eldevops.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.impl.persistence.entity.TaskEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author YunTao.Li
 * @description:
 * @date 2022/5/24 9:22
 */
@Slf4j
@Component
public class GlobalTaskListener implements TaskListener {

    @Resource
    private AutomaticTaskHandler automaticTaskHandler;

    @Resource
    private UserTaskHandler userTaskHandler;

    @Lazy
    @Autowired
    private IPlaybookInstanceService playbookInstanceService;

    @Override
    public void notify(DelegateTask delegateTask) {
        log.info("delegateTask------------------> " + delegateTask.getName() + "----------->" + delegateTask.getEventName() + "----->" + delegateTask.getBpmnModelElementInstance().getElementType().getTypeName() + "--->");

        String processInstanceId = delegateTask.getProcessInstanceId();
        String eventName = delegateTask.getEventName();
        String assignee = delegateTask.getAssignee();
        String currentActivityName = delegateTask.getExecution().getCurrentActivityName();
        /**
         * 所有任务都用usertask来做，如果assignee是AUTOMATIC则认为是自动任务，用automaticTaskHandler处理
         * */
        if (Constants.TASK_EVENT_TYPE_AUTOMATIC.equals(assignee)) { // 自动任务
            try {
                Thread.sleep(50);
            } catch (Exception e) {

            }
            automaticTaskHandler.execute(delegateTask);
        } else { // 人工任务
            userTaskHandler.execute(delegateTask);
        }
    }
}
