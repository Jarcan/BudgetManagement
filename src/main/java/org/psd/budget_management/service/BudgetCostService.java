package org.psd.budget_management.service;

import org.psd.budget_management.entity.BudgetCost;
import com.baomidou.mybatisplus.extension.service.IService;
import org.psd.budget_management.entity.Result;

import java.math.BigDecimal;
import java.util.List;

/**
 * 针对表【budget_cost(预算费用)】的数据库操作Service
 *
 * @author pengshidun
 * @since 2024-11-12
 */
public interface BudgetCostService extends IService<BudgetCost> {
    /**
     * 批量修改预算费用的状态
     *
     * @param idList id列表
     * @param status 状态
     * @return 修改结果
     */
    Boolean updateStatus(List<Integer> idList, Integer status);

    /**
     * 变更预算费用
     *
     * @param budgetCostId   预算费用id
     * @param budgetCostType 预算类型
     * @param changeType     变更类型
     * @param amount         金额
     * @param description    描述
     * @return status 变更结果
     */
    Result changeBudgetCost(Integer budgetCostId, String budgetCostType, String changeType, BigDecimal amount, String description);

    /**
     * 调整预算费用金额到另一个预算费用
     *
     * @param budgetCostId       预算费用ID，用于标识特定的预算费用记录
     * @param targetBudgetCostId 目标预算费用ID，用于关联调整操作的预算费用
     * @param budgetCostType     预算费用类型，描述费用的类别
     * @param amount             调整金额，要增加或减少的具体金额
     * @param description        描述，对此次调整的详细说明
     * @return 调整操作的结果，包括是否成功以及相关的消息
     */
    Result adjust(Integer budgetCostId, Integer targetBudgetCostId, String budgetCostType, BigDecimal amount, String description);
}
