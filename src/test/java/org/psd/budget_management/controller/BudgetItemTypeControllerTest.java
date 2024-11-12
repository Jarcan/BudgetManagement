package org.psd.budget_management.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.psd.budget_management.entity.BudgetItemType;
import org.psd.budget_management.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@Slf4j
@SpringBootTest
class BudgetItemTypeControllerTest {

    @Autowired
    private BudgetItemTypeController budgetItemTypeController;

    @Test
    void testInsert() {
        BudgetItemType budgetItemType = new BudgetItemType();
        budgetItemType.setName("营销费用");
        System.out.println(budgetItemTypeController.insert(budgetItemType));
    }

    @Test
    void testFindById() {
        Integer id = 1;
        Result result = budgetItemTypeController.findById(id);
        log.info(result.toString());
    }
}