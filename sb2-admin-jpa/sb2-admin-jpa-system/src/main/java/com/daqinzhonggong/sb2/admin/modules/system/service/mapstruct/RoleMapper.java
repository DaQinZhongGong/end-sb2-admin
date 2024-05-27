package com.daqinzhonggong.sb2.admin.modules.system.service.mapstruct;

import com.daqinzhonggong.sb2.admin.base.BaseMapper;
import com.daqinzhonggong.sb2.admin.modules.system.domain.Role;
import com.daqinzhonggong.sb2.admin.modules.system.service.dto.RoleDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = {MenuMapper.class,
    DeptMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper extends BaseMapper<RoleDto, Role> {

}
