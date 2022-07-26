package com.el.eldevops.bpm.model;

import lombok.Data;

/**
 * @author YunTao.Li
 * @description:
 * @date 2022/6/16 14:43
 */
@Data
public class TaskExecuteVO {
    private String bookId;
    private String taskId;
    private String executionId;
    private String appId;
    private String appInstId;
    private String actDefId;
    private String actInstId;
    private String processInstId;
    private String processDefId;
}
