package com.daqinzhonggong.sb2.admin.modules.mnt.service.mapstruct;

import com.daqinzhonggong.sb2.admin.base.BaseMapper;
import com.daqinzhonggong.sb2.admin.modules.mnt.domain.App;
import com.daqinzhonggong.sb2.admin.modules.mnt.service.dto.AppDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AppMapper extends BaseMapper<AppDto, App> {

}
