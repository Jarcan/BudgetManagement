package org.psd.budget_management.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.psd.budget_management.entity.BudgetItemType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * 针对表【budget_item_type(预算科目类型)】的数据库操作Mapper
 *
 * @author pengshidun
 * @since 2024-11-11
 */
@Mapper
@Repository
public interface BudgetItemTypeMapper extends BaseMapper<BudgetItemType> {

}
