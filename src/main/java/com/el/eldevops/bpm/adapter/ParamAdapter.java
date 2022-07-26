package com.el.eldevops.bpm.adapter;

import com.el.eldevops.bpm.model.ParamInstPOJO;
import com.el.eldevops.model.ParamsInstanceEntity;
import com.el.eldevops.util.Constants;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author YunTao.Li
 * @description:
 * @date 2022/5/24 15:00
 */
@Component
public class ParamAdapter {

    @Lazy
    @Autowired
    private RuntimeService runtimeService;

    /**
     * @param soarParamsEntity
     * @param processInstID
     * @return java.util.List<com.central.msargus.soar.impl.model.pojo.ParamInstPOJO>
     * @description 入参适配器，从运行环境获得参数值，并填充参数值
     * @author YunTao.Li
     * @date 2022/5/24 15:07
     */
    protected ParamInstPOJO inputParamAdapter(ParamsInstanceEntity soarParamsEntity, String processInstID) {
        String paramValue = soarParamsEntity.getParamValue();
        Integer paramValueType = soarParamsEntity.getValueType();

        // 构造paramInstPOJO
        ParamInstPOJO paramInst = new ParamInstPOJO();
        paramInst.setParamName(soarParamsEntity.getParamName());
        paramInst.setParamLabel(soarParamsEntity.getParamLabel());
        paramInst.setParamType(soarParamsEntity.getParamType());
        paramInst.setDataType(soarParamsEntity.getDataType());

        if (paramValueType == Constants.PARAMS_VALUETYPE_CONSTANT) {
            // 常量
            paramInst.setParamValue(paramValue);
            return paramInst;
        }

        // 按参数类型对参数值进行适配处理
        String paramDataType = soarParamsEntity.getDataType();
        Object paramObjectValue = paramDataTransfer(paramDataType, paramValue, processInstID);

        paramInst.setParamValue(paramObjectValue);

        return paramInst;
    }

    /**
     * @param paramsInstanceEntityList
     * @param processInstID
     * @return java.util.List<com.central.msargus.soar.impl.model.pojo.ParamInstPOJO>
     * @description
     * @author YunTao.Li
     * @date 2022/5/24 17:47
     */
    public List<ParamInstPOJO> paramAdapter(List<ParamsInstanceEntity> paramsInstanceEntityList, String processInstID) {
        List<ParamInstPOJO> paramInstPOJOS = new ArrayList<>();

        for (int i = 0; i < paramsInstanceEntityList.size(); i++) {
            ParamsInstanceEntity soarParamsEntity = paramsInstanceEntityList.get(i);
            Integer paramType = soarParamsEntity.getParamType();

            switch (paramType) {
                case Constants.PARAMS_TYPE_INPUT:
                    // 输入参数
                    paramInstPOJOS.add(inputParamAdapter(soarParamsEntity, processInstID));
                    break;
                case Constants.PARAMS_TYPE_OUTPUT:
                    // 输出参数
//                    paramInstPOJOS.add(outputParamAdapter(soarParamsEntity, processInstID, reval));
                    break;
                default:
                    break;
            }

        }
        return paramInstPOJOS;
    }

    /**
     * @param paramDataType
     * @param paramValue
     * @param processInstID
     * @return java.lang.Object
     * @description 参数类型转换器
     * @author YunTao.Li
     * @date 2022/5/24 15:38
     */
    protected Object paramDataTransfer(String paramDataType, String paramValue, String processInstID) {
        Object paramObjectValue = null;
        // 按照参数类型转换参数值
        switch (paramDataType) {
            case Constants.PARAMS_DATA_TYPE_STRING:
                // 字符串
                paramObjectValue = paramAdapterForString(paramValue, processInstID);
                break;
            case Constants.PARAMS_DATA_TYPE_INTEGER:
                // 整型
                paramObjectValue = paramAdapterForInteger(paramValue, processInstID);
                break;
            case Constants.PARAMS_DATA_TYPE_BOOLEAN:
                // 布尔型
                paramObjectValue = paramAdapterForBoolean(paramValue, processInstID);
                break;
            default:
                // 字符串
                paramObjectValue = paramAdapterForString(paramValue, processInstID);
                break;
        }

        return paramObjectValue;
    }

