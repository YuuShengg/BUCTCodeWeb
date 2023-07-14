package com.buct.acmer.mapper;

import com.buct.acmer.entity.Accontest;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.buct.acmer.entity.RecentContest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author BUCT
 * @since 2023-07-13
 */
@Mapper
public interface AccontestMapper extends BaseMapper<Accontest> {

    @Select("SELECT ac.ac_contest_name as contest_name, ac.ac_contest_startTimeSeconds as contest_startTimeSeconds, " +
            "ac.ac_contest_durationSeconds as contest_durationSeconds, ac.ac_contest_type as contest_url " +
            "FROM accontest ac " +
            "WHERE ac.ac_contest_phase = 'BEFORE' " +
            "ORDER BY ac.ac_contest_startTimeSeconds DESC")
    List<RecentContest> selectRecentContest(List<RecentContest> list);
}
