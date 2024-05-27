package com.daqinzhonggong.sb2.admin.service.mapstruct;

import com.daqinzhonggong.sb2.admin.base.BaseMapper;
import com.daqinzhonggong.sb2.admin.domain.SysLog;
import com.daqinzhonggong.sb2.admin.service.dto.SysLogSmallDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LogSmallMapper extends BaseMapper<SysLogSmallDto, SysLog> {

}