package com.el.eldevops.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.el.eldevops.mapper.ExecutionRecordMapper;
import com.el.eldevops.model.ExecutionRecordEntity;
import com.el.eldevops.service.IExecutionRecordService;
import com.el.eldevops.util.Constants;
import org.apache.commons.lang3.StringUtils;
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

    @Override
    public void entrustByTaskId(String taskId, String assignee) {
        UpdateWrapper queryWrapper = new UpdateWrapper();
        queryWrapper.eq("task_id", taskId);

        ExecutionRecordEntity executionRecordEntity = new ExecutionRecordEntity();
        executionRecordEntity.setAssignee(assignee);
        executionRecordEntity.setRecordStatus(Constants.ACTIVITY_INST_STATUS_HOLD);

        executionRecordMapper.update(executionRecordEntity, queryWrapper);
    }

    /**
     * @param taskId
     * @param serviceId
     * @param paramJSON
     * @return void
     * @description 填充服务调用情况
     * @author YunTao.Li
     * @date 2022/6/8 9:51
     */
    @Override
    public void fillingByTaskId(String taskId, String serviceId, String paramJSON) {
        UpdateWrapper queryWrapper = new UpdateWrapper();
        queryWrapper.eq("task_id", taskId);

        ExecutionRecordEntity executionRecordEntity = new ExecutionRecordEntity();
        executionRecordEntity.setParamJson(paramJSON);
        executionRecordEntity.setServiceId(serviceId);
        executionRecordEntity.setRecordStatus(Constants.ACTIVITY_INST_STATUS_NORMAL);

        executionRecordMapper.update(executionRecordEntity, queryWrapper);
    }

    /**
     * @param taskId
     * @param serviceId
     * @param paramJSON
     * @param errorMessage
     * @return void
     * @description 填充失败的服务调用
     * @author YunTao.Li
     * @date 2022/6/8 10:00
     */
    @Override
    public void feedBackError(String taskId, String serviceId, String paramJSON, String errorMessage) {
        // 处理errorMessage长度 不超过4500
        if (StringUtils.isNotBlank(errorMessage) && errorMessage.length() > 4500) {
            errorMessage = errorMessage.substring(0, 4500) + ".....message is too long。";
        }

        UpdateWrapper queryWrapper = new UpdateWrapper();
        queryWrapper.eq("task_id", taskId);

        ExecutionRecordEntity executionRecordEntity = new ExecutionRecordEntity();
        executionRecordEntity.setParamJson(paramJSON);
        executionRecordEntity.setServiceId(serviceId);
        executionRecordEntity.setRecordMessage(errorMessage);
        executionRecordEntity.setRecordStatus(Constants.ACTIVITY_INST_STATUS_ERROR);

        executionRecordMapper.update(executionRecordEntity, queryWrapper);
    }
}
