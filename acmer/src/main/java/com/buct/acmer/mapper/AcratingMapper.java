package com.buct.acmer.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.buct.acmer.entity.Acrating;
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
public interface AcratingMapper extends BaseMapper<Acrating> {

    @Select("SELECT stu.stu_no, stu.stu_name, stu.stu_class, stu.stu_ac_id, ac.ac_new_rating as new_rating, ac.ac_old_rating as old_rating, " +
            "ac.ac_rank as ranks, ac.ac_contest_date as date, ac.ac_contest_name as contest_name, ac.ac_ac_number as ac_number, ac.ac_sc_number as sc_number " +
            "FROM student stu " +
            "JOIN acrating ac ON stu.stu_ac_id = ac.ac_user_id " +
            "WHERE ac.ac_contest_name = #{acContestName}")
    Page<ContestStatus> selectParticipantsByContestName(Page<ContestStatus> page, @Param("acContestName") String fieldValue);
}
