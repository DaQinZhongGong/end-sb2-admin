package com.daqinzhonggong.sb2.admin.modules.quartz.repository;

import com.daqinzhonggong.sb2.admin.modules.quartz.domain.QuartzJob;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface QuartzJobRepository extends JpaRepository<QuartzJob, Long>,
    JpaSpecificationExecutor<QuartzJob> {

  /**
   * 查询启用的任务
   *
   * @return List
   */
  List<QuartzJob> findByIsPauseIsFalse();

}
