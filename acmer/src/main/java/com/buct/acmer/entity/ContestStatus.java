package com.buct.acmer.entity;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@ApiModel(value = "比赛数据对象", description = "")
public class ContestStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    // 比赛名称
    private String contestName;

    // 时间
    private String date;

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

    // 解题数
    private Integer acNumber;

    // 补题数
    private Integer scNumber;

    // 排名
    private String ranks;

    // 当前积分
    private String newRating;

    // 旧积分
    private String oldRating;

    // 积分变化
    private Integer diff;
}
