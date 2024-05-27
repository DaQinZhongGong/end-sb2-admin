package com.daqinzhonggong.sb2.admin.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 邮件配置类，数据存覆盖式存入数据存
 */
@Data
@TableName("tool_email_config")
public class EmailConfig implements Serializable {

  private static final long serialVersionUID = 2371198860003064030L;

  @TableId("config_id")
  private Long id;

  @NotBlank
  @ApiModelProperty(value = "邮件服务器SMTP地址")
  private String host;

  @NotBlank
  @ApiModelProperty(value = "邮件服务器 SMTP 端口")
  private String port;

  @NotBlank
  @ApiModelProperty(value = "发件者用户名")
  private String user;

  @NotBlank
  @ApiModelProperty(value = "密码")
  private String pass;

  @NotBlank
  @ApiModelProperty(value = "收件人")
  private String fromUser;

}
