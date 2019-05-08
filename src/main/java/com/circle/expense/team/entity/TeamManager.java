package com.circle.expense.team.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.circle.core.entity.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author yuan.liu
 * @since 2019-05-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("SYS_TEAM_MANAGER")
public class TeamManager extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableField("USER_ID")
    private Long userId;

    /**
     * 团队ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableField("TEAM_ID")
    private Long teamId;


    public TeamManager(){

    }

    public TeamManager(Long userId,Long teamId){
        this.userId = userId;
        this.teamId = teamId;
    }
}
