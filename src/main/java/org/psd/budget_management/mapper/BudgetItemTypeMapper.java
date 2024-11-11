package org.psd.budget_management.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.psd.budget_management.entity.BudgetItemType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.io.Serializable;
import java.util.Collection;

/**
 * 针对表【budget_item_type(预算科目类型)】的数据库操作Mapper
 *
 * @author pengshidun
 * @since 2024-11-11
 */
@Mapper
public interface BudgetItemTypeMapper extends BaseMapper<BudgetItemType> {
    @Override
    @Delete("UPDATE budget_item_type SET is_deleted = 1 WHERE id in #{id}")
    int deleteById(Serializable id);



}
