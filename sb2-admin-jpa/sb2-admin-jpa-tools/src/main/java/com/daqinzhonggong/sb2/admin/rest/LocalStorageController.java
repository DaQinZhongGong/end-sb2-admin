package com.daqinzhonggong.sb2.admin.rest;

import com.daqinzhonggong.sb2.admin.annotation.Log;
import com.daqinzhonggong.sb2.admin.domain.LocalStorage;
import com.daqinzhonggong.sb2.admin.exception.BadRequestException;
import com.daqinzhonggong.sb2.admin.service.LocalStorageService;
import com.daqinzhonggong.sb2.admin.service.dto.LocalStorageDto;
import com.daqinzhonggong.sb2.admin.service.dto.LocalStorageQueryCriteria;
import com.daqinzhonggong.sb2.admin.utils.FileUtil;
import com.daqinzhonggong.sb2.admin.utils.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@Api(tags = "工具：本地存储管理")
@RequestMapping("/api/localStorage")
public class LocalStorageController {

  private final LocalStorageService localStorageService;

  @GetMapping
  @ApiOperation("查询文件")
  @PreAuthorize("@el.check('storage:list')")
  public ResponseEntity<PageResult<LocalStorageDto>> queryFile(LocalStorageQueryCriteria criteria,
      Pageable pageable) {
    return new ResponseEntity<>(localStorageService.queryAll(criteria, pageable), HttpStatus.OK);
  }

  @ApiOperation("导出数据")
  @GetMapping(value = "/download")
  @PreAuthorize("@el.check('storage:list')")
  public void exportFile(HttpServletResponse response, LocalStorageQueryCriteria criteria)
      throws IOException {
    localStorageService.download(localStorageService.queryAll(criteria), response);
  }

  @PostMapping
  @ApiOperation("上传文件")
  @PreAuthorize("@el.check('storage:add')")
  public ResponseEntity<Object> createFile(@RequestParam String name,
      @RequestParam("file") MultipartFile file) {
    localStorageService.create(name, file);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @ApiOperation("上传图片")
  @PostMapping("/pictures")
  public ResponseEntity<LocalStorage> uploadPicture(@RequestParam MultipartFile file) {
    // 判断文件是否为图片
    String suffix = FileUtil.getExtensionName(file.getOriginalFilename());
    if (!FileUtil.IMAGE.equals(FileUtil.getFileType(suffix))) {
      throw new BadRequestException("只能上传图片");
    }
    LocalStorage localStorage = localStorageService.create(null, file);
    return new ResponseEntity<>(localStorage, HttpStatus.OK);
  }

  @PutMapping
  @Log("修改文件")
  @ApiOperation("修改文件")
  @PreAuthorize("@el.check('storage:edit')")
  public ResponseEntity<Object> updateFile(@Validated @RequestBody LocalStorage resources) {
    localStorageService.update(resources);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @Log("删除文件")
  @DeleteMapping
  @ApiOperation("多选删除")
  public ResponseEntity<Object> deleteFile(@RequestBody Long[] ids) {
    localStorageService.deleteAll(ids);
    return new ResponseEntity<>(HttpStatus.OK);
  }

}