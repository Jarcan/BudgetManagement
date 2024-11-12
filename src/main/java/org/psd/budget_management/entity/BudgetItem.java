package org.psd.budget_management.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 预算科目
 *
 * @author pengshidun
 * @TableName budget_item
 * @since 2024-11-11
 */
@TableName(value = "budget_item")
@Data
public class BudgetItem implements Serializable {
    /**
     * 预算科目id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 预算科目编码
     */
    @TableField(value = "code")
    private String code;

    /**
     * 预算科目名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 预算科目类型
     */
    @TableField(value = "type_id")
    private Integer typeId;

    /**
     * 预算科目分组
     */
    @TableField(value = "group_id")
    private Integer groupId;

    /**
     * 控制类型
     */
    @TableField(value = "control_type")
    private String controlType;

    /**
     * 备注
     */
    @TableField(value = "note")
    private String note;

    /**
     * 状态
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 是否删除
     */
    @TableField(value = "is_deleted", fill = FieldFill.INSERT)
    private Integer isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 预算科目类型名称
     */
    @TableField(exist = false)
    private String typeName;

    /**
     * 预算科目分组名称
     */
    @TableField(exist = false)
    private String groupName;
}