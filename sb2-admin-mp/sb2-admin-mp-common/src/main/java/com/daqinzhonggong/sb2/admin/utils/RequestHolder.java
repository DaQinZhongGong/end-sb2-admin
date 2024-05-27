package com.daqinzhonggong.sb2.admin.utils;

import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 获取 HttpServletRequest
 */
public class RequestHolder {

  public static HttpServletRequest getHttpServletRequest() {
    return ((ServletRequestAttributes) Objects.requireNonNull(
        RequestContextHolder.getRequestAttributes())).getRequest();
  }
}
