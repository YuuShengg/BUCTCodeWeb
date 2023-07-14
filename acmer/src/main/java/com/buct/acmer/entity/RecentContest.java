package com.buct.acmer.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@ApiModel(value = "recentContest对象", description = "")
public class RecentContest implements Serializable {

    private  static final long serialVersionUID = 1L;

    @ApiModelProperty("比赛名称")
    private String contestName;

    @ApiModelProperty("比赛持续时间")
    private String contestDurationSeconds;

    @ApiModelProperty("比赛开始时间")
    private String contestStartTimeSeconds;

    @ApiModelProperty("比赛参加人数")
    private Integer contestParticipantsNumber;

    @ApiModelProperty("比赛路径")
    private String contestUrl;

}
