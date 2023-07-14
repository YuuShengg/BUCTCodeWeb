package com.buct.acmer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.buct.acmer.entity.Accontest;
import com.buct.acmer.entity.RecentContest;
import com.buct.acmer.mapper.AccontestMapper;
import com.buct.acmer.service.IAccontestService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author BUCT
 * @since 2023-07-13
 */
@Service
public class AccontestServiceImpl extends ServiceImpl<AccontestMapper, Accontest> implements IAccontestService {

    @Resource
    private AccontestMapper accontestMapper;

    public Page<Accontest> selectAllOrderByStartTimeDesc(int currentPage, int pageSize) {
        Page<Accontest> page = new Page<>(currentPage, pageSize);
        QueryWrapper<Accontest> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("ac_contest_startTimeSeconds");
        return  accontestMapper.selectPage(page, queryWrapper);
    }

    public List<RecentContest> selectRecentContest() {
        List<RecentContest> list = new ArrayList<>();
        return accontestMapper.selectRecentContest(list);
    }
}
