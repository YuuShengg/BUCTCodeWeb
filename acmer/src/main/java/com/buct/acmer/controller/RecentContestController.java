package com.buct.acmer.controller;

import com.buct.acmer.entity.PublicProperty;
import com.buct.acmer.entity.RecentContest;
import com.buct.acmer.service.impl.AccontestServiceImpl;
import com.buct.acmer.service.impl.CfcontestServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "RecentContest")
@RestController
@RequestMapping("/acmer/contest")
public class RecentContestController {
    @Resource
    private CfcontestServiceImpl cfcontestService;
    @Resource
    private AccontestServiceImpl accontestService;

    @ApiOperation("按开始时间降序查询近期即将到来的比赛信息")
    @GetMapping("/recent")
    public PublicProperty<List<RecentContest>> selectAll() {

        List<RecentContest> recentContests = cfcontestService.selectRecentContest();
        List<RecentContest> acRecentContest = accontestService.selectRecentContest();
        recentContests.addAll(acRecentContest);
        return new PublicProperty(200, "success", recentContests);
    }
}
