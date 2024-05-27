package com.daqinzhonggong.sb2.admin.modules.system.rest;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.daqinzhonggong.sb2.admin.annotation.Log;
import com.daqinzhonggong.sb2.admin.exception.BadRequestException;
import com.daqinzhonggong.sb2.admin.modules.system.domain.DictDetail;
import com.daqinzhonggong.sb2.admin.modules.system.domain.vo.DictDetailQueryCriteria;
import com.daqinzhonggong.sb2.admin.modules.system.service.DictDetailService;
import com.daqinzhonggong.sb2.admin.utils.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Api(tags = "系统：字典详情管理")
@RequestMapping("/api/dictDetail")
public class DictDetailController {

  private final DictDetailService dictDetailService;
  private static final String ENTITY_NAME = "dictDetail";

  @ApiOperation("查询字典详情")
  @GetMapping
  public ResponseEntity<PageResult<DictDetail>> queryDictDetail(DictDetailQueryCriteria criteria,
      Page<Object> page) {
    return new ResponseEntity<>(dictDetailService.queryAll(criteria, page), HttpStatus.OK);
  }

  @ApiOperation("查询多个字典详情")
  @GetMapping(value = "/map")
  public ResponseEntity<Object> getDictDetailMaps(@RequestParam String dictName) {
    String[] names = dictName.split("[,，]");
    Map<String, List<DictDetail>> dictMap = new HashMap<>(16);
    for (String name : names) {
      dictMap.put(name, dictDetailService.getDictByName(name));
    }
    return new ResponseEntity<>(dictMap, HttpStatus.OK);
  }

  @Log("新增字典详情")
  @ApiOperation("新增字典详情")
  @PostMapping
  @PreAuthorize("@el.check('dict:add')")
  public ResponseEntity<Object> createDictDetail(@Validated @RequestBody DictDetail resources) {
    if (resources.getId() != null) {
      throw new BadRequestException("A new " + ENTITY_NAME + " cannot already have an ID");
    }
    dictDetailService.create(resources);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @Log("修改字典详情")
  @ApiOperation("修改字典详情")
  @PutMapping
  @PreAuthorize("@el.check('dict:edit')")
  public ResponseEntity<Object> updateDictDetail(
      @Validated(DictDetail.Update.class) @RequestBody DictDetail resources) {
    dictDetailService.update(resources);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @Log("删除字典详情")
  @ApiOperation("删除字典详情")
  @DeleteMapping(value = "/{id}")
  @PreAuthorize("@el.check('dict:del')")
  public ResponseEntity<Object> deleteDictDetail(@PathVariable Long id) {
    dictDetailService.delete(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }

}