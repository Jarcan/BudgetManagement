package org.psd.budget_management.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.psd.budget_management.entity.BudgetCostDetails;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 针对表【budget_cost_details(预算费用明细)】的数据库操作Mapper
 *
 * @author pengshidun
 * @since 2024-11-12
 */
@Mapper
public interface BudgetCostDetailsMapper extends BaseMapper<BudgetCostDetails> {

}