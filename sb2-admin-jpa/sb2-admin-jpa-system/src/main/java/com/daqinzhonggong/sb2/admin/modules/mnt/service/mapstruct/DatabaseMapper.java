package com.daqinzhonggong.sb2.admin.modules.mnt.service.mapstruct;

import com.daqinzhonggong.sb2.admin.base.BaseMapper;
import com.daqinzhonggong.sb2.admin.modules.mnt.domain.Database;
import com.daqinzhonggong.sb2.admin.modules.mnt.service.dto.DatabaseDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DatabaseMapper extends BaseMapper<DatabaseDto, Database> {

}
