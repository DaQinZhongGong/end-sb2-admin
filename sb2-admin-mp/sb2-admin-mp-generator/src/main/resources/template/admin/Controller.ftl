package ${package}.rest;

import com.daqinzhonggong.sb2.admin.annotation.Log;
import ${package}.domain.${className};
import ${package}.service.${className}Service;
import ${package}.domain.vo.${className}QueryCriteria;
import lombok.RequiredArgsConstructor;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.daqinzhonggong.sb2.admin.utils.PageResult;

/**
* @author ${author}
* @date ${date}
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "${apiAlias}管理")
@RequestMapping("/api/${changeClassName}")
public class ${className}Controller {

    private final ${className}Service ${changeClassName}Service;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('${changeClassName}:list')")
    public void export${className}(HttpServletResponse response, ${className}QueryCriteria criteria) throws IOException {
        ${changeClassName}Service.download(${changeClassName}Service.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询${apiAlias}")
    @ApiOperation("查询${apiAlias}")
    @PreAuthorize("@el.check('${changeClassName}:list')")
    public ResponseEntity<PageResult<${className}>> query${className}(${className}QueryCriteria criteria, Page<Object> page) {
        return new ResponseEntity<>(${changeClassName}Service.queryAll(criteria,page),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增${apiAlias}")
    @ApiOperation("新增${apiAlias}")
    @PreAuthorize("@el.check('${changeClassName}:add')")
    public ResponseEntity<Object> create${className}(@Validated @RequestBody ${className} resources) {
        ${changeClassName}Service.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改${apiAlias}")
    @ApiOperation("修改${apiAlias}")
    @PreAuthorize("@el.check('${changeClassName}:edit')")
    public ResponseEntity<Object> update${className}(@Validated @RequestBody ${className} resources) {
        ${changeClassName}Service.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除${apiAlias}")
    @ApiOperation("删除${apiAlias}")
    @PreAuthorize("@el.check('${changeClassName}:del')")
    public ResponseEntity<Object> delete${className}(@RequestBody List<${pkColumnType}> ids) {
        ${changeClassName}Service.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}