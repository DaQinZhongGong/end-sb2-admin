package com.daqinzhonggong.sb2.admin.modules.system.repository;

import com.daqinzhonggong.sb2.admin.modules.system.domain.DictDetail;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DictDetailRepository extends JpaRepository<DictDetail, Long>,
    JpaSpecificationExecutor<DictDetail> {

  /**
   * 根据字典名称查询
   *
   * @param name /
   * @return /
   */
  List<DictDetail> findByDictName(String name);

}