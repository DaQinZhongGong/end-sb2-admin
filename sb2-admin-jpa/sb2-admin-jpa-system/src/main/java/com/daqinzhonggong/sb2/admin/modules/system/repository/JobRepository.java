package com.daqinzhonggong.sb2.admin.modules.system.repository;

import com.daqinzhonggong.sb2.admin.modules.system.domain.Job;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface JobRepository extends JpaRepository<Job, Long>, JpaSpecificationExecutor<Job> {

  /**
   * 根据名称查询
   *
   * @param name 名称
   * @return /
   */
  Job findByName(String name);

  /**
   * 根据Id删除
   *
   * @param ids /
   */
  void deleteAllByIdIn(Set<Long> ids);

}