package org.psd.budget_management.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.psd.budget_management.entity.BudgetItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 针对表【budget_item(预算科目)】的数据库操作Mapper
 *
 * @author pengshidun
 * @since 2024-11-11
 */
@Mapper
public interface BudgetItemMapper extends BaseMapper<BudgetItem> {

}
