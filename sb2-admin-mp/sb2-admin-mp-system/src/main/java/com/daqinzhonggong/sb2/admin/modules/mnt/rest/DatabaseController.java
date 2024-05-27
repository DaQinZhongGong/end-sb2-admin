package com.daqinzhonggong.sb2.admin.modules.mnt.rest;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.daqinzhonggong.sb2.admin.annotation.Log;
import com.daqinzhonggong.sb2.admin.exception.BadRequestException;
import com.daqinzhonggong.sb2.admin.modules.mnt.domain.Database;
import com.daqinzhonggong.sb2.admin.modules.mnt.domain.vo.DatabaseQueryCriteria;
import com.daqinzhonggong.sb2.admin.modules.mnt.service.DatabaseService;
import com.daqinzhonggong.sb2.admin.modules.mnt.util.SqlUtils;
import com.daqinzhonggong.sb2.admin.utils.FileUtil;
import com.daqinzhonggong.sb2.admin.utils.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.File;
import java.io.IOException;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "运维：数据库管理")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/database")
public class DatabaseController {

  private final String fileSavePath = FileUtil.getTmpDirPath() + "/";
  private final DatabaseService databaseService;

  @ApiOperation("导出数据库数据")
  @GetMapping(value = "/download")
  @PreAuthorize("@el.check('database:list')")
  public void exportDatabase(HttpServletResponse response, DatabaseQueryCriteria criteria)
      throws IOException {
    databaseService.download(databaseService.queryAll(criteria), response);
  }

  @ApiOperation(value = "查询数据库")
  @GetMapping
  @PreAuthorize("@el.check('database:list')")
  public ResponseEntity<PageResult<Database>> queryDatabase(DatabaseQueryCriteria criteria,
      Page<Object> page) {
    return new ResponseEntity<>(databaseService.queryAll(criteria, page), HttpStatus.OK);
  }

  @Log("新增数据库")
  @ApiOperation(value = "新增数据库")
  @PostMapping
  @PreAuthorize("@el.check('database:add')")
  public ResponseEntity<Object> createDatabase(@Validated @RequestBody Database resources) {
    databaseService.create(resources);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @Log("修改数据库")
  @ApiOperation(value = "修改数据库")
  @PutMapping
  @PreAuthorize("@el.check('database:edit')")
  public ResponseEntity<Object> updateDatabase(@Validated @RequestBody Database resources) {
    databaseService.update(resources);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @Log("删除数据库")
  @ApiOperation(value = "删除数据库")
  @DeleteMapping
  @PreAuthorize("@el.check('database:del')")
  public ResponseEntity<Object> deleteDatabase(@RequestBody Set<String> ids) {
    databaseService.delete(ids);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @Log("测试数据库链接")
  @ApiOperation(value = "测试数据库链接")
  @PostMapping("/testConnect")
  @PreAuthorize("@el.check('database:testConnect')")
  public ResponseEntity<Object> testConnect(@Validated @RequestBody Database resources) {
    return new ResponseEntity<>(databaseService.testConnection(resources), HttpStatus.CREATED);
  }

  @Log("执行SQL脚本")
  @ApiOperation(value = "执行SQL脚本")
  @PostMapping(value = "/upload")
  @PreAuthorize("@el.check('database:add')")
  public ResponseEntity<Object> uploadDatabase(@RequestBody MultipartFile file,
      HttpServletRequest request) throws Exception {
    String id = request.getParameter("id");
    Database database = databaseService.getById(id);
    String fileName;
    if (database != null) {
      fileName = file.getOriginalFilename();
      File executeFile = new File(fileSavePath + fileName);
      FileUtil.del(executeFile);
      file.transferTo(executeFile);
      String result = SqlUtils.executeFile(database.getJdbcUrl(), database.getUserName(),
          database.getPwd(), executeFile);
      return new ResponseEntity<>(result, HttpStatus.OK);
    } else {
      throw new BadRequestException("Database not exist");
    }
  }

}
