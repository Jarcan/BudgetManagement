package org.psd.budget_management.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.psd.budget_management.entity.*;
import org.psd.budget_management.service.BudgetCostDetailsService;
import org.psd.budget_management.service.BudgetCostLogService;
import org.psd.budget_management.service.BudgetCostService;
import org.psd.budget_management.mapper.BudgetCostMapper;
import org.psd.budget_management.service.BudgetItemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.math.BigDecimal;
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
    @Resource
    private BudgetCostDetailsService budgetCostDetailsService;

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
            // 设置余额
            entity.setAvailableBalanceCash(entity.getInitialAmountCash() != null ? entity.getInitialAmountCash() : BigDecimal.ZERO);
            entity.setAvailableBalanceMaterial(entity.getInitialAmountMaterial() != null ? entity.getInitialAmountMaterial() : BigDecimal.ZERO);
            super.updateById(entity);
            // 获取最新的BudgetCost对象
            entity = this.getById(entity);
            // 添加日志到日志表
            BudgetCostLog budgetCostLog = new BudgetCostLog();
            budgetCostLog.setBudgetCostId(entity.getId());
            budgetCostLog.setUpdateUser("张三");
            budgetCostLog.setUpdatePosition("主管");
            // 设置日志操作类型和内容
            budgetCostLog.setActionType("新增");
            budgetCostLog.setContent("新增预算费用: " + this.getById(entity.getId()));
            budgetCostLogService.save(budgetCostLog);
            // 添加明细到明细表
            if (entity.getInitialAmountCash() != null) {
                entity.setAvailableBalanceCash(BigDecimal.ZERO);
                addBudgetCostDetails(entity, "期初", "管理员", "追加", entity.getInitialAmountCash(), "现金", null);
            }
            if (entity.getInitialAmountMaterial() != null) {
                entity.setAvailableBalanceMaterial(BigDecimal.ZERO);
                addBudgetCostDetails(entity, "期初", "管理员", "追加", entity.getInitialAmountMaterial(), "物料", null);
            }
        }
        return result;
    }

    /**
     * 添加一条预算费用明细
     *
     * @param budgetCost    BudgetCost预算费用实体
     * @param operationType 操作类型
     * @param operationUser 操作人
     * @param operation     操作
     * @param amount        金额
     * @param type          类型（现金、物料）
     */
    private void addBudgetCostDetails(BudgetCost budgetCost, String operationType, String operationUser, String operation, BigDecimal amount, String type, String description) {
        // 设置基本明细信息
        BudgetCostDetails budgetCostDetails = new BudgetCostDetails();
        budgetCostDetails.setOperationType(operationType);
        budgetCostDetails.setBudgetCostCode(budgetCost.getCode());
        budgetCostDetails.setBudgetItemName(budgetCost.getBudgetItemName());
        budgetCostDetails.setBudgetCostOrganization(budgetCost.getOrganization());
        budgetCostDetails.setBudgetCostChannel(budgetCost.getChannel());
        budgetCostDetails.setBudgetCostCustomer(budgetCost.getCustomer());
        budgetCostDetails.setBudgetCostStore(budgetCost.getStore());
        budgetCostDetails.setOperationUser(operationUser);
        budgetCostDetails.setDescription(description);
        // 设置金额类型、期初金额、操作前余额、操作金额、操作后余额
        if ("现金".equals(type)) {
            if ("追加".equals(operation)) {
                setBudgetCostDetails(budgetCostDetails, type, budgetCost.getInitialAmountCash(), budgetCost.getAvailableBalanceCash(), amount, budgetCost.getAvailableBalanceCash().add(amount));
            } else {
                setBudgetCostDetails(budgetCostDetails, type, budgetCost.getInitialAmountCash(), budgetCost.getAvailableBalanceCash(), amount, budgetCost.getAvailableBalanceCash().subtract(amount));
            }
        } else if ("物料".equals(type)) {
            if ("追加".equals(operation)) {
                setBudgetCostDetails(budgetCostDetails, type, budgetCost.getInitialAmountMaterial(), budgetCost.getAvailableBalanceMaterial(), amount, budgetCost.getAvailableBalanceMaterial().add(amount));
            } else {
                setBudgetCostDetails(budgetCostDetails, type, budgetCost.getInitialAmountMaterial(), budgetCost.getAvailableBalanceMaterial(), amount, budgetCost.getAvailableBalanceMaterial().subtract(amount));
            }
        }
        // 添加费用预算明细到数据库
        budgetCostDetailsService.save(budgetCostDetails);
    }

    /**
     * 设置金额类型、期初金额、操作前余额、操作金额、操作后余额
     *
     * @param budgetCostDetails     BudgetCostDetails明细实体
     * @param type                  金额类型
     * @param initialAmount         期初金额
     * @param availableBalance      操作前余额
     * @param amount                操作金额
     * @param availableBalanceAfter 操作后余额
     */
    private void setBudgetCostDetails(BudgetCostDetails budgetCostDetails, String type, BigDecimal initialAmount, BigDecimal availableBalance, BigDecimal amount, BigDecimal availableBalanceAfter) {
        budgetCostDetails.setType(type);
        budgetCostDetails.setInitialAmount(initialAmount);
        budgetCostDetails.setAvailableBalance(availableBalance);
        budgetCostDetails.setAmount(amount);
        budgetCostDetails.setAvailableBalanceAfter(availableBalanceAfter);
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
            String content = "原内容: " + originalBudgetCost + "\n" + "编辑后内容: " + entity;
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
     * 修改预算费用金额
     *
     * @param budgetCostId   预算费用ID
     * @param budgetCostType 预算费用类型，决定使用哪种预算变更处理逻辑
     * @param changeType     变更类型，可以是"追加"或"削减"
     * @param amount         变更金额，必须是非负数
     * @param description    变更描述，解释变更的原因或目的
     * @return 结果对象，包含状态码、消息和成功标志
     */
    @Override
    public Result changeBudgetCost(Integer budgetCostId, String budgetCostType, String changeType, BigDecimal amount, String description) {
        // 检查变更金额是否为负数
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            return new Result(400, "金额不能为负数", false);
        }
        // 根据ID获取预算费用对象
        BudgetCost budgetCost = this.getById(budgetCostId);
        if (budgetCost == null) {
            return new Result(404, "预算费用不存在", false);
        }
        // 尝试执行预算费用变更
        try {
            // 根据预算类型调用相应的方法
            switch (budgetCostType) {
                case "现金":
                    handleCashBudgetChange(budgetCost, changeType, amount, changeType, description);
                    break;
                case "物料":
                    handleMaterialBudgetChange(budgetCost, changeType, amount, changeType, description);
                    break;
                default:
                    return new Result(400, "不支持的预算类型", false);
            }
            // 更新预算费用记录
            this.updateById(budgetCost);
            return new Result(200, "预算费用变更成功", true);
        } catch (IllegalArgumentException e) {
            return new Result(400, e.getMessage(), false);
        }
    }

    /**
     * 调整预算费用金额到另一个预算费用
     *
     * @param budgetCostId       预算费用ID，用于标识特定的预算费用记录
     * @param targetBudgetCostId 目标预算费用ID，用于关联调整操作的预算费用
     * @param budgetCostType     预算费用类型，描述费用的类别
     * @param amount             调整金额，要增加或减少的具体金额
     * @param description        描述，对此次调整的详细说明
     * @return 调整操作的结果，包括是否成功以及相关的消息
     */
    @Override
    public Result adjust(Integer budgetCostId, Integer targetBudgetCostId, String budgetCostType, BigDecimal amount, String description) {
        // 输入验证
        if (budgetCostId == null || targetBudgetCostId == null || budgetCostType == null || amount == null) {
            return Result.error("输入参数不能为空");
        }
        // 检查变更金额是否为负数
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            return Result.error("金额不能为负数或0");
        }
        // 根据ID获取预算费用对象和目标对象
        BudgetCost budgetCost = this.getById(budgetCostId);
        BudgetCost targetBudgetCost = this.getById(targetBudgetCostId);
        if (budgetCost == null || targetBudgetCost == null) {
            return Result.error("预算费用或目标预算费用不存在");
        }
        // 尝试进行预算费用调整
        try {
            // 根据预算类型调用相应的方法
            switch (budgetCostType) {
                case "现金":
                    handleCashBudgetChange(budgetCost, "削减", amount, "调出", description);
                    handleCashBudgetChange(targetBudgetCost, "追加", amount, "调入", description);
                    break;
                case "物料":
                    handleMaterialBudgetChange(budgetCost, "削减", amount, "调出", description);
                    handleMaterialBudgetChange(targetBudgetCost, "追加", amount, "调入", description);
                    break;
                default:
                    return Result.error("不支持的预算类型");
            }
            // 更新预算费用记录
            this.updateById(budgetCost);
            this.updateById(targetBudgetCost);
            return Result.success(true);
        } catch (IllegalArgumentException e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 处理现金预算的变更
     *
     * @param budgetCost 预算费用对象
     * @param changeType 变更类型，可以是"追加"或"削减"
     * @param amount     变更金额
     * @throws IllegalArgumentException 如果变更类型不支持或预算费用不足
     */
    private void handleCashBudgetChange(BudgetCost budgetCost, String changeType, BigDecimal amount, String operationType, String description) {
        switch (changeType) {
            case "追加":
                addBudgetCostDetails(budgetCost, operationType, "管理员", "追加", amount, "现金", description);
                budgetCost.setAvailableBalanceCash(budgetCost.getAvailableBalanceCash().add(amount));
                break;
            case "削减":
                if (budgetCost.getAvailableBalanceCash().compareTo(amount) < 0) {
                    throw new IllegalArgumentException("预算费用不足");
                }
                addBudgetCostDetails(budgetCost, operationType, "管理员", "削减", amount, "现金", description);
                budgetCost.setAvailableBalanceCash(budgetCost.getAvailableBalanceCash().subtract(amount));
                break;
            default:
                throw new IllegalArgumentException("不支持的变更类型");
        }
    }

    /**
     * 处理物料预算的变更
     *
     * @param budgetCost 预算费用对象
     * @param changeType 变更类型，可以是"追加"或"削减"
     * @param amount     变更金额
     * @throws IllegalArgumentException 如果变更类型不支持或预算费用不足
     */
    private void handleMaterialBudgetChange(BudgetCost budgetCost, String changeType, BigDecimal amount, String operationType, String description) {
        switch (changeType) {
            case "追加":
                addBudgetCostDetails(budgetCost, operationType, "管理员", "追加", amount, "物料", description);
                budgetCost.setAvailableBalanceMaterial(budgetCost.getAvailableBalanceMaterial().add(amount));
                break;
            case "削减":
                if (budgetCost.getAvailableBalanceMaterial().compareTo(amount) < 0) {
                    throw new IllegalArgumentException("预算费用不足");
                }
                addBudgetCostDetails(budgetCost, operationType, "管理员", "削减", amount, "物料", description);
                budgetCost.setAvailableBalanceMaterial(budgetCost.getAvailableBalanceMaterial().subtract(amount));
                break;
            default:
                throw new IllegalArgumentException("不支持的变更类型");
        }
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
