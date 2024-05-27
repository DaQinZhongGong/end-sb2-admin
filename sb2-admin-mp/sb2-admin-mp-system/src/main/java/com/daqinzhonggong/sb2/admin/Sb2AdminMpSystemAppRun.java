package com.daqinzhonggong.sb2.admin;

import com.daqinzhonggong.sb2.admin.annotation.rest.AnonymousGetMapping;
import com.daqinzhonggong.sb2.admin.utils.SpringContextHolder;
import io.swagger.annotations.Api;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;

@EnableAsync
@RestController
@Api(hidden = true)
@SpringBootApplication
@EnableTransactionManagement
public class Sb2AdminMpSystemAppRun {

  public static void main(String[] args) {
    SpringApplication springApplication = new SpringApplication(Sb2AdminMpSystemAppRun.class);
    // 监控应用的PID，启动时可指定PID路径：--spring.pid.file=/home/java/app.pid
    // 或者在 application.yml 添加文件路径，方便 kill，kill `cat /home/java/app.pid`
    springApplication.addListeners(new ApplicationPidFileWriter());
    springApplication.run(args);
  }

  @Bean
  public SpringContextHolder springContextHolder() {
    return new SpringContextHolder();
  }

  /**
   * 访问首页提示
   *
   * @return /
   */
  @AnonymousGetMapping("/")
  public String index() {
    return "Sb2AdminMpSystemAppRun Backend service started successfully";
  }

}
