package com.daqinzhonggong.sb2.admin.modules.security.config;

import com.daqinzhonggong.sb2.admin.modules.security.config.bean.LoginProperties;
import com.daqinzhonggong.sb2.admin.modules.security.config.bean.SecurityProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置文件转换Pojo类的 统一配置 类
 */
@Configuration
public class ConfigBeanConfiguration {

  @Bean
  @ConfigurationProperties(prefix = "login")
  public LoginProperties loginProperties() {
    return new LoginProperties();
  }

  @Bean
  @ConfigurationProperties(prefix = "jwt")
  public SecurityProperties securityProperties() {
    return new SecurityProperties();
  }

}
