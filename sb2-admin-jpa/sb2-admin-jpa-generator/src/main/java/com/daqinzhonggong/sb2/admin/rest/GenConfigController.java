package com.daqinzhonggong.sb2.admin.rest;

import com.daqinzhonggong.sb2.admin.domain.GenConfig;
import com.daqinzhonggong.sb2.admin.service.GenConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/genConfig")
@Api(tags = "系统：代码生成器配置管理")
public class GenConfigController {

  private final GenConfigService genConfigService;

  @ApiOperation("查询")
  @GetMapping(value = "/{tableName}")
  public ResponseEntity<GenConfig> queryGenConfig(@PathVariable String tableName) {
    return new ResponseEntity<>(genConfigService.find(tableName), HttpStatus.OK);
  }

  @PutMapping
  @ApiOperation("修改")
  public ResponseEntity<Object> updateGenConfig(@Validated @RequestBody GenConfig genConfig) {
    return new ResponseEntity<>(genConfigService.update(genConfig.getTableName(), genConfig),
        HttpStatus.OK);
  }
}
