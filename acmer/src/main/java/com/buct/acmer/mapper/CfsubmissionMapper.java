package com.buct.acmer.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.buct.acmer.entity.Cfsubmission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author BUCT
 * @since 2023-07-15
 */
@Mapper
public interface CfsubmissionMapper extends BaseMapper<Cfsubmission> {

    @Select("SELECT * " +
            "FROM cfsubmission cf " +
            "WHERE cf.cf_contest_id = #{contestId} and cf.cf_user_id = #{stuId}")
    Page<Cfsubmission> selectByContestIdAndCfId(Page<Cfsubmission> page, @Param("contestId") String contestId, @Param("stuId") String stuId);
}
