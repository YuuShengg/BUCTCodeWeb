package com.buct.spider.entity;

import java.io.Serializable;
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
public class Acrating implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 比赛名称
     */
    private String acContestName;

    /**
     * 比赛日期
     */
    private String acContestDate;

    /**
     * 用户名称
     */
    private String acUserId;

    /**
     * 用户排名
     */
    private String acRank;

    /**
     * 用户表现
     */
    private String acPerformance;

    /**
     * 积分变化
     */
    private String acDiff;

    /**
     * 旧积分
     */
    private String acOldRating;

    /**
     * 新积分
     */
    private String acNewRating;

    /**
     * 解题数
     */
    private Integer acAcNumber;

    /**
     * 补题数
     */
    private Integer acScNumber;


}
