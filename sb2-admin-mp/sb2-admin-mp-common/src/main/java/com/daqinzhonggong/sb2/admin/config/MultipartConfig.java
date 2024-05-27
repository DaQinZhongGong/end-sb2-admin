package com.daqinzhonggong.sb2.admin.config;

import java.io.File;
import javax.servlet.MultipartConfigElement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class MultipartConfig {

  /**
   * 文件上传临时路径
   */
  @Bean
  MultipartConfigElement multipartConfigElement() {
    MultipartConfigFactory factory = new MultipartConfigFactory();
    String location = System.getProperty("user.home") + "/.java/file/tmp";
    File tmpFile = new File(location);
    if (!tmpFile.exists()) {
      if (!tmpFile.mkdirs()) {
        log.info("create was not successful.");
      }
    }
    factory.setLocation(location);
    return factory.createMultipartConfig();
  }

}