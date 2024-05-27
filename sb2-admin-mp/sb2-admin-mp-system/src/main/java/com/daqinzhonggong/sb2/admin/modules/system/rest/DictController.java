package com.daqinzhonggong.sb2.admin.modules.system.rest;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.daqinzhonggong.sb2.admin.annotation.Log;
import com.daqinzhonggong.sb2.admin.exception.BadRequestException;
import com.daqinzhonggong.sb2.admin.modules.system.domain.Dict;
import com.daqinzhonggong.sb2.admin.modules.system.domain.vo.DictQueryCriteria;
import com.daqinzhonggong.sb2.admin.modules.system.service.DictService;
import com.daqinzhonggong.sb2.admin.utils.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.util.List;
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
@RequiredArgsConstructor
@Api(tags = "系统：字典管理")
@RequestMapping("/api/dict")
public class DictController {

  private final DictService dictService;
  private static final String ENTITY_NAME = "dict";

  @ApiOperation("导出字典数据")
  @GetMapping(value = "/download")
  @PreAuthorize("@el.check('dict:list')")
  public void exportDict(HttpServletResponse response, DictQueryCriteria criteria)
      throws IOException {
    dictService.download(dictService.queryAll(criteria), response);
  }

  @ApiOperation("查询字典")
  @GetMapping(value = "/all")
  @PreAuthorize("@el.check('dict:list')")
  public ResponseEntity<List<Dict>> queryAllDict() {
    return new ResponseEntity<>(dictService.queryAll(new DictQueryCriteria()), HttpStatus.OK);
  }

  @ApiOperation("查询字典")
  @GetMapping
  @PreAuthorize("@el.check('dict:list')")
  public ResponseEntity<PageResult<Dict>> queryDict(DictQueryCriteria resources,
      Page<Object> page) {
    return new ResponseEntity<>(dictService.queryAll(resources, page), HttpStatus.OK);
  }

  @Log("新增字典")
  @ApiOperation("新增字典")
  @PostMapping
  @PreAuthorize("@el.check('dict:add')")
  public ResponseEntity<Object> createDict(@Validated @RequestBody Dict resources) {
    if (resources.getId() != null) {
      throw new BadRequestException("A new " + ENTITY_NAME + " cannot already have an ID");
    }
    dictService.create(resources);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @Log("修改字典")
  @ApiOperation("修改字典")
  @PutMapping
  @PreAuthorize("@el.check('dict:edit')")
  public ResponseEntity<Object> updateDict(
      @Validated(Dict.Update.class) @RequestBody Dict resources) {
    dictService.update(resources);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @Log("删除字典")
  @ApiOperation("删除字典")
  @DeleteMapping
  @PreAuthorize("@el.check('dict:del')")
  public ResponseEntity<Object> deleteDict(@RequestBody Set<Long> ids) {
    dictService.delete(ids);
    return new ResponseEntity<>(HttpStatus.OK);
  }

}