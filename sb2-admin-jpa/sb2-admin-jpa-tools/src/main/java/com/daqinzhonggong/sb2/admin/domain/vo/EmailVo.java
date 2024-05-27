package com.daqinzhonggong.sb2.admin.domain.vo;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 发送邮件时，接收参数的类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailVo {

  /**
   * 收件人，支持多个收件人
   */
  @NotEmpty
  private List<String> tos;

  @NotBlank
  private String subject;

  @NotBlank
  private String content;
}
