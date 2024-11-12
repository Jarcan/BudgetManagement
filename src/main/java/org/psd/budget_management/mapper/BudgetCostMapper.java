package org.psd.budget_management.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.psd.budget_management.entity.BudgetCost;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 针对表【budget_cost(预算费用)】的数据库操作Mapper
 *
 * @author pengshidun
 * @since 2024-11-12
 */
@Mapper
public interface BudgetCostMapper extends BaseMapper<BudgetCost> {

}