    /**
     * @param paramsInstanceEntityList
     * @param processInstID
     * @param reval
     * @return com.central.msargus.soar.impl.model.pojo.ParamInstPOJO
     * @description 出参适配器，将返回结果按类型填充到运行环境
     * @author YunTao.Li
     * @date 2022/5/24 17:44
     */
    public ParamInstPOJO outputParamAdapter(List<ParamsInstanceEntity> paramsInstanceEntityList, String processInstID, Object reval) {

        if (paramsInstanceEntityList.size() < 1 || reval == null) {
            ParamInstPOJO paramInst = new ParamInstPOJO();
            paramInst.setParamValue(reval);
            paramInst.setParamType(Constants.PARAMS_TYPE_OUTPUT);
            return paramInst;
        }

        // step1 : 找出输出参数
        ParamsInstanceEntity outputParam = null;
        for (int i = 0; i < paramsInstanceEntityList.size(); i++) {
            ParamsInstanceEntity soarParamsEntity = paramsInstanceEntityList.get(i);
            Integer paramType = soarParamsEntity.getParamType();
            if (paramType != Constants.PARAMS_TYPE_OUTPUT) {
                continue;
            } else {
                outputParam = soarParamsEntity;
            }
        }

        // step2 : 出参永远写入运行环境
        String paramDataType = outputParam.getDataType();
        String paramValue = outputParam.getParamValue();

        // step3 : 构造paramInstPOJO
        ParamInstPOJO paramInst = new ParamInstPOJO();
        paramInst.setParamName(outputParam.getParamName());
        paramInst.setParamLabel(outputParam.getParamLabel());
        paramInst.setParamType(outputParam.getParamType());
        paramInst.setParamValue(reval);

        // step4 : 按类型写入环境
        if (reval == null) {
            this.runtimeService.setVariable(processInstID, paramValue, "");
            return paramInst;
        }

        switch (paramDataType) {
            case Constants.PARAMS_DATA_TYPE_STRING:
                // 字符串
                this.runtimeService.setVariable(processInstID, paramValue, reval.toString());
                break;
            case Constants.PARAMS_DATA_TYPE_INTEGER:
                // 整型
                this.runtimeService.setVariable(processInstID, paramValue, Integer.parseInt(reval.toString()));
                break;
            case Constants.PARAMS_DATA_TYPE_BOOLEAN:
                // 布尔型
                this.runtimeService.setVariable(processInstID, paramValue, Boolean.parseBoolean(reval.toString()));
                break;
            default:
                // 字符串
                this.runtimeService.setVariable(processInstID, paramValue, reval.toString());
                break;
        }

        return paramInst;
    }

    /**
     * @param paramValue
     * @param processInstID
     * @return com.central.msargus.soar.impl.model.pojo.ParamInstPOJO
     * @description 字符串参数适配器，用于填充参数值
     * @author YunTao.Li
     * @date 2022/5/24 15:15
     */
    protected String paramAdapterForString(String paramValue, String processInstID) {
        Object value = runtimeService.getVariable(processInstID, paramValue);
        if (value == null) {
            value = "";
        }
        return String.valueOf(value);
    }

    /**
     * @param paramValue
     * @param processInstID
     * @return com.central.msargus.soar.impl.model.pojo.ParamInstPOJO
     * @description 整数型参数适配器，用于填充参数值
     * @author YunTao.Li
     * @date 2022/5/24 15:15
     */
    protected Integer paramAdapterForInteger(String paramValue, String processInstID) {
        Object value = runtimeService.getVariable(processInstID, paramValue);
        if (value == null) {
            value = 0;
        }
        return Integer.valueOf(value.toString());
    }

    /**
     * @param paramValue
     * @param processInstID
     * @return com.central.msargus.soar.impl.model.pojo.ParamInstPOJO
     * @description 布尔型参数适配器，用于填充参数值
     * @author YunTao.Li
     * @date 2022/5/24 15:15
     */
    protected Boolean paramAdapterForBoolean(String paramValue, String processInstID) {
        Object value = runtimeService.getVariable(processInstID, paramValue);
        if (value == null) {
            value = false;
        }
        return Boolean.valueOf(value.toString());
    }
}
