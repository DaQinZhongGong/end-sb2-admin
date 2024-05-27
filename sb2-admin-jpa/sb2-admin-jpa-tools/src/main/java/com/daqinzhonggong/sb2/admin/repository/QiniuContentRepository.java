package com.daqinzhonggong.sb2.admin.repository;

import com.daqinzhonggong.sb2.admin.domain.QiniuContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface QiniuContentRepository extends JpaRepository<QiniuContent, Long>,
    JpaSpecificationExecutor<QiniuContent> {

  /**
   * 根据key查询
   *
   * @param key 文件名
   * @return QiniuContent
   */
  QiniuContent findByKey(String key);

}
