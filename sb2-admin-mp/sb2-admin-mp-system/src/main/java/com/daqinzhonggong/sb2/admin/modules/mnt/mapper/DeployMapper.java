package com.daqinzhonggong.sb2.admin.modules.mnt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.daqinzhonggong.sb2.admin.modules.mnt.domain.Deploy;
import com.daqinzhonggong.sb2.admin.modules.mnt.domain.vo.DeployQueryCriteria;
import java.util.List;
import java.util.Set;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface DeployMapper extends BaseMapper<Deploy> {

  Long countAll(@Param("criteria") DeployQueryCriteria criteria);

  List<Deploy> findAll(@Param("criteria") DeployQueryCriteria criteria);

  Set<Long> getIdByAppIds(@Param("appIds") Set<Long> appIds);

  Deploy getDeployById(@Param("deployId") Long deployId);
}
