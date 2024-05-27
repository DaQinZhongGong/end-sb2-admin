package com.daqinzhonggong.sb2.admin.repository;

import com.daqinzhonggong.sb2.admin.domain.ColumnInfo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColumnInfoRepository extends JpaRepository<ColumnInfo, Long> {

  /**
   * 查询表信息
   *
   * @param tableName 表格名
   * @return 表信息
   */
  List<ColumnInfo> findByTableNameOrderByIdAsc(String tableName);
}
