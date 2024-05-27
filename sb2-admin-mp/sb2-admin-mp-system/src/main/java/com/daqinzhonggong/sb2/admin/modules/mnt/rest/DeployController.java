package com.daqinzhonggong.sb2.admin.modules.mnt.rest;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.daqinzhonggong.sb2.admin.annotation.Log;
import com.daqinzhonggong.sb2.admin.modules.mnt.domain.Deploy;
import com.daqinzhonggong.sb2.admin.modules.mnt.domain.DeployHistory;
import com.daqinzhonggong.sb2.admin.modules.mnt.domain.vo.DeployQueryCriteria;
import com.daqinzhonggong.sb2.admin.modules.mnt.service.DeployService;
import com.daqinzhonggong.sb2.admin.utils.FileUtil;
import com.daqinzhonggong.sb2.admin.utils.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@Api(tags = "运维：部署管理")
@RequiredArgsConstructor
@RequestMapping("/api/deploy")
public class DeployController {

  private final String fileSavePath = FileUtil.getTmpDirPath() + "/";
  private final DeployService deployService;

  @ApiOperation("导出部署数据")
  @GetMapping(value = "/download")
  @PreAuthorize("@el.check('database:list')")
  public void exportDeployData(HttpServletResponse response, DeployQueryCriteria criteria)
      throws IOException {
    deployService.download(deployService.queryAll(criteria), response);
  }

  @ApiOperation(value = "查询部署")
  @GetMapping
  @PreAuthorize("@el.check('deploy:list')")
  public ResponseEntity<PageResult<Deploy>> queryDeployData(DeployQueryCriteria criteria,
      Page<Object> page) {
    return new ResponseEntity<>(deployService.queryAll(criteria, page), HttpStatus.OK);
  }

  @Log("新增部署")
  @ApiOperation(value = "新增部署")
  @PostMapping
  @PreAuthorize("@el.check('deploy:add')")
  public ResponseEntity<Object> createDeploy(@Validated @RequestBody Deploy resources) {
    deployService.create(resources);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @Log("修改部署")
  @ApiOperation(value = "修改部署")
  @PutMapping
  @PreAuthorize("@el.check('deploy:edit')")
  public ResponseEntity<Object> updateDeploy(@Validated @RequestBody Deploy resources) {
    deployService.update(resources);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @Log("删除部署")
  @ApiOperation(value = "删除部署")
  @DeleteMapping
  @PreAuthorize("@el.check('deploy:del')")
  public ResponseEntity<Object> deleteDeploy(@RequestBody Set<Long> ids) {
    deployService.delete(ids);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @Log("上传文件部署")
  @ApiOperation(value = "上传文件部署")
  @PostMapping(value = "/upload")
  @PreAuthorize("@el.check('deploy:edit')")
  public ResponseEntity<Object> uploadDeploy(@RequestBody MultipartFile file,
      HttpServletRequest request) throws Exception {
    Long id = Long.valueOf(request.getParameter("id"));
    String fileName = "";
    if (file != null) {
      fileName = file.getOriginalFilename();
      File deployFile = new File(fileSavePath + fileName);
      FileUtil.del(deployFile);
      file.transferTo(deployFile);
      //文件下一步要根据文件名字来
      deployService.deploy(fileSavePath + fileName, id);
    } else {
      log.info("没有找到相对应的文件");
    }
    log.info("文件上传的原名称为:" + Objects.requireNonNull(file).getOriginalFilename());
    Map<String, Object> map = new HashMap<>(2);
    map.put("errno", 0);
    map.put("id", fileName);
    return new ResponseEntity<>(map, HttpStatus.OK);
  }

  @Log("系统还原")
  @ApiOperation(value = "系统还原")
  @PostMapping(value = "/serverReduction")
  @PreAuthorize("@el.check('deploy:edit')")
  public ResponseEntity<String> serverReduction(@Validated @RequestBody DeployHistory resources) {
    String result = deployService.serverReduction(resources);
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @Log("服务运行状态")
  @ApiOperation(value = "服务运行状态")
  @PostMapping(value = "/serverStatus")
  @PreAuthorize("@el.check('deploy:edit')")
  public ResponseEntity<String> serverStatus(@Validated @RequestBody Deploy resources) {
    String result = deployService.serverStatus(resources);
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @Log("启动服务")
  @ApiOperation(value = "启动服务")
  @PostMapping(value = "/startServer")
  @PreAuthorize("@el.check('deploy:edit')")
  public ResponseEntity<String> startServer(@Validated @RequestBody Deploy resources) {
    String result = deployService.startServer(resources);
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @Log("停止服务")
  @ApiOperation(value = "停止服务")
  @PostMapping(value = "/stopServer")
  @PreAuthorize("@el.check('deploy:edit')")
  public ResponseEntity<String> stopServer(@Validated @RequestBody Deploy resources) {
    String result = deployService.stopServer(resources);
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

}
