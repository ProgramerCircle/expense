package com.circle.expense.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.circle.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author yuan.liu
 * @since 2019-04-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("SYS_USER")
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户昵称
     */
    @TableField("NAME")
    private String name;

    /**
     * 登陆账号
     */
    @TableField("LOGIN_NO")
    private String loginNo;

    /**
     * 用户密码
     */
    @TableField("PASSWORD")
    private String password;

    /**
     * 手机号码
     */
    @TableField("PHONE")
    private String phone;

    /**
     * 邮箱
     */
    @TableField("EMAIL")
    private String email;

    /**
     * 管理权限
     */
    @TableField("RANK")
    private Integer rank;

    /**
     * 团队Id
     */
    @TableField("TEAM_ID")
    private Long teamId;

}
