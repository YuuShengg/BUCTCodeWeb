package com.buct.acmer.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.buct.acmer.entity.Cfsubmission;
import com.buct.acmer.mapper.CfsubmissionMapper;
import com.buct.acmer.service.ICfsubmissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author BUCT
 * @since 2023-07-15
 */
@Service
public class CfsubmissionServiceImpl extends ServiceImpl<CfsubmissionMapper, Cfsubmission> implements ICfsubmissionService {

    @Resource
    private CfsubmissionMapper cfsubmissionMapper;

    @Override
    public Page<Cfsubmission> selectByContestIdAndStuNo(int currentPage, int pageSize, String contestId, String stuId) {
        Page<Cfsubmission> page = new Page<>(currentPage, pageSize);
        return cfsubmissionMapper.selectByContestIdAndCfId(page, contestId, stuId);
    }

}
