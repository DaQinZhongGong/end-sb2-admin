package com.daqinzhonggong.sb2.admin.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 统一异常处理
 */
@Getter
public class BadRequestException extends RuntimeException {

  private static final long serialVersionUID = -403281107812168477L;

  private Integer status = BAD_REQUEST.value();

  public BadRequestException(String msg) {
    super(msg);
  }

  public BadRequestException(HttpStatus status, String msg) {
    super(msg);
    this.status = status.value();
  }
}
