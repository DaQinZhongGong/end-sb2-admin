package com.daqinzhonggong.sb2.admin.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.daqinzhonggong.sb2.admin.modules.system.domain.DictDetail;
import com.daqinzhonggong.sb2.admin.modules.system.domain.vo.DictDetailQueryCriteria;
import java.util.List;
import java.util.Set;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DictDetailMapper extends BaseMapper<DictDetail> {

  List<DictDetail> findByDictName(@Param("name") String name);

  IPage<DictDetail> findAll(@Param("criteria") DictDetailQueryCriteria criteria, Page<Object> page);

  void deleteByDictBatchIds(@Param("dictIds") Set<Long> dictIds);

}