package com.daqinzhonggong.sb2.admin.modules.mnt.service.mapstruct;

import com.daqinzhonggong.sb2.admin.base.BaseMapper;
import com.daqinzhonggong.sb2.admin.modules.mnt.domain.Deploy;
import com.daqinzhonggong.sb2.admin.modules.mnt.service.dto.DeployDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = {AppMapper.class,
    ServerDeployMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DeployMapper extends BaseMapper<DeployDto, Deploy> {

}
