package com.buct.acmer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.buct.acmer.entity.Cfcontest;
import com.buct.acmer.mapper.CfcontestMapper;
import com.buct.acmer.service.ICfcontestService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author BUCT
 * @since 2023-07-09
 */
@Service
public class CfcontestServiceImpl extends ServiceImpl<CfcontestMapper, Cfcontest> implements ICfcontestService {

    @Resource
    private CfcontestMapper cfcontestMapper;
    public Page<Cfcontest> selectAllOrderByStartTimeDesc(int currentPage, int pageSize) {
        Page<Cfcontest> page = new Page<>(currentPage, pageSize);
        QueryWrapper<Cfcontest> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("cf_contest_startTimeSeconds");
        return cfcontestMapper.selectPage(page, queryWrapper);
    }


}
