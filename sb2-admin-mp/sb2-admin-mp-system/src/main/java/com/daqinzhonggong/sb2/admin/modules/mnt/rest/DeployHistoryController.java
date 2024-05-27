package com.daqinzhonggong.sb2.admin.modules.mnt.rest;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.daqinzhonggong.sb2.admin.annotation.Log;
import com.daqinzhonggong.sb2.admin.modules.mnt.domain.DeployHistory;
import com.daqinzhonggong.sb2.admin.modules.mnt.domain.vo.DeployHistoryQueryCriteria;
import com.daqinzhonggong.sb2.admin.modules.mnt.service.DeployHistoryService;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Api(tags = "运维：部署历史管理")
@RequestMapping("/api/deployHistory")
public class DeployHistoryController {

  private final DeployHistoryService deployhistoryService;

  @ApiOperation("导出部署历史数据")
  @GetMapping(value = "/download")
  @PreAuthorize("@el.check('deployHistory:list')")
  public void exportDeployHistory(HttpServletResponse response, DeployHistoryQueryCriteria criteria)
      throws IOException {
    deployhistoryService.download(deployhistoryService.queryAll(criteria), response);
  }

  @ApiOperation(value = "查询部署历史")
  @GetMapping
  @PreAuthorize("@el.check('deployHistory:list')")
  public ResponseEntity<PageResult<DeployHistory>> queryDeployHistory(
      DeployHistoryQueryCriteria criteria, Page<Object> page) {
    return new ResponseEntity<>(deployhistoryService.queryAll(criteria, page), HttpStatus.OK);
  }

  @Log("删除DeployHistory")
  @ApiOperation(value = "删除部署历史")
  @DeleteMapping
  @PreAuthorize("@el.check('deployHistory:del')")
  public ResponseEntity<Object> deleteDeployHistory(@RequestBody Set<String> ids) {
    deployhistoryService.delete(ids);
    return new ResponseEntity<>(HttpStatus.OK);
  }

}
