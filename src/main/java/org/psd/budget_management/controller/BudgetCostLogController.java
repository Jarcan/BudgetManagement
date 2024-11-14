package org.psd.budget_management.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.psd.budget_management.entity.BudgetCostLog;
import org.psd.budget_management.entity.Result;
import org.psd.budget_management.service.BudgetCostLogService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 预算费用日志(BudgetCostLog)表控制层
 *
 * @author pengshidun
 * @since 2024-11-12
 */
@RestController
@RequestMapping("budgetCostLog")
public class BudgetCostLogController {
    /**
     * 服务对象
     */
    @Resource
    private BudgetCostLogService budgetCostLogService;

    /**
     * 分页查询所有数据
     *
     * @param page          分页对象
     * @param budgetCostLog 查询实体
     * @return 所有数据
     */
    @GetMapping("findByConditions")
    public Result findByConditions(Page<BudgetCostLog> page, BudgetCostLog budgetCostLog) {
        return Result.success(this.budgetCostLogService.page(page, new QueryWrapper<>(budgetCostLog)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public Result findById(@PathVariable Serializable id) {
        return Result.success(this.budgetCostLogService.getById(id));
    }

    /**
     * 通过预算费用id查询单条数据
     *
     * @param budgetCostId 预算费用id
     * @return 返回该预算费用的所有日志
     */
    @GetMapping("/findByBudgetCostId/{budgetCostId}")
    public Result findByBudgetCostId(@PathVariable Integer budgetCostId) {
        return Result.success(this.budgetCostLogService.findByBudgetCostId(budgetCostId));
    }

    /**
     * 新增数据
     *
     * @param budgetCostLog 实体对象
     * @return 新增结果
     */
    @PostMapping
    public Result create(@RequestBody BudgetCostLog budgetCostLog) {
        return Result.success(this.budgetCostLogService.save(budgetCostLog));
    }

    /**
     * 修改数据
     *
     * @param budgetCostLog 实体对象
     * @return 修改结果
     */
    @PutMapping
    public Result update(@RequestBody BudgetCostLog budgetCostLog) {
        return Result.success(this.budgetCostLogService.updateById(budgetCostLog));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public Result deleteByIds(@RequestParam("idList") List<Integer> idList) {
        return Result.success(this.budgetCostLogService.removeByIds(idList));
    }
}
