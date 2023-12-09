package com.tencent.wxcloudrun.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import java.time.LocalDateTime;
import java.util.Date;

public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        // 插入操作时的自动填充逻辑
        this.strictInsertFill(metaObject, "CreateTime", Date.class, new Date()); // 填充创建时间
        this.strictInsertFill(metaObject, "UpdateTime", Date.class, new Date()); // 填充更新时间
        this.strictInsertFill(metaObject, "CreateUser", String.class, "sysUser"); // 填充创建人
        this.strictInsertFill(metaObject, "UpdateUser", String.class, "sysUser"); // 填充更新人
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // 更新操作时的自动填充逻辑
        this.strictUpdateFill(metaObject, "UpdateTime", Date.class, new Date()); // 填充更新时间
        this.strictInsertFill(metaObject, "UpdateUser", String.class, "sysUser"); // 填充更新人

    }
}
