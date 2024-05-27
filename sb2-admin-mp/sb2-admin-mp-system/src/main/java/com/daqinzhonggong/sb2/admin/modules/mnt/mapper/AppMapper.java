package com.daqinzhonggong.sb2.admin.modules.mnt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.daqinzhonggong.sb2.admin.modules.mnt.domain.App;
import com.daqinzhonggong.sb2.admin.modules.mnt.domain.vo.AppQueryCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AppMapper extends BaseMapper<App> {

  IPage<App> queryAll(@Param("criteria") AppQueryCriteria criteria, Page<Object> page);

  List<App> queryAll(@Param("criteria") AppQueryCriteria criteria);

}
