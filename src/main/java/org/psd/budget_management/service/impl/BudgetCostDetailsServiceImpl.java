package org.psd.budget_management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.psd.budget_management.entity.BudgetCostDetails;
import org.psd.budget_management.mapper.BudgetCostMapper;
import org.psd.budget_management.service.BudgetCostDetailsService;
import org.psd.budget_management.mapper.BudgetCostDetailsMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 针对表【budget_cost_details(预算费用明细)】的数据库操作Service实现
 *
 * @author pengshidun
 * @since 2024-11-12
 */
@Service
public class BudgetCostDetailsServiceImpl extends ServiceImpl<BudgetCostDetailsMapper, BudgetCostDetails>
        implements BudgetCostDetailsService {
    @Resource
    private BudgetCostMapper budgetCostMapper;

    /**
     * 通过预算费用id查询数据
     *
     * @param budgetCostId 预算费用id
     * @return 返回该预算费用的所有明细
     */
    @Override
    public List<BudgetCostDetails> findByBudgetCostId(Integer budgetCostId) {
        // 根据预算科目id查出预算科目编码
        String code = budgetCostMapper.selectById(budgetCostId).getCode();
        // 根据预算科目编码查出所有明细
        LambdaQueryWrapper<BudgetCostDetails> budgetCostDetailsLambdaQueryWrapper = new LambdaQueryWrapper<>();
        budgetCostDetailsLambdaQueryWrapper.eq(BudgetCostDetails::getBudgetCostCode, code);
        return super.list(budgetCostDetailsLambdaQueryWrapper);
    }

}