package com.daqinzhonggong.sb2.admin.repository;

import com.daqinzhonggong.sb2.admin.domain.EmailConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<EmailConfig, Long> {

}
