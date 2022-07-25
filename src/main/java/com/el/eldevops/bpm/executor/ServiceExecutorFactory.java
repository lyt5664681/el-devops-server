package com.el.eldevops.bpm.executor;

import com.central.msargus.soar.impl.util.Constants;
import com.el.eldevops.bpm.executor.impl.BuiltInJavaServiceExecutor;
import com.el.eldevops.bpm.executor.impl.HttpRemoteServiceExecutor;
import com.el.eldevops.bpm.executor.impl.PythonServiceExecutor;
import com.el.eldevops.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author YunTao.Li
 * @description:
 * @date 2022/5/24 13:47
 */
@Component
public class ServiceExecutorFactory {
    @Autowired
    private BuiltInJavaServiceExecutor builtInJavaServiceExecutor;

    @Autowired
    private HttpRemoteServiceExecutor httpRemoteServiceExecutor;

    @Autowired
    private PythonServiceExecutor pythonServiceExecutor;

    /**
     * @param protocal 协议
     * @return com.central.msargus.soar.impl.bpm.executor.SoarServiceExecutor
     * @description 根据protocal选择实例
     * @author YunTao.Li
     * @date 2022/6/7 10:09
     */
    public ServiceExecutorInterface newInstance(String protocal) {
        if (Constants.SERVICE_PROTOCAL_PYTHON.equalsIgnoreCase(protocal)) {
            return pythonServiceExecutor;
        } else if (Constants.SERVICE_PROTOCAL_LOCAL.equalsIgnoreCase(protocal)) {
            return builtInJavaServiceExecutor;
        } else if (Constants.SERVICE_PROTOCAL_HTTP.equalsIgnoreCase(protocal)) {
            return httpRemoteServiceExecutor;
        }
        return null;
    }
}
