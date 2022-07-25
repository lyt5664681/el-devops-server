package com.el.eldevops.service;

import com.el.eldevops.model.PlaybookInstEntity;

/**
 * @author YunTao.Li
 * @description:
 * @date 2022/7/25 11:03
 */
public interface IPlaybookInstanceService {
    void insert(PlaybookInstEntity playbookInstEntity);

    void instStatusChange(String processInstId, int status);
}
