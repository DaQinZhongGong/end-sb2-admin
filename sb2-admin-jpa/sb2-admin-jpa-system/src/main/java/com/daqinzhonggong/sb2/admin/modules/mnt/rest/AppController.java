package com.daqinzhonggong.sb2.admin.modules.mnt.rest;

import com.daqinzhonggong.sb2.admin.annotation.Log;
import com.daqinzhonggong.sb2.admin.modules.mnt.domain.App;
import com.daqinzhonggong.sb2.admin.modules.mnt.service.AppService;
import com.daqinzhonggong.sb2.admin.modules.mnt.service.dto.AppDto;
import com.daqinzhonggong.sb2.admin.modules.mnt.service.dto.AppQueryCriteria;
import com.daqinzhonggong.sb2.admin.utils.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@Api(tags = "运维：应用管理")
@RequestMapping("/api/app")
public class AppController {

  private final AppService appService;

  @ApiOperation("导出应用数据")
  @GetMapping(value = "/download")
  @PreAuthorize("@el.check('app:list')")
  public void exportApp(HttpServletResponse response, AppQueryCriteria criteria)
      throws IOException {
    appService.download(appService.queryAll(criteria), response);
  }

  @ApiOperation(value = "查询应用")
  @GetMapping
  @PreAuthorize("@el.check('app:list')")
  public ResponseEntity<PageResult<AppDto>> queryApp(AppQueryCriteria criteria, Pageable pageable) {
    return new ResponseEntity<>(appService.queryAll(criteria, pageable), HttpStatus.OK);
  }

  @Log("新增应用")
  @ApiOperation(value = "新增应用")
  @PostMapping
  @PreAuthorize("@el.check('app:add')")
  public ResponseEntity<Object> createApp(@Validated @RequestBody App resources) {
    appService.create(resources);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @Log("修改应用")
  @ApiOperation(value = "修改应用")
  @PutMapping
  @PreAuthorize("@el.check('app:edit')")
  public ResponseEntity<Object> updateApp(@Validated @RequestBody App resources) {
    appService.update(resources);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @Log("删除应用")
  @ApiOperation(value = "删除应用")
  @DeleteMapping
  @PreAuthorize("@el.check('app:del')")
  public ResponseEntity<Object> deleteApp(@RequestBody Set<Long> ids) {
    appService.delete(ids);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
