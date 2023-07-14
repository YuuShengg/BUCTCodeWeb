package com.buct.acmer.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.buct.acmer.entity.Accontest;
import com.buct.acmer.entity.Acrating;
import com.buct.acmer.entity.ContestStatus;
import com.buct.acmer.entity.PublicProperty;
import com.buct.acmer.service.impl.AccontestServiceImpl;
import com.buct.acmer.service.impl.AcratingServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author BUCT
 * @since 2023-07-13
 */
@Api(tags = "AtContest")
@RestController
@RequestMapping("/acmer/accontest")
public class AccontestController {

    @Resource
    private AccontestServiceImpl accontestService;
    @Resource
    private AcratingServiceImpl acratingService;

    @ApiOperation("按开始时间降序查询所有At竞赛信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage",value = "当前页数",dataType = "int",required = true),
            @ApiImplicitParam(name = "pageSize",value = "页面大小",dataType = "int",required = true)
    })
    @GetMapping("/all/{currentPage}/{pageSize}")
    public PublicProperty<Page<Accontest>> selectAll(@PathVariable("currentPage") Integer currentPage,
                                                     @PathVariable("pageSize") Integer pageSize) {

        Page<Accontest> page = accontestService.selectAllOrderByStartTimeDesc(currentPage, pageSize);
        return new PublicProperty(200, "success", page);
    }

    @ApiOperation("按比赛名称查询某场At竞赛参赛数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage",value = "当前页数",dataType = "int",required = true),
            @ApiImplicitParam(name = "pageSize",value = "页面大小",dataType = "int",required = true),
            @ApiImplicitParam(name = "fieldValue",value = "比赛名称",dataType = "String",required = true)
    })
    @GetMapping("/{fieldValue}/{currentPage}/{pageSize}")
    public PublicProperty<Page<ContestStatus>> selectParticipants(@PathVariable("currentPage") Integer currentPage,
                                                                  @PathVariable("pageSize") Integer pageSize,
                                                                  @PathVariable("fieldValue") String fieldValue) {

        Page<ContestStatus> page = acratingService.selectParticipantsByContestName(currentPage, pageSize,fieldValue);
        return new PublicProperty(200, "success", page);
    }
}
