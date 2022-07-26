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

    /**
     * @param taskId
     * @param assignee
     * @return void
     * @description 为环节记录添加处理人
     * @author YunTao.Li
     * @date 2022/6/14 14:49
     */
    void entrustByTaskId(String taskId, String assignee);

    /**
     * @param taskId
     * @param serviceId
     * @param paramJSON
     * @return void
     * @description 填充服务调用情况
     * @author YunTao.Li
     * @date 2022/6/8 9:51
     */
    void fillingByTaskId(String taskId, String serviceId, String paramJSON);

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
    void feedBackError(String taskId, String serviceId, String paramJSON, String errorMessage);
}
