package com.el.eldevops.bpm.handle.execution;

import com.central.msargus.soar.impl.service.IPlaybookInstService;
import com.central.msargus.soar.impl.service.ISoarActivityInstService;
import com.central.msargus.soar.impl.util.Constants;
import com.central.msargus.soar.impl.util.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionEntity;
import org.camunda.bpm.engine.impl.pvm.runtime.ActivityInstanceState;

/**
 * @author YunTao.Li
 * @description:
 * @date 2022/5/20 17:29
 */
@Slf4j
public class EndEventExecutionHandler implements ExecutionHandler {

    private static volatile boolean isCreate = false;
    private static volatile EndEventExecutionHandler instance;

    public static EndEventExecutionHandler getSingleton() {
        if (null == instance) {
            synchronized (EndEventExecutionHandler.class) {
                if (null == instance) {
                    instance = new EndEventExecutionHandler();
                }
            }
        }
        return instance;
    }

    private EndEventExecutionHandler() {
        if (isCreate) {
        }
        isCreate = true;
    }

    @Override
    public void execute(DelegateExecution execution) {
        String eventName = execution.getEventName();

        if (ExecutionListener.EVENTNAME_END.equals(eventName)) { // 结束
            this.end(execution);
        }
    }

    @Override
    public void start(DelegateExecution execution) {

    }

    @Override
    public void end(DelegateExecution execution) {
        ExecutionEntity executionEntity = (ExecutionEntity) execution;
        ISoarActivityInstService soarActivityInstService = SpringContextUtils.getBean("soarActivityInstServiceImpl");
        IPlaybookInstService playbookInstService = SpringContextUtils.getBean("playbookInstServiceImpl");

        // 判断事件步骤,不是开始步骤则直接返回不处理
        int activityState = executionEntity.getActivityInstanceState();
        if (!(ActivityInstanceState.DEFAULT.getStateCode() == activityState)) {
            return;
        }

        // step2 :获得流程信息
        String processInstID = execution.getProcessInstanceId();

        // step3 :判断是否结束
        boolean isEnd = executionEntity.isEnded();
        if (isEnd) {
            boolean f = soarActivityInstService.notExistErrorActivity(processInstID);
            // 如果不存在异常，则修改流程实例状态为结束,否则修改为异常终止
            if (f) {
                playbookInstService.instStatusChange(processInstID, Constants.PLAYBOOK_INST_STATUS_FINISHED);
            } else {
                playbookInstService.instStatusChange(processInstID, Constants.PLAYBOOK_INST_STATUS_EXCEPTION);
            }
        }
    }

    @Override
    public void take(DelegateExecution execution) {

    }
}
