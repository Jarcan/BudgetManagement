package org.psd.budget_management.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.psd.budget_management.entity.BudgetCostLog;

/**
 * 针对表【budget_cost_log(预算科目日志)】的数据库操作Mapper
 *
 * @author pengshidun
 * @since 2024-11-12
 */
@Mapper
public interface BudgetCostLogMapper extends BaseMapper<BudgetCostLog> {

}