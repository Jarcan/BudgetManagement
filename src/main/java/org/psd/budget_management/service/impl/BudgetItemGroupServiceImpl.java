package org.psd.budget_management.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.psd.budget_management.entity.BudgetItemGroup;
import org.psd.budget_management.service.BudgetItemGroupService;
import org.psd.budget_management.mapper.BudgetItemGroupMapper;
import org.springframework.stereotype.Service;

/**
 * 针对表【budget_item_group(预算科目分组)】的数据库操作Service实现
 *
 * @author pengshidun
 * @since 2024-11-11
 */
@Service
public class BudgetItemGroupServiceImpl extends ServiceImpl<BudgetItemGroupMapper, BudgetItemGroup> implements BudgetItemGroupService {

}
