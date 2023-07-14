package com.buct.acmer.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.buct.acmer.entity.Acrating;
import com.buct.acmer.entity.Cfrating;
import com.buct.acmer.entity.PublicProperty;
import com.buct.acmer.service.impl.AcratingServiceImpl;
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

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author BUCT
 * @since 2023-07-13
 */
@Api(tags = "学生At积分变化")
@RestController
@RequestMapping("/acmer/acrating")
public class AcratingController {

    @Resource
    private AcratingServiceImpl acratingService;

    @ApiOperation("查询所有学生At积分变化信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage",value = "当前页数",dataType = "int",required = true),
            @ApiImplicitParam(name = "pageSize",value = "页面大小",dataType = "int",required = true)
    })
    @GetMapping("/all/{currentPage}/{pageSize}")
    public PublicProperty<Page<Acrating>> selectBase(@PathVariable("currentPage") Integer currentPage,
                                                     @PathVariable("pageSize") Integer pageSize){

        Page<Acrating> page = new Page<>(currentPage,pageSize);
        return new PublicProperty(200,"success",acratingService.page(page));
    }

    @ApiOperation("按值查询学生At积分变化信息(按时间顺序升序排序)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage",value = "当前页数",dataType = "int",required = true),
            @ApiImplicitParam(name = "pageSize",value = "页面大小",dataType = "int",required = true),
            @ApiImplicitParam(name = "fieldName",value = "要查询的字段名",dataType = "String",required = true),
            @ApiImplicitParam(name = "fieldValue",value = "字段值",dataType = "String",required = true)
    })
    @GetMapping("/all/select/{currentPage}/{pageSize}/{fieldName}/{fieldValue}")
    public PublicProperty<Page<Acrating>> searchByField(@PathVariable("currentPage") Integer currentPage,
                                                        @PathVariable("pageSize") Integer pageSize,
                                                        @PathVariable("fieldName") String fieldName,
                                                        @PathVariable("fieldValue") String fieldValue) {

        Page<Acrating> page = acratingService.selectOrderByDate(currentPage,pageSize,fieldName,fieldValue);
        return new PublicProperty<>(200, "success", page);
    }
}
