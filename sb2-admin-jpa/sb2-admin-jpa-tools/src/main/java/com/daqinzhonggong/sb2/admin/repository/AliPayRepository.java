package com.daqinzhonggong.sb2.admin.repository;

import com.daqinzhonggong.sb2.admin.domain.AlipayConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AliPayRepository extends JpaRepository<AlipayConfig, Long> {

}
