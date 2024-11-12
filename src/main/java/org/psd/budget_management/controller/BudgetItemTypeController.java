package org.psd.budget_management.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.psd.budget_management.entity.BudgetItemType;
import org.psd.budget_management.entity.Result;
import org.psd.budget_management.service.BudgetItemTypeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 预算科目类型(BudgetItemType)表控制层
 *
 * @author pengshidun
 * @since 2024-11-11
 */
@RestController
@RequestMapping("budgetItemType")
public class BudgetItemTypeController {
    /**
     * 服务对象
     */
    @Resource
    private BudgetItemTypeService budgetItemTypeService;

    /**
     * 分页查询所有数据
     *
     * @param page           分页对象
     * @param budgetItemType 查询实体
     * @return 所有数据
     */
    @GetMapping
    public Result findByPage(Page<BudgetItemType> page, BudgetItemType budgetItemType) {
        return new Result(200, "执行成功", this.budgetItemTypeService.page(page, new QueryWrapper<>(budgetItemType)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public Result findById(@PathVariable Serializable id) {
        return new Result(200, "执行成功", this.budgetItemTypeService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param budgetItemType 实体对象
     * @return 新增结果
     */
    @PostMapping
    public Result insert(@RequestBody BudgetItemType budgetItemType) {
        return new Result(200, "执行成功", this.budgetItemTypeService.save(budgetItemType));
    }

    /**
     * 修改数据
     *
     * @param budgetItemType 实体对象
     * @return 修改结果
     */
    @PutMapping
    public Result update(@RequestBody BudgetItemType budgetItemType) {
        return new Result(200, "执行成功", this.budgetItemTypeService.updateById(budgetItemType));
    }

    /**
     * 批量删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public Result deleteByIds(@RequestParam("idList") List<Integer> idList) {
        return new Result(200, "执行成功", this.budgetItemTypeService.removeByIds(idList));
    }
}
