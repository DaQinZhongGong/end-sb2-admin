package com.daqinzhonggong.sb2.admin.modules.mnt.service.mapstruct;

import com.daqinzhonggong.sb2.admin.base.BaseMapper;
import com.daqinzhonggong.sb2.admin.modules.mnt.domain.ServerDeploy;
import com.daqinzhonggong.sb2.admin.modules.mnt.service.dto.ServerDeployDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ServerDeployMapper extends BaseMapper<ServerDeployDto, ServerDeploy> {

}
