package org.psd.budget_management.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 预算费用
 *
 * @author pengshidun
 * @TableName budget_cost
 * @since 2024-11-12
 */
@TableName(value = "budget_cost")
@Data
public class BudgetCost implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 费用预算编码
     */
    @TableField(value = "code")
    private String code;

    /**
     * 费用预算类型
     */
    @TableField(value = "type")
    private String type;

    /**
     * 预算来源
     */
    @TableField(value = "source")
    private String source;

    /**
     * 年份
     */
    @TableField(value = "year")
    private Integer year;

    /**
     * 季度
     */
    @TableField(value = "quarter")
    private Integer quarter;

    /**
     * 月份
     */
    @TableField(value = "month")
    private Integer month;

    /**
     * 预算科目id
     */
    @TableField(value = "budget_item_id")
    private Integer budgetItemId;

    /**
     * 组织
     */
    @TableField(value = "organization")
    private String organization;

    /**
     * 渠道
     */
    @TableField(value = "channel")
    private String channel;

    /**
     * 客户
     */
    @TableField(value = "customer")
    private String customer;

    /**
     * 门店
     */
    @TableField(value = "store")
    private String store;

    /**
     * 产品层级
     */
    @TableField(value = "product_hierarchy")
    private String productHierarchy;

    /**
     * 产品
     */
    @TableField(value = "product")
    private String product;

    /**
     * 期初金额（现金）
     */
    @TableField(value = "initial_amount_cash")
    private BigDecimal initialAmountCash;

    /**
     * 可用余额（现金）
     */
    @TableField(value = "available_balance_cash")
    private BigDecimal availableBalanceCash;

    /**
     * 期初金额（物料）
     */
    @TableField(value = "initial_amount_material")
    private BigDecimal initialAmountMaterial;

    /**
     * 可用余额（物料）
     */
    @TableField(value = "available_balance_material")
    private BigDecimal availableBalanceMaterial;

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
     * 预算科目编码
     */
    @TableField(exist = false)
    private String budgetItemCode;

    /**
     * 预算科目名称
     */
    @TableField(exist = false)
    private String budgetItemName;
}