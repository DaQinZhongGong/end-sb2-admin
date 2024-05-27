package com.daqinzhonggong.sb2.admin.modules.mnt.rest;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.daqinzhonggong.sb2.admin.annotation.Log;
import com.daqinzhonggong.sb2.admin.modules.mnt.domain.Server;
import com.daqinzhonggong.sb2.admin.modules.mnt.domain.vo.ServerQueryCriteria;
import com.daqinzhonggong.sb2.admin.modules.mnt.service.ServerService;
import com.daqinzhonggong.sb2.admin.utils.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
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
@Api(tags = "运维：服务器管理")
@RequiredArgsConstructor
@RequestMapping("/api/serverDeploy")
public class ServerController {

  private final ServerService serverService;

  @ApiOperation("导出服务器数据")
  @GetMapping(value = "/download")
  @PreAuthorize("@el.check('serverDeploy:list')")
  public void exportServerDeploy(HttpServletResponse response, ServerQueryCriteria criteria)
      throws IOException {
    serverService.download(serverService.queryAll(criteria), response);
  }

  @ApiOperation(value = "查询服务器")
  @GetMapping
  @PreAuthorize("@el.check('serverDeploy:list')")
  public ResponseEntity<PageResult<Server>> queryServerDeploy(ServerQueryCriteria criteria,
      Page<Object> page) {
    return new ResponseEntity<>(serverService.queryAll(criteria, page), HttpStatus.OK);
  }

  @Log("新增服务器")
  @ApiOperation(value = "新增服务器")
  @PostMapping
  @PreAuthorize("@el.check('serverDeploy:add')")
  public ResponseEntity<Object> createServerDeploy(@Validated @RequestBody Server resources) {
    serverService.create(resources);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @Log("修改服务器")
  @ApiOperation(value = "修改服务器")
  @PutMapping
  @PreAuthorize("@el.check('serverDeploy:edit')")
  public ResponseEntity<Object> updateServerDeploy(@Validated @RequestBody Server resources) {
    serverService.update(resources);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @Log("删除服务器")
  @ApiOperation(value = "删除Server")
  @DeleteMapping
  @PreAuthorize("@el.check('serverDeploy:del')")
  public ResponseEntity<Object> deleteServerDeploy(@RequestBody Set<Long> ids) {
    serverService.delete(ids);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @Log("测试连接服务器")
  @ApiOperation(value = "测试连接服务器")
  @PostMapping("/testConnect")
  @PreAuthorize("@el.check('serverDeploy:add')")
  public ResponseEntity<Object> testConnectServerDeploy(@Validated @RequestBody Server resources) {
    return new ResponseEntity<>(serverService.testConnect(resources), HttpStatus.CREATED);
  }

}
