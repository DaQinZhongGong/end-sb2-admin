package com.daqinzhonggong.sb2.admin.modules.mnt.repository;

import com.daqinzhonggong.sb2.admin.modules.mnt.domain.Database;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DatabaseRepository extends JpaRepository<Database, String>,
    JpaSpecificationExecutor<Database> {

}
