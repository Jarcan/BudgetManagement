package org.psd.budget_management.service;

import org.psd.budget_management.entity.BudgetCostDetails;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 针对表【budget_cost_details(预算费用明细)】的数据库操作Service
 *
 * @author pengshidun
 * @since 2024-11-12
 */
public interface BudgetCostDetailsService extends IService<BudgetCostDetails> {

    /**
     * 通过预算费用id查询数据
     *
     * @param budgetCostId 预算科目id
     * @return 返回该预算费用的所有明细
     */
    List<BudgetCostDetails> findByBudgetCostId(Integer budgetCostId);
}
