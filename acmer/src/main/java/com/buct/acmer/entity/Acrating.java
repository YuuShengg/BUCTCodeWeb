package com.buct.acmer.entity;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author BUCT
 * @since 2023-07-13
 */
@Getter
@Setter
@ApiModel(value = "Acrating对象", description = "")
public class Acrating implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("比赛名称")
    private String acContestName;

    @ApiModelProperty("比赛日期")
    private String acContestDate;

    @ApiModelProperty("用户名称")
    private String acUserId;

    @ApiModelProperty("用户排名")
    private String acRank;

    @ApiModelProperty("用户表现")
    private String acPerformance;

    @ApiModelProperty("积分变化")
    private String acDiff;

    @ApiModelProperty("旧积分")
    private String acOldRating;

    @ApiModelProperty("新积分")
    private String acNewRating;

    @ApiModelProperty("解题数")
    private Integer acAcNumber;

    @ApiModelProperty("补题数")
    private Integer acScNumber;


}
