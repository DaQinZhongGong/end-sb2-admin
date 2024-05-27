package ${package}.service;

import ${package}.domain.${className};
import ${package}.domain.vo.${className}QueryCriteria;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.daqinzhonggong.sb2.admin.utils.PageResult;

/**
* @description 服务接口
* @author ${author}
* @date ${date}
**/
public interface ${className}Service extends IService<${className}> {

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param page 分页参数
    * @return PageResult
    */
    PageResult<${className}> queryAll(${className}QueryCriteria criteria, Page<Object> page);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<${className}Dto>
    */
    List<${className}> queryAll(${className}QueryCriteria criteria);

    /**
    * 创建
    * @param resources /
    */
    void create(${className} resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(${className} resources);

    /**
    * 多选删除
    * @param ids /
    */
    void deleteAll(List<${pkColumnType}> ids);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<${className}> all, HttpServletResponse response) throws IOException;

}