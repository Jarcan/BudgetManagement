package org.psd.budget_management.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 自定义元数据对象处理器，用来自动填充创建时间、更新时间等
 *
 * @author pengshidun
 * @since 2024-11-11
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    /**
     * 插入操作自动填充
     *
     * @param metaObject 填充对象
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("公共字段自动填充[insert]...");
        log.info(metaObject.toString());
        // 填充创建时间
        metaObject.setValue("createTime", LocalDateTime.now());
        // 填充更新时间
        metaObject.setValue("updateTime", LocalDateTime.now());
        // 填充是否删除
        metaObject.setValue("isDeleted", 0);
        // 填充状态
        metaObject.setValue("status", 1);
    }

    /**
     * 更新操作自动填充
     *
     * @param metaObject 填充对象
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("公共字段自动填充[update]...");
        log.info(metaObject.toString());
        // 填充更新时间
        metaObject.setValue("updateTime", LocalDateTime.now());
    }
}
