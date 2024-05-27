package com.daqinzhonggong.sb2.admin.modules.mnt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.daqinzhonggong.sb2.admin.modules.mnt.domain.Database;
import com.daqinzhonggong.sb2.admin.modules.mnt.domain.vo.DatabaseQueryCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DatabaseMapper extends BaseMapper<Database> {

  IPage<Database> findAll(@Param("criteria") DatabaseQueryCriteria criteria, Page<Object> page);

  List<Database> findAll(@Param("criteria") DatabaseQueryCriteria criteria);

}
