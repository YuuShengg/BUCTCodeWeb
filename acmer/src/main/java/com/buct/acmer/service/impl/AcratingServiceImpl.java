package com.buct.acmer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.buct.acmer.entity.Acrating;
import com.buct.acmer.entity.Cfrating;
import com.buct.acmer.entity.ContestStatus;
import com.buct.acmer.mapper.AcratingMapper;
import com.buct.acmer.service.IAcratingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author BUCT
 * @since 2023-07-13
 */
@Service
public class AcratingServiceImpl extends ServiceImpl<AcratingMapper, Acrating> implements IAcratingService {

    @Resource
    private AcratingMapper acratingMapper;

    public Page<Acrating> selectOrderByDate(int currentPage, int pageSize, String fieldName, String fieldValue) {
        Page<Acrating> page = new Page<>(currentPage, pageSize);
        QueryWrapper<Acrating> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq(fieldName, fieldValue)
                .orderByDesc("ac_contest_date");
        return acratingMapper.selectPage(page, queryWrapper);
    }

    @Override
    public Page<ContestStatus> selectParticipantsByContestName(int currentPage, int pageSize, String fieldValue) {
        Page<ContestStatus> page = new Page<>(currentPage, pageSize);
        return acratingMapper.selectParticipantsByContestName(page, fieldValue);
    }
}
