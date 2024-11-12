package org.psd.budget_management.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.psd.budget_management.entity.BudgetItem;
import org.psd.budget_management.entity.BudgetItemGroup;
import org.psd.budget_management.entity.BudgetItemLog;
import org.psd.budget_management.entity.BudgetItemType;
import org.psd.budget_management.service.BudgetItemGroupService;
import org.psd.budget_management.service.BudgetItemLogService;
import org.psd.budget_management.service.BudgetItemService;
import org.psd.budget_management.mapper.BudgetItemMapper;
import org.psd.budget_management.service.BudgetItemTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
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
    @Resource
    private BudgetItemLogService budgetItemLogService;

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
     * 重写save方法，添加科目编码和状态
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
            entity.setStatus(1);
            super.updateById(entity);
            // 添加日志到日志表
            BudgetItemLog budgetItemLog = new BudgetItemLog();
            budgetItemLog.setBudgetItemId(entity.getId());
            budgetItemLog.setUpdateUser("张三");
            budgetItemLog.setUpdatePosition("主管");
            // 设置日志操作类型和内容
            budgetItemLog.setActionType("新增");
            budgetItemLog.setContent("新增预算科目: " + this.getById(entity.getId()));
            budgetItemLogService.save(budgetItemLog);
        }
        return result;
    }

    /**
     * 重写updateById方法，添加日志
     *
     * @param entity BudgetItem
     * @return 修改结果
     */
    @Override
    public boolean updateById(BudgetItem entity) {
        // 获取原始的BudgetItem对象
        BudgetItem originalBudgetItem = this.getById(entity.getId());
        boolean result = super.updateById(entity);
        if (result) {
            // 添加日志到日志表
            BudgetItemLog budgetItemLog = new BudgetItemLog();
            budgetItemLog.setBudgetItemId(entity.getId());
            budgetItemLog.setUpdateUser("张三");
            budgetItemLog.setUpdatePosition("主管");
            // 设置日志操作类型
            budgetItemLog.setActionType("编辑");
            // 构建日志内容
            entity = this.getById(entity.getId());
            String content = "原内容: " + originalBudgetItem + "\n" +
                    "编辑后内容: " + entity;
            budgetItemLog.setContent(content);
            budgetItemLogService.save(budgetItemLog);
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
            // 添加日志到日志表
            BudgetItemLog budgetItemLog = new BudgetItemLog();
            budgetItemLog.setBudgetItemId(id);
            budgetItemLog.setUpdateUser("张三");
            budgetItemLog.setUpdatePosition("主管");
            // 根据状态设置日志操作类型和内容
            if (1 == status) {
                budgetItemLog.setActionType("启用");
                budgetItemLog.setContent("启用预算科目");
            } else {
                budgetItemLog.setActionType("禁用");
                budgetItemLog.setContent("禁用预算科目");
            }
            budgetItemLogService.save(budgetItemLog);
        }
        return true;
    }

    /**
     * 重写removeByIds方法，添加删除日志
     *
     * @param list 需要删除的预算科目ids
     * @return 删除结果
     */
    @Override
    public boolean removeByIds(Collection<?> list) {
        // 添加日志到日志表
        for (Object id : list) {
            BudgetItem budgetItem = this.getById((Integer) id);
            BudgetItemLog budgetItemLog = new BudgetItemLog();
            budgetItemLog.setBudgetItemId(budgetItem.getId());
            budgetItemLog.setUpdateUser("张三");
            budgetItemLog.setUpdatePosition("主管");
            budgetItemLog.setActionType("删除");
            budgetItemLog.setContent("删除预算科目: " + budgetItem);
            budgetItemLogService.save(budgetItemLog);
        }
        return super.removeByIds(list);
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
