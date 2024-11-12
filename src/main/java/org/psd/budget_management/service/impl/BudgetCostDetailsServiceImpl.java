package org.psd.budget_management.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.psd.budget_management.entity.BudgetCostDetails;
import org.psd.budget_management.service.BudgetCostDetailsService;
import org.psd.budget_management.mapper.BudgetCostDetailsMapper;
import org.springframework.stereotype.Service;

/**
 * 针对表【budget_cost_details(预算费用明细)】的数据库操作Service实现
 *
 * @author pengshidun
 * @since 2024-11-12
 */
@Service
public class BudgetCostDetailsServiceImpl extends ServiceImpl<BudgetCostDetailsMapper, BudgetCostDetails>
        implements BudgetCostDetailsService {

}