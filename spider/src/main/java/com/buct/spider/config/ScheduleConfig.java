package com.buct.spider.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.buct.spider.atcoder.*;
import com.buct.spider.codeforces.GetCfData;
import com.buct.spider.entity.Accontest;
import com.buct.spider.entity.Acrating;
import com.buct.spider.entity.Student;
import com.buct.spider.mapper.AccontestMapper;
import com.buct.spider.mapper.AcratingMapper;
import com.buct.spider.mapper.StudentMapper;
import com.buct.spider.util.HttpRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;

import javax.annotation.Resource;
import java.util.List;

@Component
public class ScheduleConfig {

    @Resource
    private ATProcess atProcess;
    @Resource
    private ATPipeline atPipeline;
    @Resource
    private StudentMapper studentMapper;
    @Resource
    private AccontestMapper accontestMapper;
    @Resource
    private AcratingMapper acratingMapper;
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

    @Scheduled(fixedRate = 1000*60*60*24*7)
    private void AcContestProcess() {
        // Atcoder所有比赛信息
        Spider.create(acContestProcess)
                .addUrl("https://atcoder.jp/contests/")
                .addUrl("https://atcoder.jp/contests/archive")
                .addPipeline(acContestPipeline)
                .run();

        List<Student> students = studentMapper.selectList(null);
        for (int i = 0; i < students.size(); i++) {
            String id = students.get(i).getStuAcId();
            if(id!=null && !id.equals("")){
                Spider.create(atProcess)
                        .addUrl("https://atcoder.jp/users/"+id+"/history")
                        .addPipeline(atPipeline)
                        .run();

                // 爬取每个学生Atcoder参赛历史
                Spider.create(acRatingProcess)
                        .addUrl("https://atcoder.jp/users/"+id+"/history")
                        .addPipeline(acRatingPipeline)
                        .run();
                // Atcoder解题数据
                List<Accontest> accontests = accontestMapper.selectList(null);
                for (int j = 0; j < accontests.size(); j++) {
                    String contestUrl = accontests.get(j).getAcContestType();
                    String contestName = accontests.get(j).getAcContestName();

                    QueryWrapper<Acrating> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("ac_contest_name", contestName)
                            .eq("ac_user_id", id);
                    Acrating exist = acratingMapper.selectOne(queryWrapper);
                    if (exist != null) {
                        Spider.create(acSubmissionProcess)
                                .addUrl("https://atcoder.jp" + contestUrl + "/submissions?f.User=" + id)
                                .addPipeline(acSubmissionPipeline)
                                .run();
                    }
                }
            }
        }
    }
    @Scheduled(fixedRate = 1000*60*60*24*7)   //定时器定义，设置执行时间
    private void CfProcess() {
        getCfData.getInfo();
        getCfData.getStatus();
        getCfData.getContest();
    }

    @Scheduled(fixedRate =  1000*60*60*24*7)
    private void CfProblemProcess() { getCfData.getProblem(); }

    @Scheduled(fixedRate = 1000*60*60*24*7)
    private void CfSubmission() { getCfData.getSubmission(); }

}

