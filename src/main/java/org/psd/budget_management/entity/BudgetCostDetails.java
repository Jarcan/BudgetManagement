package org.psd.budget_management.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 预算费用明细
 *
 * @author pengshidun
 * @TableName budget_cost_details
 * @since 2024-11-12
 */
@TableName(value = "budget_cost_details")
@Data
public class BudgetCostDetails implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 操作类型
     */
    @TableField(value = "operation_type")
    private String operationType;

    /**
     * 业务编码
     */
    @TableField(value = "budget_cost_code")
    private String budgetCostCode;

    /**
     * 科目
     */
    @TableField(value = "budget_item_name")
    private String budgetItemName;

    /**
     * 明细类型
     */
    @TableField(value = "type")
    private String type;

    /**
     * 组织名称
     */
    @TableField(value = "budget_cost_organization")
    private String budgetCostOrganization;

    /**
     * 渠道
     */
    @TableField(value = "budget_cost_channel")
    private String budgetCostChannel;

    /**
     * 客户名称
     */
    @TableField(value = "budget_cost_customer")
    private String budgetCostCustomer;

    /**
     * 门店名称
     */
    @TableField(value = "budget_cost_store")
    private String budgetCostStore;

    /**
     * 初期金额
     */
    @TableField(value = "initial_amount")
    private BigDecimal initialAmount;

    /**
     * 操作前余额
     */
    @TableField(value = "available_balance")
    private BigDecimal availableBalance;

    /**
     * 当前操作金额
     */
    @TableField(value = "amount")
    private BigDecimal amount;

    /**
     * 操作后余额
     */
    @TableField(value = "available_balance_after")
    private BigDecimal availableBalanceAfter;

    /**
     * 备注
     */
    @TableField(value = "description")
    private String description;

    /**
     * 操作人
     */
    @TableField(value = "operation_user")
    private String operationUser;

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
}