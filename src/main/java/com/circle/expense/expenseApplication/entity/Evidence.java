package com.circle.expense.expenseApplication.entity;

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
 * 凭证表
 * </p>
 *
 * @author yuan.liu
 * @since 2019-05-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("SYS_EVIDENCE")
public class Evidence extends BaseEntity {

    private static final long serialVersionUID = 1L;


    /**
     * 凭证存放路径
     */
    @TableField("PATH")
    private String path;


}
