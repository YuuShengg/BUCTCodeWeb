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
@ApiModel(value = "Cfrating对象", description = "")
public class Cfrating implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("比赛id")
    private String cfContestId;

    @ApiModelProperty("比赛名称")
    private String cfContestName;

    @ApiModelProperty("用户名称")
    private String cfUserId;

    @ApiModelProperty("用户排名")
    private String cfRank;

    @ApiModelProperty("更新时间")
    private String cfUpdateTime;

    @ApiModelProperty("旧积分")
    private String cfOldRating;

    @ApiModelProperty("新积分")
    private String cfNewRating;

    @ApiModelProperty("解题数")
    private Integer cfAcNumber;

    @ApiModelProperty("补题数")
    private Integer cfScNumber;


}
