package com.daqinzhonggong.sb2.admin.modules.quartz.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.daqinzhonggong.sb2.admin.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("sys_quartz_job")
public class QuartzJob extends BaseEntity implements Serializable {

  public static final String JOB_KEY = "JOB_KEY";

  @TableId(value = "job_id", type = IdType.AUTO)
  @NotNull(groups = {Update.class})
  private Long id;

  @TableField(exist = false)
  @ApiModelProperty(value = "用于子任务唯一标识", hidden = true)
  private String uuid;

  @ApiModelProperty(value = "定时器名称")
  private String jobName;

  @NotBlank
  @ApiModelProperty(value = "Bean名称")
  private String beanName;

  @NotBlank
  @ApiModelProperty(value = "方法名称")
  private String methodName;

  @ApiModelProperty(value = "参数")
  private String params;

  @NotBlank
  @ApiModelProperty(value = "cron表达式")
  private String cronExpression;

  @ApiModelProperty(value = "状态，暂时或启动")
  private Boolean isPause = false;

  @ApiModelProperty(value = "负责人")
  private String personInCharge;

  @ApiModelProperty(value = "报警邮箱")
  private String email;

  @ApiModelProperty(value = "子任务")
  private String subTask;

  @ApiModelProperty(value = "失败后暂停")
  private Boolean pauseAfterFailure;

  @NotBlank
  @ApiModelProperty(value = "备注")
  private String description;

}