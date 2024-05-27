package com.daqinzhonggong.sb2.admin.modules.mnt.repository;

import com.daqinzhonggong.sb2.admin.modules.mnt.domain.Deploy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DeployRepository extends JpaRepository<Deploy, Long>,
    JpaSpecificationExecutor<Deploy> {

}
