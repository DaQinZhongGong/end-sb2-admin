package com.daqinzhonggong.sb2.admin.repository;

import com.daqinzhonggong.sb2.admin.domain.LocalStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LocalStorageRepository extends JpaRepository<LocalStorage, Long>,
    JpaSpecificationExecutor<LocalStorage> {

}