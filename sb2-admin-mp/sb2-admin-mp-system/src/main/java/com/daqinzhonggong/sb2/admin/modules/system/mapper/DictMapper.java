package com.daqinzhonggong.sb2.admin.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.daqinzhonggong.sb2.admin.modules.system.domain.Dict;
import com.daqinzhonggong.sb2.admin.modules.system.domain.vo.DictQueryCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DictMapper extends BaseMapper<Dict> {

  List<Dict> findAll(@Param("criteria") DictQueryCriteria criteria);

  Long countAll(@Param("criteria") DictQueryCriteria criteria);

}