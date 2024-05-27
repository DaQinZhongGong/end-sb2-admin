package com.daqinzhonggong.sb2.admin.exception.handler;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.daqinzhonggong.sb2.admin.exception.BadRequestException;
import com.daqinzhonggong.sb2.admin.exception.EntityExistException;
import com.daqinzhonggong.sb2.admin.exception.EntityNotFoundException;
import com.daqinzhonggong.sb2.admin.utils.ThrowableUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * <p>@RestControllerAdvice 是 Spring Framework 提供的一个注解，用于定义全局的异常处理器。这个注解结合了 @ControllerAdvice 和
 *
 * @ResponseBody 的特性，专门用于 RESTful 风格的控制器。下面详细说明其作用：
 * <p>
 * 全局异常处理：@RestControllerAdvice 标记的类中的方法可以处理整个应用程序中控制器（通常是 @RestController
 * 注解标记的类）抛出的所有异常。这意味着你可以在这个类中集中处理异常，而不用在每个控制器方法中单独处理异常，提高了代码的整洁性和可维护性。
 * <p>
 * 自动响应体：由于包含了 @ResponseBody 的特性，这个注解标记的异常处理方法会自动将返回值转换为HTTP响应体，无须再手动包装响应对象，使得API的响应更加统一和简洁。
 * <p>
 * 方法匹配：你可以根据异常类型或者特定的条件来定义处理方法。例如，可以针对特定异常类型定义处理方法，或者使用
 * @ExceptionHandler、@ModelAttribute、@InitBinder 等注解来细化处理逻辑。
 * <p>
 * 数据绑定与验证错误处理：除了处理运行时异常之外，@RestControllerAdvice 也能集中处理数据绑定异常（例如，由 @Valid
 * 注解引发的验证错误）和类型转换异常，使得错误信息能以统一且友好的形式返回给客户端。
 * <p>
 * 日志记录与监控：通常在 GlobalExceptionHandler 中还会包含日志记录逻辑，帮助开发者追踪异常发生的上下文，便于问题排查和系统监控。
 * <p>
 * 总之，@RestControllerAdvice 允许开发者实现全局的、统一的异常处理逻辑，提升REST API的健壮性和用户体验，同时也简化了异常处理的代码结构。 </p>
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  /**
   * 处理所有不可知的异常
   */
  @ExceptionHandler(Throwable.class)
  public ResponseEntity<ApiError> handleException(Throwable e) {
    // 打印堆栈信息
    log.error(ThrowableUtil.getStackTrace(e));
    return buildResponseEntity(ApiError.error(e.getMessage()));
  }

  /**
   * BadCredentialsException
   */
  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ApiError> badCredentialsException(BadCredentialsException e) {
    // 打印堆栈信息
    String message = "坏的凭证".equals(e.getMessage()) ? "用户名或密码不正确" : e.getMessage();
    log.error(message);
    return buildResponseEntity(ApiError.error(message));
  }

  /**
   * 处理自定义异常
   */
  @ExceptionHandler(value = BadRequestException.class)
  public ResponseEntity<ApiError> badRequestException(BadRequestException e) {
    // 打印堆栈信息
    log.error(ThrowableUtil.getStackTrace(e));
    return buildResponseEntity(ApiError.error(e.getStatus(), e.getMessage()));
  }

  /**
   * 处理 EntityExist
   */
  @ExceptionHandler(value = EntityExistException.class)
  public ResponseEntity<ApiError> entityExistException(EntityExistException e) {
    // 打印堆栈信息
    log.error(ThrowableUtil.getStackTrace(e));
    return buildResponseEntity(ApiError.error(e.getMessage()));
  }

  /**
   * 处理 EntityNotFound
   */
  @ExceptionHandler(value = EntityNotFoundException.class)
  public ResponseEntity<ApiError> entityNotFoundException(EntityNotFoundException e) {
    // 打印堆栈信息
    log.error(ThrowableUtil.getStackTrace(e));
    return buildResponseEntity(ApiError.error(NOT_FOUND.value(), e.getMessage()));
  }

  /**
   * 处理所有接口数据验证异常
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiError> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException e) {
    // 打印堆栈信息
    log.error(ThrowableUtil.getStackTrace(e));
    ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
    String message = objectError.getDefaultMessage();
    if (objectError instanceof FieldError) {
      message = ((FieldError) objectError).getField() + ": " + message;
    }
    return buildResponseEntity(ApiError.error(message));
  }

  /**
   * 统一返回
   */
  private ResponseEntity<ApiError> buildResponseEntity(ApiError apiError) {
    return new ResponseEntity<>(apiError, HttpStatus.valueOf(apiError.getStatus()));
  }

}
