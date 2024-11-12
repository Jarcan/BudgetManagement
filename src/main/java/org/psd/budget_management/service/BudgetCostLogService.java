package org.psd.budget_management.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.psd.budget_management.entity.BudgetCostLog;

import java.util.List;

/**
 * 针对表【budget_cost_log(预算科目日志)】的数据库操作Service
 *
 * @author pengshidun
 * @since 2024-11-12
 */
public interface BudgetCostLogService extends IService<BudgetCostLog> {

    /**
     * 通过预算科目id查询单条数据
     *
     * @param budgetCostId 预算科目id
     * @return 返回该预算科目的所有日志
     */
    List<BudgetCostLog> findByBudgetCostId(Integer budgetCostId);
}