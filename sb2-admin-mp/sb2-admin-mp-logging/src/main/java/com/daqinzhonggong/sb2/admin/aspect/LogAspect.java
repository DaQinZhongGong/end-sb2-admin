package com.daqinzhonggong.sb2.admin.aspect;

import com.daqinzhonggong.sb2.admin.domain.SysLog;
import com.daqinzhonggong.sb2.admin.service.SysLogService;
import com.daqinzhonggong.sb2.admin.utils.RequestHolder;
import com.daqinzhonggong.sb2.admin.utils.SecurityUtils;
import com.daqinzhonggong.sb2.admin.utils.StringUtils;
import com.daqinzhonggong.sb2.admin.utils.ThrowableUtil;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class LogAspect {

  private final SysLogService sysLogService;

  ThreadLocal<Long> currentTime = new ThreadLocal<>();

  public LogAspect(SysLogService sysLogService) {
    this.sysLogService = sysLogService;
  }

  /**
   * 配置切入点
   */
  @Pointcut("@annotation(com.daqinzhonggong.sb2.admin.annotation.Log)")
  public void logPointcut() {
    // 该方法无方法体,主要为了让同类中其他方法使用此切入点
  }

  /**
   * 配置环绕通知,使用在方法logPointcut()上注册的切入点
   *
   * @param joinPoint join point for advice
   */
  @Around("logPointcut()")
  public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
    Object result;
    currentTime.set(System.currentTimeMillis());
    result = joinPoint.proceed();
    SysLog sysLog = new SysLog("INFO", System.currentTimeMillis() - currentTime.get());
    currentTime.remove();
    HttpServletRequest request = RequestHolder.getHttpServletRequest();
    sysLogService.save(getUsername(), StringUtils.getBrowser(request), StringUtils.getIp(request),
        joinPoint, sysLog);
    return result;
  }

  /**
   * 配置异常通知
   *
   * @param joinPoint join point for advice
   * @param e         exception
   */
  @AfterThrowing(pointcut = "logPointcut()", throwing = "e")
  public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
    SysLog sysLog = new SysLog("ERROR", System.currentTimeMillis() - currentTime.get());
    currentTime.remove();
    sysLog.setExceptionDetail(new String(ThrowableUtil.getStackTrace(e).getBytes()));
    HttpServletRequest request = RequestHolder.getHttpServletRequest();
    sysLogService.save(getUsername(), StringUtils.getBrowser(request), StringUtils.getIp(request),
        (ProceedingJoinPoint) joinPoint, sysLog);
  }

  public String getUsername() {
    try {
      return SecurityUtils.getCurrentUsername();
    } catch (Exception e) {
      return "";
    }
  }

}
