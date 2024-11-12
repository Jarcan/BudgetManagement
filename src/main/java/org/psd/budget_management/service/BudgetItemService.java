package org.psd.budget_management.service;

import org.psd.budget_management.entity.BudgetItem;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 针对表【budget_item(预算科目)】的数据库操作Service
 *
 * @author pengshidun
 * @since 2024-11-11
 */
public interface BudgetItemService extends IService<BudgetItem> {

    /**
     * 批量修改预算科目的状态
     *
     * @param idList id列表
     * @param status 状态
     * @return 修改结果
     */
    Boolean updateStatus(List<Integer> idList, Integer status);
}
