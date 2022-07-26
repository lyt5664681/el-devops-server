package com.el.eldevops.bpm.listener;

import com.el.eldevops.bpm.handle.execution.*;
import com.el.eldevops.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;

import javax.annotation.Resource;

/**
 * @author YunTao.Li
 * @description:
 * @date 2022/5/24 9:22
 */
@Slf4j
@Component
public class GlobalExcutionListener implements ExecutionListener {

    @Resource
    private StartEventExecutionHandler startEventExecutionHandler;

    @Resource
    private EndEventExecutionHandler endEventExecutionHandler;

    @Resource
    private ExclusiveGatewayExecutionHandler exclusiveGatewayExecutionHandler;

    @Resource
    private IntermediateEventExecutionHandler intermediateEventExecutionHandler;

    @Resource
    private CallActivityExecutionHandler callActivityExecutionHandler;


    @Autowired
    DataSourceTransactionManager dataSourceTransactionManager;

    @Autowired
    TransactionDefinition transactionDefinition;

    @Override
    public void notify(DelegateExecution execution) throws Exception {
        log.info("execution---> " + execution.getCurrentActivityName() + "-->" + execution.getEventName() + "-->" +
                execution.getBpmnModelElementInstance().getElementType().getTypeName());
        String processInstID = execution.getProcessInstanceId();
        String activityId = execution.getCurrentActivityId();
        String activityName = execution.getCurrentActivityName();

        try {
            String elementType = execution.getBpmnModelElementInstance().getElementType().getTypeName();
            switch (elementType) {
                case Constants.ACTIVITY_TYPE_STARTEVENT:
                    startEventExecutionHandler.execute(execution);
                    break;
                case Constants.ACTIVITY_TYPE_ENDEVENT:
                    endEventExecutionHandler.execute(execution);
                    break;
                case Constants.ACTIVITY_TYPE_USERTASK:
                    // do nothing
                    break;
                case Constants.ACTIVITY_TYPE_TASK:
//                    automaticTaskExecutionHandler.execute(execution);
                    break;
                case Constants.ACTIVITY_TYPE_EXCLUSIVEGATEWAY:
                    Thread.sleep(50);
                    exclusiveGatewayExecutionHandler.execute(execution);
                    break;
                case Constants.ACTIVITY_TYPE_INTERMEDIATECATCHEVENT:
                    Thread.sleep(50);
                    intermediateEventExecutionHandler.execute(execution);
                    break;
                case Constants.ACTIVITY_TYPE_CALLACTIVITY:
                    Thread.sleep(50);
                    callActivityExecutionHandler.execute(execution);
                    break;
                default:
                    // do nothing
                    break;
            }
        } catch (Exception e) {
            // todo: 挂起流程实例,添加异常信息
            log.error("流程执行异常，挂起流程实例ID：" + processInstID + " 活动ID:" + activityId + " 活动名称:" + activityName, e);
        }
    }
}
