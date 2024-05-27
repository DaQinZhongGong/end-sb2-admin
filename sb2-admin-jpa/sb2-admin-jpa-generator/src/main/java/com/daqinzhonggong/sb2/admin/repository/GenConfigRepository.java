package com.daqinzhonggong.sb2.admin.repository;

import com.daqinzhonggong.sb2.admin.domain.GenConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenConfigRepository extends JpaRepository<GenConfig, Long> {

  /**
   * 查询表配置
   *
   * @param tableName 表名
   * @return /
   */
  GenConfig findByTableName(String tableName);
}
