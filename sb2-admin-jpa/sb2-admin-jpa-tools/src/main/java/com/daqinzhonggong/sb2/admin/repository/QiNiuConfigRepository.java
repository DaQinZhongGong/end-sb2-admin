package com.daqinzhonggong.sb2.admin.repository;

import com.daqinzhonggong.sb2.admin.domain.QiniuConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface QiNiuConfigRepository extends JpaRepository<QiniuConfig, Long> {

  /**
   * 编辑类型
   *
   * @param type
   */
  @Modifying
  @Query(value = "update QiniuConfig set type = ?1")
  void update(String type);

}
