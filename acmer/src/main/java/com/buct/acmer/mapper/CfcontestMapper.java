package com.buct.acmer.mapper;

import com.buct.acmer.entity.Cfcontest;
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
 * @since 2023-07-09
 */
@Mapper
public interface CfcontestMapper extends BaseMapper<Cfcontest> {

    @Select("SELECT cf.cf_contest_name as contest_name, cf.cf_contest_startTimeSeconds as contest_startTimeSeconds, " +
            "cf.cf_contest_durationSeconds as contest_durationSeconds, cf.cf_contest_id as contest_url " +
            "FROM cfcontest cf " +
            "WHERE cf.cf_contest_phase = 'BEFORE' " +
            "ORDER BY cf.cf_contest_startTimeSeconds DESC")
    List<RecentContest> selectRecentContest(List<RecentContest> list);
}
