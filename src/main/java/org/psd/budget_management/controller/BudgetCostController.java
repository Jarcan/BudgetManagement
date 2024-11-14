package org.psd.budget_management.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.psd.budget_management.entity.BudgetCost;
import org.psd.budget_management.entity.Result;
import org.psd.budget_management.service.BudgetCostService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 预算费用(BudgetCost)表控制层
 *
 * @author pengshidun
 * @since 2024-11-12
 */
@RestController
@RequestMapping("budgetCost")
public class BudgetCostController {
    /**
     * 服务对象
     */
    @Resource
    private BudgetCostService budgetCostService;

    /**
     * 分页查询所有数据
     *
     * @param page       分页对象
     * @param budgetCost 查询实体
     * @return 所有数据
     */
    @GetMapping("findByConditions")
    public Result findByConditions(Page<BudgetCost> page, BudgetCost budgetCost) {
        return Result.success(this.budgetCostService.page(page, new QueryWrapper<>(budgetCost)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public Result findById(@PathVariable Serializable id) {
        return Result.success(this.budgetCostService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param budgetCost 实体对象
     * @return 新增结果
     */
    @PostMapping
    public Result create(@Valid @RequestBody BudgetCost budgetCost) {
        return this.budgetCostService.create(budgetCost);
    }

    /**
     * 修改数据
     *
     * @param budgetCost 实体对象
     * @return 修改结果
     */
    @PutMapping
    public Result update(@RequestBody BudgetCost budgetCost) {
        return Result.success(this.budgetCostService.updateById(budgetCost));
    }

    /**
     * 批量修改状态
     *
     * @param idList 主键结合
     * @return status 状态
     */
    @PutMapping("status")
    public Result updateStatus(@RequestParam("idList") List<Integer> idList, @RequestParam("status") Integer status) {
        return Result.success(this.budgetCostService.updateStatus(idList, status));
    }

    /**
     * 变更预算费用金额
     *
     * @param budgetCostId   预算费用id
     * @param budgetCostType 预算类型
     * @param changeType     变更类型
     * @param amount         金额
     * @param description    描述
     * @return status 变更结果
     */
    @PutMapping("changeBudgetCost")
    public Result changeBudgetCost(Integer budgetCostId, String budgetCostType, String changeType, BigDecimal amount, String description) {
        return this.budgetCostService.changeBudgetCost(budgetCostId, budgetCostType, changeType, amount, description);
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
    @PutMapping("adjust")
    public Result adjust(Integer budgetCostId, Integer targetBudgetCostId, String budgetCostType, BigDecimal amount, String description) {
        return this.budgetCostService.adjust(budgetCostId, targetBudgetCostId, budgetCostType, amount, description);
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public Result deleteByIds(@RequestParam("idList") List<Integer> idList) {
        return Result.success(this.budgetCostService.removeByIds(idList));
    }
}
