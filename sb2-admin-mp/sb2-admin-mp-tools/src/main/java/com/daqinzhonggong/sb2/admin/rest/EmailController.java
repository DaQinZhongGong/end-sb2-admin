package com.daqinzhonggong.sb2.admin.rest;

import com.daqinzhonggong.sb2.admin.annotation.Log;
import com.daqinzhonggong.sb2.admin.domain.EmailConfig;
import com.daqinzhonggong.sb2.admin.domain.vo.EmailVo;
import com.daqinzhonggong.sb2.admin.service.EmailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/email")
@Api(tags = "工具：邮件管理")
public class EmailController {

  private final EmailService emailService;

  @GetMapping
  public ResponseEntity<EmailConfig> queryEmailConfig() {
    return new ResponseEntity<>(emailService.find(), HttpStatus.OK);
  }

  @Log("配置邮件")
  @PutMapping
  @ApiOperation("配置邮件")
  public ResponseEntity<Object> updateEmailConfig(@Validated @RequestBody EmailConfig emailConfig)
      throws Exception {
    emailService.config(emailConfig, emailService.find());
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @Log("发送邮件")
  @PostMapping
  @ApiOperation("发送邮件")
  public ResponseEntity<Object> sendEmail(@Validated @RequestBody EmailVo emailVo) {
    emailService.send(emailVo, emailService.find());
    return new ResponseEntity<>(HttpStatus.OK);
  }

}
