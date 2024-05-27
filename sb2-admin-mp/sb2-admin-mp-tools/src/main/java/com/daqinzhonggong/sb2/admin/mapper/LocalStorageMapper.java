package com.daqinzhonggong.sb2.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.daqinzhonggong.sb2.admin.domain.LocalStorage;
import com.daqinzhonggong.sb2.admin.domain.vo.LocalStorageQueryCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LocalStorageMapper extends BaseMapper<LocalStorage> {

  IPage<LocalStorage> findAll(@Param("criteria") LocalStorageQueryCriteria criteria,
      Page<Object> page);

  List<LocalStorage> findAll(@Param("criteria") LocalStorageQueryCriteria criteria);

}
