package org.psd.budget_management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.psd.budget_management.entity.BudgetCostLog;
import org.psd.budget_management.mapper.BudgetCostLogMapper;
import org.psd.budget_management.service.BudgetCostLogService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 针对表【budget_item_log(预算科目日志)】的数据库操作Service实现
 *
 * @author pengshidun
 * @since 2024-11-12
 */
@Service
public class BudgetCostLogServiceImpl extends ServiceImpl<BudgetCostLogMapper, BudgetCostLog>
        implements BudgetCostLogService {

    /**
     * 通过预算科目id查询单条数据
     *
     * @param budgetCostId 预算科目id
     * @return 返回该预算科目的所有日志
     */
    @Override
    public List<BudgetCostLog> findByBudgetCostId(Integer budgetCostId) {
        LambdaQueryWrapper<BudgetCostLog> budgetCostLogLambdaQueryWrapper = new LambdaQueryWrapper<>();
        return super.list(budgetCostLogLambdaQueryWrapper.eq(BudgetCostLog::getBudgetCostId, budgetCostId));
    }
}