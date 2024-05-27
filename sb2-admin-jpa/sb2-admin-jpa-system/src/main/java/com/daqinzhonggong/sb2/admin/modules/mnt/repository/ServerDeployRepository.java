package com.daqinzhonggong.sb2.admin.modules.mnt.repository;

import com.daqinzhonggong.sb2.admin.modules.mnt.domain.ServerDeploy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ServerDeployRepository extends JpaRepository<ServerDeploy, Long>,
    JpaSpecificationExecutor<ServerDeploy> {

  /**
   * 根据IP查询
   *
   * @param ip /
   * @return /
   */
  ServerDeploy findByIp(String ip);

}
