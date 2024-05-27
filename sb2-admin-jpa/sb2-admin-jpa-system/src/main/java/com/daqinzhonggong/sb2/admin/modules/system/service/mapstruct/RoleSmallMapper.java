package com.daqinzhonggong.sb2.admin.modules.system.service.mapstruct;

import com.daqinzhonggong.sb2.admin.base.BaseMapper;
import com.daqinzhonggong.sb2.admin.modules.system.domain.Role;
import com.daqinzhonggong.sb2.admin.modules.system.service.dto.RoleSmallDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleSmallMapper extends BaseMapper<RoleSmallDto, Role> {

}
