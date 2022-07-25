package com.el.eldevops.service;

import com.el.eldevops.model.PlaybookDefineEntity;

/**
 * @author YunTao.Li
 * @description:
 * @date 2022/7/25 10:07
 */
public interface IPlaybookDefineService {
    public PlaybookDefineEntity getPlaybookDefineByProcessDefId(String processDefId);
}
