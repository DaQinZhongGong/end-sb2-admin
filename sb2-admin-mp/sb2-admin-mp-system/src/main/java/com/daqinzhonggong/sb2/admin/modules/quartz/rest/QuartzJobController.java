package com.daqinzhonggong.sb2.admin.modules.quartz.rest;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.daqinzhonggong.sb2.admin.annotation.Log;
import com.daqinzhonggong.sb2.admin.exception.BadRequestException;
import com.daqinzhonggong.sb2.admin.modules.quartz.domain.QuartzJob;
import com.daqinzhonggong.sb2.admin.modules.quartz.domain.QuartzLog;
import com.daqinzhonggong.sb2.admin.modules.quartz.domain.vo.QuartzJobQueryCriteria;
import com.daqinzhonggong.sb2.admin.modules.quartz.service.QuartzJobService;
import com.daqinzhonggong.sb2.admin.utils.PageResult;
import com.daqinzhonggong.sb2.admin.utils.SpringContextHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/jobs")
@Api(tags = "系统:定时任务管理")
public class QuartzJobController {

  private static final String ENTITY_NAME = "quartzJob";
  private final QuartzJobService quartzJobService;

  @ApiOperation("查询定时任务")
  @GetMapping
  @PreAuthorize("@el.check('timing:list')")
  public ResponseEntity<PageResult<QuartzJob>> queryQuartzJob(QuartzJobQueryCriteria criteria,
      Page<Object> page) {
    return new ResponseEntity<>(quartzJobService.queryAll(criteria, page), HttpStatus.OK);
  }

  @ApiOperation("导出任务数据")
  @GetMapping(value = "/download")
  @PreAuthorize("@el.check('timing:list')")
  public void exportQuartzJob(HttpServletResponse response, QuartzJobQueryCriteria criteria)
      throws IOException {
    quartzJobService.download(quartzJobService.queryAll(criteria), response);
  }

  @ApiOperation("导出日志数据")
  @GetMapping(value = "/logs/download")
  @PreAuthorize("@el.check('timing:list')")
  public void exportQuartzJobLog(HttpServletResponse response, QuartzJobQueryCriteria criteria)
      throws IOException {
    quartzJobService.downloadLog(quartzJobService.queryAllLog(criteria), response);
  }

  @ApiOperation("查询任务执行日志")
  @GetMapping(value = "/logs")
  @PreAuthorize("@el.check('timing:list')")
  public ResponseEntity<PageResult<QuartzLog>> queryQuartzJobLog(QuartzJobQueryCriteria criteria,
      Page<Object> page) {
    return new ResponseEntity<>(quartzJobService.queryAllLog(criteria, page), HttpStatus.OK);
  }

  @Log("新增定时任务")
  @ApiOperation("新增定时任务")
  @PostMapping
  @PreAuthorize("@el.check('timing:add')")
  public ResponseEntity<Object> createQuartzJob(@Validated @RequestBody QuartzJob resources) {
    if (resources.getId() != null) {
      throw new BadRequestException("A new " + ENTITY_NAME + " cannot already have an ID");
    }
    // 验证Bean是不是合法的，合法的定时任务 Bean 需要用 @Service 定义
    checkBean(resources.getBeanName());
    quartzJobService.create(resources);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @Log("修改定时任务")
  @ApiOperation("修改定时任务")
  @PutMapping
  @PreAuthorize("@el.check('timing:edit')")
  public ResponseEntity<Object> updateQuartzJob(
      @Validated(QuartzJob.Update.class) @RequestBody QuartzJob resources) {
    // 验证Bean是不是合法的，合法的定时任务 Bean 需要用 @Service 定义
    checkBean(resources.getBeanName());
    quartzJobService.update(resources);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @Log("更改定时任务状态")
  @ApiOperation("更改定时任务状态")
  @PutMapping(value = "/{id}")
  @PreAuthorize("@el.check('timing:edit')")
  public ResponseEntity<Object> updateQuartzJobStatus(@PathVariable Long id) {
    quartzJobService.updateIsPause(quartzJobService.getById(id));
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @Log("执行定时任务")
  @ApiOperation("执行定时任务")
  @PutMapping(value = "/exec/{id}")
  @PreAuthorize("@el.check('timing:edit')")
  public ResponseEntity<Object> executionQuartzJob(@PathVariable Long id) {
    quartzJobService.execution(quartzJobService.getById(id));
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @Log("删除定时任务")
  @ApiOperation("删除定时任务")
  @DeleteMapping
  @PreAuthorize("@el.check('timing:del')")
  public ResponseEntity<Object> deleteQuartzJob(@RequestBody Set<Long> ids) {
    quartzJobService.delete(ids);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  private void checkBean(String beanName) {
    // 避免调用攻击者可以从SpringContextHolder获得控制jdbcTemplate类
    // 并使用getDeclaredMethod调用jdbcTemplate的queryForMap函数，执行任意sql命令。
    if (!SpringContextHolder.getAllServiceBeanName().contains(beanName)) {
      throw new BadRequestException("非法的 Bean，请重新输入！");
    }
  }

}
