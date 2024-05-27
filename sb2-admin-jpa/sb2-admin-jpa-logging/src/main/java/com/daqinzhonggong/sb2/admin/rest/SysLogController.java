package com.daqinzhonggong.sb2.admin.rest;

import com.daqinzhonggong.sb2.admin.annotation.Log;
import com.daqinzhonggong.sb2.admin.service.SysLogService;
import com.daqinzhonggong.sb2.admin.service.dto.SysLogQueryCriteria;
import com.daqinzhonggong.sb2.admin.service.dto.SysLogSmallDto;
import com.daqinzhonggong.sb2.admin.utils.PageResult;
import com.daqinzhonggong.sb2.admin.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/logs")
@Api(tags = "系统：日志管理")
public class SysLogController {

  private final SysLogService sysLogService;

  @Log("导出数据")
  @ApiOperation("导出数据")
  @GetMapping(value = "/download")
  @PreAuthorize("@el.check()")
  public void exportLog(HttpServletResponse response, SysLogQueryCriteria criteria)
      throws IOException {
    criteria.setLogType("INFO");
    sysLogService.download(sysLogService.queryAll(criteria), response);
  }

  @Log("导出错误数据")
  @ApiOperation("导出错误数据")
  @GetMapping(value = "/error/download")
  @PreAuthorize("@el.check()")
  public void exportErrorLog(HttpServletResponse response, SysLogQueryCriteria criteria)
      throws IOException {
    criteria.setLogType("ERROR");
    sysLogService.download(sysLogService.queryAll(criteria), response);
  }

  @GetMapping
  @ApiOperation("日志查询")
  @PreAuthorize("@el.check()")
  public ResponseEntity<Object> queryLog(SysLogQueryCriteria criteria, Pageable pageable) {
    criteria.setLogType("INFO");
    return new ResponseEntity<>(sysLogService.queryAll(criteria, pageable), HttpStatus.OK);
  }

  @GetMapping(value = "/user")
  @ApiOperation("用户日志查询")
  public ResponseEntity<PageResult<SysLogSmallDto>> queryUserLog(SysLogQueryCriteria criteria,
      Pageable pageable) {
    criteria.setLogType("INFO");
    criteria.setUsername(SecurityUtils.getCurrentUsername());
    return new ResponseEntity<>(sysLogService.queryAllByUser(criteria, pageable), HttpStatus.OK);
  }

  @GetMapping(value = "/error")
  @ApiOperation("错误日志查询")
  @PreAuthorize("@el.check()")
  public ResponseEntity<Object> queryErrorLog(SysLogQueryCriteria criteria, Pageable pageable) {
    criteria.setLogType("ERROR");
    return new ResponseEntity<>(sysLogService.queryAll(criteria, pageable), HttpStatus.OK);
  }

  @GetMapping(value = "/error/{id}")
  @ApiOperation("日志异常详情查询")
  @PreAuthorize("@el.check()")
  public ResponseEntity<Object> queryErrorLogDetail(@PathVariable Long id) {
    return new ResponseEntity<>(sysLogService.findByErrDetail(id), HttpStatus.OK);
  }

  @DeleteMapping(value = "/del/error")
  @Log("删除所有ERROR日志")
  @ApiOperation("删除所有ERROR日志")
  @PreAuthorize("@el.check()")
  public ResponseEntity<Object> delAllErrorLog() {
    sysLogService.delAllByError();
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @DeleteMapping(value = "/del/info")
  @Log("删除所有INFO日志")
  @ApiOperation("删除所有INFO日志")
  @PreAuthorize("@el.check()")
  public ResponseEntity<Object> delAllInfoLog() {
    sysLogService.delAllByInfo();
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
