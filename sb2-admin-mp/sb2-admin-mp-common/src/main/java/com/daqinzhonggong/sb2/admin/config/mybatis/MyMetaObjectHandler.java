package com.daqinzhonggong.sb2.admin.config.mybatis;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.daqinzhonggong.sb2.admin.utils.SecurityUtils;
import java.sql.Timestamp;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

  @Override
  public void insertFill(MetaObject metaObject) {
    /* 创建时间 */
    this.strictInsertFill(metaObject, "createTime", Timestamp.class, DateTime.now().toTimestamp());
    this.strictInsertFill(metaObject, "updateTime", Timestamp.class, DateTime.now().toTimestamp());
    /* 操作人 */
    String username = "System";
    try {
      username = SecurityUtils.getCurrentUsername();
    } catch (Exception ignored) {
    }
    this.strictInsertFill(metaObject, "createBy", String.class, username);
    this.strictInsertFill(metaObject, "updateBy", String.class, username);
  }

  @Override
  public void updateFill(MetaObject metaObject) {
    /* 更新时间 */
    this.strictUpdateFill(metaObject, "updateTime", Timestamp.class, DateTime.now().toTimestamp());
    /* 操作人 */
    String username = "System";
    try {
      username = SecurityUtils.getCurrentUsername();
    } catch (Exception ignored) {
    }
    this.strictUpdateFill(metaObject, "updateBy", String.class, username);
  }

}

