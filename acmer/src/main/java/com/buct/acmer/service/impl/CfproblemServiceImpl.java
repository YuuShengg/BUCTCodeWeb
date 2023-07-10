package com.buct.acmer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.buct.acmer.entity.Cfcontest;
import com.buct.acmer.entity.Cfproblem;
import com.buct.acmer.mapper.CfproblemMapper;
import com.buct.acmer.service.ICfproblemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author BUCT
 * @since 2023-07-09
 */
@Service
public class CfproblemServiceImpl extends ServiceImpl<CfproblemMapper, Cfproblem> implements ICfproblemService {

}
