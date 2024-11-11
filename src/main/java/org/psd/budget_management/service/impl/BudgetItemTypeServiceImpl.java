package org.psd.budget_management.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.psd.budget_management.entity.BudgetItemType;
import org.psd.budget_management.service.BudgetItemTypeService;
import org.psd.budget_management.mapper.BudgetItemTypeMapper;
import org.springframework.stereotype.Service;

/**
 * 针对表【budget_item_type(预算科目类型)】的数据库操作Service实现
 *
 * @author pengshidun
 * @since 2024-11-11
 */
@Service
public class BudgetItemTypeServiceImpl extends ServiceImpl<BudgetItemTypeMapper, BudgetItemType> implements BudgetItemTypeService {

}
