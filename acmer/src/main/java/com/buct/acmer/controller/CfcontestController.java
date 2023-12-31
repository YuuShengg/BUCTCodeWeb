package com.buct.acmer.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.buct.acmer.entity.*;
import com.buct.acmer.service.impl.CfcontestServiceImpl;
import com.buct.acmer.service.impl.CfratingServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author BUCT
 * @since 2023-07-09
 */
@Api(tags = "CfContest")
@RestController
@RequestMapping("/acmer/cfcontest")
public class CfcontestController {

    @Resource
    private CfcontestServiceImpl cfcontestService;
    @Resource
    private CfratingServiceImpl cfratingService;

    @ApiOperation("按开始时间降序查询所有cf竞赛信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage",value = "当前页数",dataType = "int",required = true),
            @ApiImplicitParam(name = "pageSize",value = "页面大小",dataType = "int",required = true)
    })
    @GetMapping("/all/{currentPage}/{pageSize}")
    public PublicProperty<Page<Cfcontest>> selectAll(@PathVariable("currentPage") Integer currentPage,
                                                      @PathVariable("pageSize") Integer pageSize){

        Page<Cfcontest> page = cfcontestService.selectAllOrderByStartTimeDesc(currentPage, pageSize);

        return new PublicProperty(200,"success",page);
    }

    @ApiOperation("按比赛Id查询某场cf竞赛参赛数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage",value = "当前页数",dataType = "int",required = true),
            @ApiImplicitParam(name = "pageSize",value = "页面大小",dataType = "int",required = true),
            @ApiImplicitParam(name = "fieldValue",value = "比赛Id",dataType = "String",required = true)
    })
    @GetMapping("/{fieldValue}/{currentPage}/{pageSize}")
    public PublicProperty<Page<ContestStatus>> selectParticipants(@PathVariable("currentPage") Integer currentPage,
                                                             @PathVariable("pageSize") Integer pageSize,
                                                             @PathVariable("fieldValue") String fieldValue) {

        Page<ContestStatus> page = cfratingService.selectParticipantsByContestId(currentPage, pageSize,fieldValue);
        return new PublicProperty(200, "success", page);
    }
}
