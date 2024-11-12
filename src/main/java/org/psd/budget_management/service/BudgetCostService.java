package org.psd.budget_management.service;

import org.psd.budget_management.entity.BudgetCost;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 针对表【budget_cost(预算费用)】的数据库操作Service
 *
 * @author pengshidun
 * @since 2024-11-12
 */
public interface BudgetCostService extends IService<BudgetCost> {
    /**
     * 批量修改预算费用的状态
     *
     * @param idList id列表
     * @param status 状态
     * @return 修改结果
     */
    Boolean updateStatus(List<Integer> idList, Integer status);
}
