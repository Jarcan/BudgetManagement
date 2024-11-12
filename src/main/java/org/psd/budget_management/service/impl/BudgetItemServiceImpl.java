package org.psd.budget_management.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.psd.budget_management.entity.BudgetItem;
import org.psd.budget_management.entity.BudgetItemGroup;
import org.psd.budget_management.entity.BudgetItemType;
import org.psd.budget_management.service.BudgetItemGroupService;
import org.psd.budget_management.service.BudgetItemService;
import org.psd.budget_management.mapper.BudgetItemMapper;
import org.psd.budget_management.service.BudgetItemTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 针对表【budget_item(预算科目)】的数据库操作Service实现
 *
 * @author pengshidun
 * @since 2024-11-11
 */
@Service
public class BudgetItemServiceImpl extends ServiceImpl<BudgetItemMapper, BudgetItem> implements BudgetItemService {
    @Resource
    private BudgetItemTypeService budgetItemTypeService;
    @Resource
    private BudgetItemGroupService budgetItemGroupService;

    /**
     * 重写page方法，查询出类型名称、组名称
     *
     * @param page 分页对象
     * @return 预算科目
     */
    @Override
    public <E extends IPage<BudgetItem>> E page(E page, Wrapper<BudgetItem> queryWrapper) {
        // 查出基本数据
        E budgetItems = super.page(page, queryWrapper);
        // 查出类型名称、组名称
        budgetItems.setRecords(budgetItems.getRecords().stream().map(this::addTypeNameAndGroupName).collect(Collectors.toList()));
        return budgetItems;
    }

    /**
     * 重写根据id查询方法，查询出类型名称、组名称
     *
     * @param id 主键id
     * @return BudgetItem
     */
    @Override
    public BudgetItem getById(Serializable id) {
        // 查出基本数据
        BudgetItem budgetItem = super.getById(id);
        // 查出类型名称、组名称
        budgetItem = addTypeNameAndGroupName(budgetItem);
        return budgetItem;
    }

    /**
     * 重写save方法，添加科目编码
     *
     * @param entity BudgetItem
     * @return 是否成功
     */
    @Override
    public boolean save(BudgetItem entity) {
        boolean result = super.save(entity);
        if (result) {
            // 添加科目编码，前两位为YS，后四位为自增id
            entity.setCode("YS" + String.format("%04d", entity.getId()));
            result = super.updateById(entity);
        }
        return result;
    }

    /**
     * 批量修改预算科目的状态
     *
     * @param idList 需要更新状态的预算项ID列表，不应为null或包含null元素
     * @param status 新的状态码，将应用于所有指定的预算项
     * @return 修改结果
     */
    @Override
    public Boolean updateStatus(List<Integer> idList, Integer status) {
        // 遍历ID列表，更新每个预算项的状态
        for (Integer id : idList) {
            // 创建一个新的BudgetItem对象，并设置其ID和状态
            BudgetItem budgetItem = new BudgetItem();
            budgetItem.setId(id);
            budgetItem.setStatus(status);
            // 调用父类的更新方法，根据ID更新数据库中的预算项记录
            super.updateById(budgetItem);
        }
        return true;
    }

    /**
     * 封装一个方法，查出类型名称、组名称
     *
     * @param budgetItem BudgetItem
     * @return BudgetItem
     */
    public BudgetItem addTypeNameAndGroupName(BudgetItem budgetItem) {
        if (budgetItem != null) {
            // 查出类型名称
            BudgetItemType budgetItemType = budgetItemTypeService.getById(budgetItem.getTypeId());
            budgetItem.setTypeName(budgetItemType != null ? budgetItemType.getName() : null);
            // 查出组名称
            BudgetItemGroup budgetItemGroup = budgetItemGroupService.getById(budgetItem.getGroupId());
            budgetItem.setGroupName(budgetItemGroup != null ? budgetItemGroup.getName() : null);
        }
        return budgetItem;
    }

}
