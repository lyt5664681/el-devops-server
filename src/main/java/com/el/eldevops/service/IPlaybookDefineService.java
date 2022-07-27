package com.el.eldevops.service;

import com.el.eldevops.model.PlaybookDefineEntity;
import com.el.eldevops.model.vo.PlaybookDefineVO;

import java.util.Map;

/**
 * @author YunTao.Li
 * @description:
 * @date 2022/7/25 10:07
 */
public interface IPlaybookDefineService {
    PlaybookDefineEntity getPlaybookDefineByProcessDefId(String processDefId);

    void createPlaybookDefine(PlaybookDefineVO playbookDefineVO);

    void deployPlaybookByDefId(String bookDefId);

    void startPlaybookByDefId(String bookDefId);

    void startPlaybookWithCondition(String bookDefId, Map condition);
}
