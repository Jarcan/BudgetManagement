package org.psd.budget_management.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.psd.budget_management.entity.BudgetCost;
import org.psd.budget_management.entity.Result;
import org.psd.budget_management.service.BudgetCostService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
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
    @GetMapping
    public Result findByPage(Page<BudgetCost> page, BudgetCost budgetCost) {
        return new Result(200, "执行成功", this.budgetCostService.page(page, new QueryWrapper<>(budgetCost)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public Result findById(@PathVariable Serializable id) {
        return new Result(200, "执行成功", this.budgetCostService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param budgetCost 实体对象
     * @return 新增结果
     */
    @PostMapping
    public Result insert(@RequestBody BudgetCost budgetCost) {
        return new Result(200, "执行成功", this.budgetCostService.save(budgetCost));
    }

    /**
     * 修改数据
     *
     * @param budgetCost 实体对象
     * @return 修改结果
     */
    @PutMapping
    public Result update(@RequestBody BudgetCost budgetCost) {
        return new Result(200, "执行成功", this.budgetCostService.updateById(budgetCost));
    }

    /**
     * 批量修改状态
     *
     * @param idList 主键结合
     * @return status 状态
     */
    @PutMapping("status")
    public Result updateStatus(@RequestParam("idList") List<Integer> idList, @RequestParam("status") Integer status) {
        return new Result(200, "执行成功", this.budgetCostService.updateStatus(idList, status));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public Result deleteByIds(@RequestParam("idList") List<Integer> idList) {
        return new Result(200, "执行成功", this.budgetCostService.removeByIds(idList));
    }
}
