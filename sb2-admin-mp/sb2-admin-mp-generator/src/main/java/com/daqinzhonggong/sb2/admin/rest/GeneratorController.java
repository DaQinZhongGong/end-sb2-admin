package com.daqinzhonggong.sb2.admin.rest;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.daqinzhonggong.sb2.admin.domain.ColumnInfo;
import com.daqinzhonggong.sb2.admin.domain.vo.TableInfo;
import com.daqinzhonggong.sb2.admin.exception.BadRequestException;
import com.daqinzhonggong.sb2.admin.service.GenConfigService;
import com.daqinzhonggong.sb2.admin.service.GeneratorService;
import com.daqinzhonggong.sb2.admin.utils.PageResult;
import com.daqinzhonggong.sb2.admin.utils.PageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/generator")
@Api(tags = "系统：代码生成管理")
public class GeneratorController {

  private final GeneratorService generatorService;
  private final GenConfigService genConfigService;

  @Value("${generator.enabled}")
  private Boolean generatorEnabled;

  @ApiOperation("查询数据库数据")
  @GetMapping(value = "/tables")
  public ResponseEntity<PageResult<TableInfo>> queryTables(
      @RequestParam(defaultValue = "") String name, Page<Object> page) {
    return new ResponseEntity<>(generatorService.getTables(name, page), HttpStatus.OK);
  }

  @ApiOperation("查询字段数据")
  @GetMapping(value = "/columns")
  public ResponseEntity<PageResult<ColumnInfo>> queryColumns(@RequestParam String tableName) {
    List<ColumnInfo> columnInfos = generatorService.getColumns(tableName);
    return new ResponseEntity<>(PageUtil.toPage(columnInfos), HttpStatus.OK);
  }

  @ApiOperation("保存字段数据")
  @PutMapping
  public ResponseEntity<HttpStatus> saveColumn(@RequestBody List<ColumnInfo> columnInfos) {
    generatorService.save(columnInfos);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @ApiOperation("同步字段数据")
  @PostMapping(value = "sync")
  public ResponseEntity<HttpStatus> syncColumn(@RequestBody List<String> tables) {
    for (String table : tables) {
      generatorService.sync(generatorService.getColumns(table), generatorService.query(table));
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @ApiOperation("生成代码")
  @PostMapping(value = "/{tableName}/{type}")
  public ResponseEntity<Object> generatorCode(@PathVariable String tableName,
      @PathVariable Integer type, HttpServletRequest request, HttpServletResponse response) {
    if (!generatorEnabled && type == 0) {
      throw new BadRequestException("此环境不允许生成代码，请选择预览或者下载查看！");
    }
    switch (type) {
      // 生成代码
      case 0:
        generatorService.generator(genConfigService.find(tableName),
            generatorService.getColumns(tableName));
        break;
      // 预览
      case 1:
        return generatorService.preview(genConfigService.find(tableName),
            generatorService.getColumns(tableName));
      // 打包
      case 2:
        generatorService.download(genConfigService.find(tableName),
            generatorService.getColumns(tableName), request, response);
        break;
      default:
        throw new BadRequestException("没有这个选项");
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }

}
