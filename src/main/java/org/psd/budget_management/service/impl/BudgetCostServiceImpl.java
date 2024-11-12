package org.psd.budget_management.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.psd.budget_management.entity.BudgetCost;
import org.psd.budget_management.entity.BudgetCostLog;
import org.psd.budget_management.entity.BudgetItem;
import org.psd.budget_management.service.BudgetCostLogService;
import org.psd.budget_management.service.BudgetCostService;
import org.psd.budget_management.mapper.BudgetCostMapper;
import org.psd.budget_management.service.BudgetItemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 针对表【budget_cost(预算费用)】的数据库操作Service实现
 *
 * @author pengshidun
 * @since 2024-11-12
 */
@Service
public class BudgetCostServiceImpl extends ServiceImpl<BudgetCostMapper, BudgetCost> implements BudgetCostService {
    @Resource
    private BudgetItemService budgetItemService;
    @Resource
    private BudgetCostLogService budgetCostLogService;

    /**
     * 重写page方法，查出其他数据（预算科目编码、预算科目名称等）
     *
     * @param page 分页对象
     * @return 预算科目
     */
    @Override
    public <E extends IPage<BudgetCost>> E page(E page, Wrapper<BudgetCost> queryWrapper) {
        // 查出基本数据
        E budgetCosts = super.page(page, queryWrapper);
        // 查出其他数据（预算科目编码、预算科目名称等）
        budgetCosts.setRecords(budgetCosts.getRecords().stream().map(this::addOtherData).collect(Collectors.toList()));
        return budgetCosts;
    }

    /**
     * 重写根据id查询方法，查出其他数据（预算科目编码、预算科目名称等）
     *
     * @param id 主键id
     * @return BudgetCost
     */
    @Override
    public BudgetCost getById(Serializable id) {
        // 查出基本数据
        BudgetCost budgetCost = super.getById(id);
        // 查出其他数据（预算科目编码、预算科目名称等）
        budgetCost = addOtherData(budgetCost);
        return budgetCost;
    }

    /**
     * 重写save方法，添加预算费用编码和状态
     *
     * @param entity BudgetCost
     * @return 是否成功
     */
    @Override
    public boolean save(BudgetCost entity) {
        boolean result = super.save(entity);
        if (result) {
            // 添加预算费用编码，前两位为SRYS，后四位为自增id
            entity.setCode("SRYS" + String.format("%04d", entity.getId()));
            entity.setStatus(1);
            super.updateById(entity);
            // 添加日志到日志表
            BudgetCostLog budgetCostLog = new BudgetCostLog();
            budgetCostLog.setBudgetCostId(entity.getId());
            budgetCostLog.setUpdateUser("张三");
            budgetCostLog.setUpdatePosition("主管");
            // 设置日志操作类型和内容
            budgetCostLog.setActionType("新增");
            budgetCostLog.setContent("新增预算费用: " + this.getById(entity.getId()));
            budgetCostLogService.save(budgetCostLog);
        }
        return result;
    }

    /**
     * 重写updateById方法，添加日志
     *
     * @param entity BudgetCost
     * @return 修改结果
     */
    @Override
    public boolean updateById(BudgetCost entity) {
        // 获取原始的BudgetItem对象
        BudgetCost originalBudgetCost = this.getById(entity.getId());
        boolean result = super.updateById(entity);
        if (result) {
            // 添加日志到日志表
            BudgetCostLog budgetCostLog = new BudgetCostLog();
            budgetCostLog.setBudgetCostId(entity.getId());
            budgetCostLog.setUpdateUser("张三");
            budgetCostLog.setUpdatePosition("主管");
            // 设置日志操作类型
            budgetCostLog.setActionType("编辑");
            // 构建日志内容
            entity = this.getById(entity.getId());
            String content = "原内容: " + originalBudgetCost + "\n" +
                    "编辑后内容: " + entity;
            budgetCostLog.setContent(content);
            budgetCostLogService.save(budgetCostLog);
        }
        return result;
    }

    /**
     * 批量修改预算费用的状态
     *
     * @param idList 需要更新状态的预算项ID列表，不应为null或包含null元素
     * @param status 新的状态码，将应用于所有指定的预算项
     * @return 修改结果
     */
    @Override
    public Boolean updateStatus(List<Integer> idList, Integer status) {
        // 遍历ID列表，更新每个预算费用的状态
        for (Integer id : idList) {
            // 创建一个新的BudgetCost对象，并设置其ID和状态
            BudgetCost budgetCost = new BudgetCost();
            budgetCost.setId(id);
            budgetCost.setStatus(status);
            // 调用父类的更新方法，根据ID更新数据库中的预算项记录
            super.updateById(budgetCost);
            // 添加日志到日志表
            BudgetCostLog budgetCostLog = new BudgetCostLog();
            budgetCostLog.setBudgetCostId(id);
            budgetCostLog.setUpdateUser("张三");
            budgetCostLog.setUpdatePosition("主管");
            // 根据状态设置日志操作类型和内容
            if (1 == status) {
                budgetCostLog.setActionType("启用");
                budgetCostLog.setContent("启用预算费用");
            } else {
                budgetCostLog.setActionType("禁用");
                budgetCostLog.setContent("禁用预算费用");
            }
            budgetCostLogService.save(budgetCostLog);
        }
        return true;
    }

    /**
     * 重写removeByIds方法，添加删除日志
     *
     * @param list 需要删除的预算费用ids
     * @return 删除结果
     */
    @Override
    public boolean removeByIds(Collection<?> list) {
        // 添加日志到日志表
        for (Object id : list) {
            BudgetCost budgetCost = this.getById((Integer) id);
            BudgetCostLog budgetCostLog = new BudgetCostLog();
            budgetCostLog.setBudgetCostId(budgetCost.getId());
            budgetCostLog.setUpdateUser("张三");
            budgetCostLog.setUpdatePosition("主管");
            budgetCostLog.setActionType("删除");
            budgetCostLog.setContent("删除预算费用: " + budgetCost);
            budgetCostLogService.save(budgetCostLog);
        }
        return super.removeByIds(list);
    }

    /**
     * 封装一个方法，查出其他数据（预算科目编码、预算科目名称等）
     *
     * @param budgetCost budgetCost
     * @return BudgetCost
     */
    public BudgetCost addOtherData(BudgetCost budgetCost) {
        if (budgetCost != null) {
            // 查出预算科目编码和名称
            BudgetItem budgetItem = budgetItemService.getById(budgetCost.getBudgetItemId());
            budgetCost.setBudgetItemCode(budgetItem != null ? budgetItem.getCode() : null);
            budgetCost.setBudgetItemName(budgetItem != null ? budgetItem.getName() : null);
        }
        return budgetCost;
    }
}
