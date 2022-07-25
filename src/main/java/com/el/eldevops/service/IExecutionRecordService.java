package com.el.eldevops.service;

import com.el.eldevops.model.ExecutionRecordEntity;

/**
 * @author YunTao.Li
 * @description:
 * @date 2022/7/25 13:58
 */
public interface IExecutionRecordService {

    /**
     * @return void
     * @description 日常记录
     * @author YunTao.Li
     * @date 2022/7/25 13:59
     */
    void record(String processInstId, String activityInstId, String taskId, String serviceId);

    /**
     * @return void
     * @description 日常记录
     * @author YunTao.Li
     * @date 2022/7/25 13:59
     */
    void record(String processInstId, String activityInstId, String taskId, String serviceId, Integer status, String recordMessage, String paramJson);

    void insert(ExecutionRecordEntity executionRecordEntity);
}
