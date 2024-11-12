package org.psd.budget_management.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.psd.budget_management.entity.BudgetItemLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 针对表【budget_item_log(预算科目日志)】的数据库操作Mapper
 *
 * @author pengshidun
 * @since 2024-11-12
 */
@Mapper
public interface BudgetItemLogMapper extends BaseMapper<BudgetItemLog> {

}