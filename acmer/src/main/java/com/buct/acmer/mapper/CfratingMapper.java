package com.buct.acmer.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.buct.acmer.entity.Cfrating;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.buct.acmer.entity.ContestStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author BUCT
 * @since 2023-07-13
 */
@Mapper
public interface CfratingMapper extends BaseMapper<Cfrating> {

    @Select("SELECT stu.stu_no, stu.stu_name, stu.stu_class, stu.stu_cf_id, cf.cf_new_rating as new_rating, cf.cf_old_rating as old_rating, " +
            "cf.cf_rank as ranks, cf.cf_update_time as date, cf.cf_contest_name as contest_name, cf.cf_ac_number as ac_number, cf.cf_sc_number as sc_number " +
            "FROM student stu " +
            "JOIN cfrating cf ON stu.stu_cf_id = cf.cf_user_id " +
            "WHERE cf.cf_contest_id = #{cfContestId}")
    Page<ContestStatus> selectParticipantsByContestId(Page<ContestStatus> page, @Param("cfContestId") String fieldValue);

}
