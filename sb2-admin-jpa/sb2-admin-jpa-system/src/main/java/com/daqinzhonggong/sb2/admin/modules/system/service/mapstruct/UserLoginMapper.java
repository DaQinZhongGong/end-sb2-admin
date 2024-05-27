package com.daqinzhonggong.sb2.admin.modules.system.service.mapstruct;

import com.daqinzhonggong.sb2.admin.base.BaseMapper;
import com.daqinzhonggong.sb2.admin.modules.system.domain.User;
import com.daqinzhonggong.sb2.admin.modules.system.service.dto.UserLoginDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = {RoleMapper.class, DeptMapper.class,
    JobMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserLoginMapper extends BaseMapper<UserLoginDto, User> {

}
