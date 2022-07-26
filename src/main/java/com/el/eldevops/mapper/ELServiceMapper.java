package com.el.eldevops.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.el.eldevops.model.ELServiceEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author YunTao.Li
 * @description:
 * @date 2022/7/21 10:03
 */
@Mapper
public interface ELServiceMapper extends BaseMapper<ELServiceEntity> {
    @Select("SELECT * FROM soar_service t1 WHERE EXISTS " +
            "( SELECT 1 FROM soar_service_on_activity tt1 WHERE tt1.book_id = #{bookId} " +
            "AND tt1.activity_id = #{activityId} AND t1.service_id = tt1.service_id )")
    List<ELServiceEntity> getActivityService(@Param("bookId") String bookId, @Param("activityId") String activityId);
}
