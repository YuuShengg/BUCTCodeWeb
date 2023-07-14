package com.buct.spider.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * 
 * </p>
 *
 * @author BUCT
 * @since 2023-07-11
 */
@Getter
@Setter
@ToString
public class Accontest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 比赛id
     */
    @TableId(value = "ac_contest_id", type = IdType.INPUT)
    private Integer acContestId;

    /**
     * 比赛名称
     */
    private String acContestName;

    /**
     * 比赛类型
     */
    private String acContestType;

    /**
     * 比赛持续时间
     */
    private String acContestDurationseconds;

    /**
     * 比赛开始时间
     */
    private String acContestStarttimeseconds;

    /**
     * 比赛参加人数
     */
    private Integer acContestParticipantsnumber;


}
