package com.el.eldevops.service.impl;

import com.el.eldevops.mapper.ExecutionRecordMapper;
import com.el.eldevops.model.ExecutionRecordEntity;
import com.el.eldevops.service.IExecutionRecordService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author YunTao.Li
 * @description:
 * @date 2022/7/25 13:58
 */
@Primary
@Component
public class ExecutionRecordServiceImpl implements IExecutionRecordService {

    @Resource
    private ExecutionRecordMapper executionRecordMapper;

    @Override
    public void record(String processInstId, String activityInstId, String taskId, String serviceId) {
        ExecutionRecordEntity executionRecordEntity = new ExecutionRecordEntity();
        executionRecordEntity.setProcessInstId(processInstId);
        executionRecordEntity.setActivityInstId(activityInstId);
        executionRecordEntity.setTaskId(taskId);
        executionRecordEntity.setServiceId(serviceId);

        this.insert(executionRecordEntity);
    }

    @Override
    public void record(String processInstId, String activityInstId, String taskId, String serviceId, Integer status, String recordMessage, String paramJson) {
        ExecutionRecordEntity executionRecordEntity = new ExecutionRecordEntity();
        executionRecordEntity.setProcessInstId(processInstId);
        executionRecordEntity.setActivityInstId(activityInstId);
        executionRecordEntity.setTaskId(taskId);
        executionRecordEntity.setServiceId(serviceId);
        executionRecordEntity.setRecordStatus(status);
        executionRecordEntity.setRecordMessage(recordMessage);
        executionRecordEntity.setParamJson(paramJson);

        this.insert(executionRecordEntity);
    }

    @Override
    public void insert(ExecutionRecordEntity executionRecordEntity) {
        executionRecordMapper.insert(executionRecordEntity);
    }
}
