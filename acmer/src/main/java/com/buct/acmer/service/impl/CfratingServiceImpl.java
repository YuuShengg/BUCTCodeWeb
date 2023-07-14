package com.buct.acmer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.buct.acmer.entity.Acrating;
import com.buct.acmer.entity.Cfcontest;
import com.buct.acmer.entity.Cfrating;
import com.buct.acmer.entity.ContestStatus;
import com.buct.acmer.mapper.AcratingMapper;
import com.buct.acmer.mapper.CfratingMapper;
import com.buct.acmer.service.ICfratingService;
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
public class CfratingServiceImpl extends ServiceImpl<CfratingMapper, Cfrating> implements ICfratingService {

    @Resource
    private CfratingMapper cfratingMapper;

    public Page<Cfrating> selectOrderByDate(int currentPage, int pageSize, String fieldName, String fieldValue) {
        Page<Cfrating> page = new Page<>(currentPage, pageSize);
        QueryWrapper<Cfrating> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq(fieldName, fieldValue)
                .orderByDesc("cf_update_time");
        return cfratingMapper.selectPage(page, queryWrapper);
    }
    @Override
    public Page<ContestStatus> selectParticipantsByContestId(int currentPage, int pageSize, String fieldValue) {
        Page<ContestStatus> page = new Page<>(currentPage, pageSize);
        return cfratingMapper.selectParticipantsByContestId(page, fieldValue);
    }
}
