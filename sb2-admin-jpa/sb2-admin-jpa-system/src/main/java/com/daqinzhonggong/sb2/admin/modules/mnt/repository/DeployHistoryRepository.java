package com.daqinzhonggong.sb2.admin.modules.mnt.repository;

import com.daqinzhonggong.sb2.admin.modules.mnt.domain.DeployHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DeployHistoryRepository extends JpaRepository<DeployHistory, String>,
    JpaSpecificationExecutor<DeployHistory> {

}
