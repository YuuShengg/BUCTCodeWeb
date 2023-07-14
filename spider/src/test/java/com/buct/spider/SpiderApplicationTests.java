package com.buct.spider;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.buct.spider.atcoder.*;
import com.buct.spider.codeforces.GetCfData;
import com.buct.spider.entity.Accontest;
import com.buct.spider.entity.Acrating;
import com.buct.spider.entity.Student;
import com.buct.spider.mapper.AccontestMapper;
import com.buct.spider.mapper.AcratingMapper;
import com.buct.spider.mapper.StudentMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import us.codecraft.webmagic.Spider;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class SpiderApplicationTests {

    @Resource
    private ATProcess atProcess;
    @Resource
    private ATPipeline atPipeline;
    @Resource
    private StudentMapper studentMapper;
    @Resource
    private AccontestMapper accontestMapper;
    @Resource
    private GetCfData getCfData;
    @Resource
    private AcContestProcess acContestProcess;
    @Resource
    private AcContestPipeline acContestPipeline;
    @Resource
    private AcRatingPipeline acRatingPipeline;
    @Resource
    private AcRatingProcess acRatingProcess;
    @Resource
    private AcSubmissionProcess acSubmissionProcess;
    @Resource
    private AcSubmissionPipeline acSubmissionPipeline;
    @Resource
    private AcratingMapper acratingMapper;
    @Test
    void contextLoads() {

}
