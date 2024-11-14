package org.psd.budget_management.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.psd.budget_management.entity.BudgetItemGroup;
import org.psd.budget_management.entity.Result;
import org.psd.budget_management.service.BudgetItemGroupService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 预算科目分组(BudgetItemGroup)表控制层
 *
 * @author pengshidun
 * @since 2024-11-11
 */
@RestController
@RequestMapping("budgetItemGroup")
public class BudgetItemGroupController {
    /**
     * 服务对象
     */
    @Resource
    private BudgetItemGroupService budgetItemGroupService;

    /**
     * 分页查询所有数据
     *
     * @param page            分页对象
     * @param budgetItemGroup 查询实体
     * @return 所有数据
     */
    @GetMapping("findByConditions")
    public Result findByConditions(Page<BudgetItemGroup> page, BudgetItemGroup budgetItemGroup) {
        Page<BudgetItemGroup> page1 = this.budgetItemGroupService.page(page, new QueryWrapper<>(budgetItemGroup));
        return Result.success(page1);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public Result findById(@PathVariable Serializable id) {
        return Result.success(this.budgetItemGroupService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param budgetItemGroup 实体对象
     * @return 新增结果
     */
    @PostMapping
    public Result create(@RequestBody BudgetItemGroup budgetItemGroup) {
        return Result.success(this.budgetItemGroupService.save(budgetItemGroup));
    }

    /**
     * 修改数据
     *
     * @param budgetItemGroup 实体对象
     * @return 修改结果
     */
    @PutMapping
    public Result update(@RequestBody BudgetItemGroup budgetItemGroup) {
        return Result.success(this.budgetItemGroupService.updateById(budgetItemGroup));
    }

    /**
     * 批量删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public Result deleteByIds(@RequestParam("idList") List<Integer> idList) {
        return Result.success(this.budgetItemGroupService.removeByIds(idList));
    }
}