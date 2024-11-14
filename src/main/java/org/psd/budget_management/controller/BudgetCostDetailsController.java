package org.psd.budget_management.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.psd.budget_management.entity.BudgetCostDetails;
import org.psd.budget_management.entity.Result;
import org.psd.budget_management.service.BudgetCostDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 预算费用明细(BudgetCostDetails)表控制层
 *
 * @author pengshidun
 * @since 2024-11-12
 */
@RestController
@RequestMapping("budgetCostDetails")
public class BudgetCostDetailsController {
    /**
     * 服务对象
     */
    @Resource
    private BudgetCostDetailsService budgetCostDetailsService;

    /**
     * 分页查询所有数据
     *
     * @param page              分页对象
     * @param budgetCostDetails 查询实体
     * @return 所有数据
     */
    @GetMapping("findByConditions")
    public Result findByConditions(Page<BudgetCostDetails> page, BudgetCostDetails budgetCostDetails) {
        return Result.success(this.budgetCostDetailsService.page(page, new QueryWrapper<>(budgetCostDetails)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public Result findById(@PathVariable Serializable id) {
        return Result.success(this.budgetCostDetailsService.getById(id));
    }

    /**
     * 通过预算费用id查询数据
     *
     * @param budgetCostId 预算费用id
     * @return 返回该预算费用的所有明细
     */
    @GetMapping("/findByBudgetCostId/{budgetCostId}")
    public Result findByBudgetCostId(@PathVariable Integer budgetCostId) {
        return Result.success(this.budgetCostDetailsService.findByBudgetCostId(budgetCostId));
    }

    /**
     * 新增数据
     *
     * @param budgetCostDetails 实体对象
     * @return 新增结果
     */
    @PostMapping
    public Result insert(@RequestBody BudgetCostDetails budgetCostDetails) {
        return Result.success(this.budgetCostDetailsService.save(budgetCostDetails));
    }

    /**
     * 修改数据
     *
     * @param budgetCostDetails 实体对象
     * @return 修改结果
     */
    @PutMapping
    public Result update(@RequestBody BudgetCostDetails budgetCostDetails) {
        return Result.success(this.budgetCostDetailsService.updateById(budgetCostDetails));
    }

    /**
     * 批量删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public Result deleteByIds(@RequestParam("idList") List<Long> idList) {
        return Result.success(this.budgetCostDetailsService.removeByIds(idList));
    }
}
