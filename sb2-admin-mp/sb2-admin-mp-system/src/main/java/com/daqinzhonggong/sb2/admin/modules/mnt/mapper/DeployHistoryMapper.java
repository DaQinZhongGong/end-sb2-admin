package com.daqinzhonggong.sb2.admin.modules.mnt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.daqinzhonggong.sb2.admin.modules.mnt.domain.DeployHistory;
import com.daqinzhonggong.sb2.admin.modules.mnt.domain.vo.DeployHistoryQueryCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DeployHistoryMapper extends BaseMapper<DeployHistory> {

  IPage<DeployHistory> findAll(@Param("criteria") DeployHistoryQueryCriteria criteria,
      Page<Object> page);

  List<DeployHistory> findAll(@Param("criteria") DeployHistoryQueryCriteria criteria);

}
