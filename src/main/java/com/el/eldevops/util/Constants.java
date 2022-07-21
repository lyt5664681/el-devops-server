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
}
