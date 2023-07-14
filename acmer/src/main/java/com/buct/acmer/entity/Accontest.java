package com.buct.acmer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
@ApiModel(value = "Accontest对象", description = "")
public class Accontest implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("比赛id")
    @TableId(value = "ac_contest_id", type = IdType.AUTO)
    private Integer acContestId;

    @ApiModelProperty("比赛名称")
    private String acContestName;

    @ApiModelProperty("比赛类型")
    private String acContestType;

    @ApiModelProperty("比赛持续时间")
    private String acContestDurationseconds;

    @ApiModelProperty("比赛开始时间")
    private String acContestStarttimeseconds;

    @ApiModelProperty("比赛参加人数")
    private Integer acContestParticipantsnumber;

    @ApiModelProperty("比赛状态")
    private String acContestPhase;

}
