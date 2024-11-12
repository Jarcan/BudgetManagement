package org.psd.budget_management.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 预算科目日志
 *
 * @author pengshidun
 * @TableName budget_item_log
 * @since 2024-11-12
 */
@TableName(value = "budget_item_log")
@Data
public class BudgetItemLog implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "budget_item_id")
    private Integer budgetItemId;

    /**
     * 更新人姓名
     */
    @TableField(value = "update_user", fill = FieldFill.INSERT)
    private String updateUser;

    /**
     * 更新人职位
     */
    @TableField(value = "update_position", fill = FieldFill.INSERT)
    private String updatePosition;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 操作类型
     */
    @TableField(value = "action_type")
    private String actionType;

    /**
     * 更新内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 是否删除
     */
    @TableField(value = "is_deleted", fill = FieldFill.INSERT)
    private Integer isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}