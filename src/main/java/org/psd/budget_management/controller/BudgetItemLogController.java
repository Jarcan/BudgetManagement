package org.psd.budget_management.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.psd.budget_management.entity.BudgetItemLog;
import org.psd.budget_management.entity.Result;
import org.psd.budget_management.service.BudgetItemLogService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 预算科目日志(BudgetItemLog)表控制层
 *
 * @author pengshidun
 * @since 2024-11-12
 */
@RestController
@RequestMapping("budgetItemLog")
public class BudgetItemLogController {
    /**
     * 服务对象
     */
    @Resource
    private BudgetItemLogService budgetItemLogService;

    /**
     * 分页查询所有数据
     *
     * @param page          分页对象
     * @param budgetItemLog 查询实体
     * @return 所有数据
     */
    @GetMapping("findByConditions")
    public Result findByConditions(Page<BudgetItemLog> page, BudgetItemLog budgetItemLog) {
        return Result.success(this.budgetItemLogService.page(page, new QueryWrapper<>(budgetItemLog)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public Result findById(@PathVariable Serializable id) {
        return Result.success(this.budgetItemLogService.getById(id));
    }

    /**
     * 通过预算科目id查询单条数据
     *
     * @param budgetItemId 预算科目id
     * @return 返回该预算科目的所有日志
     */
    @GetMapping("/findByBudgetItemId/{budgetItemId}")
    public Result findByBudgetItemId(@PathVariable Integer budgetItemId) {
        return Result.success(this.budgetItemLogService.findByBudgetItemId(budgetItemId));
    }

    /**
     * 新增数据
     *
     * @param budgetItemLog 实体对象
     * @return 新增结果
     */
    @PostMapping
    public Result create(@RequestBody BudgetItemLog budgetItemLog) {
        return Result.success(this.budgetItemLogService.save(budgetItemLog));
    }

    /**
     * 修改数据
     *
     * @param budgetItemLog 实体对象
     * @return 修改结果
     */
    @PutMapping
    public Result update(@RequestBody BudgetItemLog budgetItemLog) {
        return Result.success(this.budgetItemLogService.updateById(budgetItemLog));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public Result deleteByIds(@RequestParam("idList") List<Integer> idList) {
        return Result.success(this.budgetItemLogService.removeByIds(idList));
    }
}
