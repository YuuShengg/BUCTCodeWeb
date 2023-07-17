package com.buct.acmer.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.buct.acmer.entity.Cfrating;
import com.buct.acmer.entity.Cfsubmission;
import com.buct.acmer.entity.PublicProperty;
import com.buct.acmer.entity.Student;
import com.buct.acmer.mapper.CfsubmissionMapper;
import com.buct.acmer.mapper.StudentMapper;
import com.buct.acmer.service.impl.CfsubmissionServiceImpl;
import com.buct.acmer.service.impl.StudentServiceImpl;
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
 * @since 2023-07-15
 */
@Api(tags = "cfSubmission")
@RestController
@RequestMapping("/acmer/cfsubmission")
public class CfsubmissionController {

    @Resource
    private CfsubmissionServiceImpl cfsubmissionService;
    @Resource
    private StudentMapper studentMapper;
    @Resource
    private CfsubmissionMapper cfsubmissionMapper;

    @ApiOperation("按学号查询该学生全部cf比赛的提交数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage",value = "当前页数",dataType = "int",required = true),
            @ApiImplicitParam(name = "pageSize",value = "页面大小",dataType = "int",required = true),
            @ApiImplicitParam(name = "stuNo",value = "学号",dataType = "String",required = true)})

    @GetMapping("/{stuNo}/{currentPage}/{pageSize}")
    public PublicProperty<Page<Cfsubmission>> selectBase(@PathVariable("currentPage") Integer currentPage,
                                                     @PathVariable("pageSize") Integer pageSize,
                                                     @PathVariable("stuNo") String stuNo){

        Student student = studentMapper.selectById(stuNo);
        String cfId = student.getStuCfId();
        Page<Cfsubmission> page = new Page<>(currentPage, pageSize);
        QueryWrapper<Cfsubmission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cf_user_id", cfId);
        Page<Cfsubmission> result = cfsubmissionMapper.selectPage(page, queryWrapper);
        return new PublicProperty(200,"success", result);
    }

    @ApiOperation("按学号以及比赛Id查询该学生某场cf比赛的提交数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage",value = "当前页数",dataType = "int",required = true),
            @ApiImplicitParam(name = "pageSize",value = "页面大小",dataType = "int",required = true),
            @ApiImplicitParam(name = "stuNo",value = "学号",dataType = "String",required = true),
            @ApiImplicitParam(name = "contestId",value = "比赛Id",dataType = "String",required = true)
    })
    @GetMapping("/{contestId}/{stuNo}/{currentPage}/{pageSize}")
    public PublicProperty<Page<Cfsubmission>> selectBase(@PathVariable("currentPage") Integer currentPage,
                                                         @PathVariable("pageSize") Integer pageSize,
                                                         @PathVariable("contestId") String contestId,
                                                         @PathVariable("stuNo") String stuNo){

        Student student = studentMapper.selectById(stuNo);
        String cfId = student.getStuCfId();
        Page<Cfsubmission> page = cfsubmissionService.selectByContestIdAndStuNo(currentPage, pageSize, contestId, cfId);
        return new PublicProperty(200,"success",page);
    }

    @ApiOperation("按比赛Id查询某场cf比赛的提交数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage",value = "当前页数",dataType = "int",required = true),
            @ApiImplicitParam(name = "pageSize",value = "页面大小",dataType = "int",required = true),
            @ApiImplicitParam(name = "contestId",value = "比赛Id",dataType = "String",required = true)
    })
    @GetMapping("/contest/{contestId}/{currentPage}/{pageSize}")
    public PublicProperty<Page<Cfsubmission>> selectContest(@PathVariable("currentPage") Integer currentPage,
                                                         @PathVariable("pageSize") Integer pageSize,
                                                         @PathVariable("contestId") String contestId){

        Page<Cfsubmission> res = new Page<>(currentPage,pageSize);
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cf_contest_id",contestId);
        Page<Cfsubmission> page = cfsubmissionMapper.selectPage(res, queryWrapper);
        return new PublicProperty(200,"success",page);
    }
}
