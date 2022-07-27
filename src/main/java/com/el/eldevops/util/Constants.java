package com.el.eldevops.util;

/**
 * @author YunTao.Li
 * @description:
 * @date 2022/7/20 11:18
 */
public class Constants {
    // ========================环节类型：开始、结束、人工任务、自动任务=======================================
    public static final String ACTIVITY_TYPE_STARTEVENT = "startEvent";
    public static final String ACTIVITY_TYPE_ENDEVENT = "endEvent";
    public static final String ACTIVITY_TYPE_USERTASK = "userTask";
    public static final String ACTIVITY_TYPE_TASK = "task";
    public static final String ACTIVITY_TYPE_EXCLUSIVEGATEWAY = "exclusiveGateway"; //网关
    public static final String ACTIVITY_TYPE_INTERMEDIATECATCHEVENT = "intermediateCatchEvent"; //延迟
    public static final String ACTIVITY_TYPE_CALLACTIVITY = "callActivity";// 子流程callActivity

    // ========================参数归属：1全局参数(剧本参数)，2环节参数(服务参数)=======================================
    public static final int PARAMS_OF_GLOBAL = 1; // 1全局参数(剧本参数)
    public static final int PARAMS_OF_SERVICE = 2; // 2环节参数(服务参数)

    // ========================剧本状态=======================================
    public static final int PLAYBOOK_STATUS_TEMP = 0; // 已生成，临时状态
    public static final int PLAYBOOK_STATUS_CREATED = 1; // 已创建
    public static final int PLAYBOOK_STATUS_DEPLOYED = 2; // 已发布
    public static final int PLAYBOOK_STATUS_SUSPENDED = 3; // 已挂起
    public static final int PLAYBOOK_STATUS_DRAFT = 4; // 草稿
    // ========================剧本实例状态:1运行中,2暂停,3异常终止,4已完结=======================================
    public static final int PLAYBOOK_INST_STATUS_RUNNING = 1; //运行中
    public static final int PLAYBOOK_INST_STATUS_PAUSE = 2; //暂停
    public static final int PLAYBOOK_INST_STATUS_EXCEPTION = 3; //异常终止
    public static final int PLAYBOOK_INST_STATUS_FINISHED = 4; //已完结

    //=======================活动实例执行状态4正常5异常========================================
    public static final int ACTIVITY_INST_STATUS_CREATE = 1;
    public static final int ACTIVITY_INST_STATUS_HOLD = 3; // 待处理
    public static final int ACTIVITY_INST_STATUS_NORMAL = 4; // 已处理
    public static final int ACTIVITY_INST_STATUS_ERROR = 5; // 异常

    //=======================自动任务的标识========================================
    public static final String TASK_EVENT_TYPE_AUTOMATIC = "AUTOMATIC";

    //=======================服务协议========================================
    public static final String SERVICE_PROTOCAL_LOCAL = "LOCAL"; // 本地调用
    public static final String SERVICE_PROTOCAL_HTTP = "HTTP"; // 服务协议:http
    public static final String SERVICE_PROTOCAL_PYTHON = "PYTHON"; // 服务协议:python

    //=======================参数数据类型========================================
    public static final String PARAMS_DATA_TYPE_STRING = "String"; // 字符串
    public static final String PARAMS_DATA_TYPE_BOOLEAN = "Boolean"; // 布尔
    public static final String PARAMS_DATA_TYPE_INTEGER = "Integer"; // 整形

    //=======================参数值类型========================================
    public static final int PARAMS_VALUETYPE_CONSTANT = 1; // 常量
    public static final int PARAMS_VALUETYPE_VARIABLE = 2; // 变量

    // =======================参数类型========================================
    public static final int PARAMS_TYPE_OUTPUT = 1; // 出参
    public static final int PARAMS_TYPE_INPUT = 2; // 入参
}
