package com.daqinzhonggong.sb2.admin.service;

import com.daqinzhonggong.sb2.admin.domain.SysLog;
import com.daqinzhonggong.sb2.admin.service.dto.SysLogQueryCriteria;
import com.daqinzhonggong.sb2.admin.service.dto.SysLogSmallDto;
import com.daqinzhonggong.sb2.admin.utils.PageResult;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;

public interface SysLogService {

  /**
   * 分页查询
   *
   * @param criteria 查询条件
   * @param pageable 分页参数
   * @return /
   */
  Object queryAll(SysLogQueryCriteria criteria, Pageable pageable);

  /**
   * 查询全部数据
   *
   * @param criteria 查询条件
   * @return /
   */
  List<SysLog> queryAll(SysLogQueryCriteria criteria);

  /**
   * 查询用户日志
   *
   * @param criteria 查询条件
   * @param pageable 分页参数
   * @return -
   */
  PageResult<SysLogSmallDto> queryAllByUser(SysLogQueryCriteria criteria, Pageable pageable);

  /**
   * 保存日志数据
   *
   * @param username  用户
   * @param browser   浏览器
   * @param ip        请求IP
   * @param joinPoint /
   * @param sysLog    日志实体
   */
  @Async
  void save(String username, String browser, String ip, ProceedingJoinPoint joinPoint,
      SysLog sysLog);

  /**
   * 查询异常详情
   *
   * @param id 日志ID
   * @return Object
   */
  Object findByErrDetail(Long id);

  /**
   * 导出日志
   *
   * @param sysLogs  待导出的数据
   * @param response /
   * @throws IOException /
   */
  void download(List<SysLog> sysLogs, HttpServletResponse response) throws IOException;

  /**
   * 删除所有错误日志
   */
  void delAllByError();

  /**
   * 删除所有INFO日志
   */
  void delAllByInfo();
}
