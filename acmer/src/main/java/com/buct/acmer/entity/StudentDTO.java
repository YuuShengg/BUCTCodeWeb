package com.buct.acmer.entity;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
@ApiModel(value = "StudentDTO对象", description = "")
public class StudentDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    // 学号
    private String stuNo;

    // 姓名
    private String stuName;

    // 班级
    private String stuClass;

    // atcoder id
    private String stuAcId;

    // codeforce id
    private String stuCfId;

    // cf分数
    private String cfNewRating;

    // at分数
    private String acNewRating;

    // cf比赛次数
    private Integer cfSumContest;

    // at比赛次数
    private Integer acCount;

    // cf解题数
    private Integer cfAcNumberSum;

    // cf补题数
    private Integer cfScNumberSum;

    // at解题数
    private Integer atAcNumberSum;

    // at补题数
    private Integer atScNumberSum;
}
