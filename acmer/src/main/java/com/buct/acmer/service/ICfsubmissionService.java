package com.buct.acmer.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.buct.acmer.entity.Cfsubmission;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author BUCT
 * @since 2023-07-15
 */
public interface ICfsubmissionService extends IService<Cfsubmission> {

    public Page<Cfsubmission> selectByContestIdAndStuNo(int currentPage, int pageSize, String contestId, String stuId);

}
