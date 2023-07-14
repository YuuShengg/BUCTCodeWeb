package com.buct.acmer.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.buct.acmer.entity.Cfrating;
import com.baomidou.mybatisplus.extension.service.IService;
import com.buct.acmer.entity.ContestStatus;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author BUCT
 * @since 2023-07-13
 */
public interface ICfratingService extends IService<Cfrating> {

    Page<ContestStatus> selectParticipantsByContestId(int currentPage, int pageSize, String fieldValue);
}
