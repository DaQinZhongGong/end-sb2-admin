package com.daqinzhonggong.sb2.admin.modules.system.service.mapstruct;

import com.daqinzhonggong.sb2.admin.base.BaseMapper;
import com.daqinzhonggong.sb2.admin.modules.system.domain.DictDetail;
import com.daqinzhonggong.sb2.admin.modules.system.service.dto.DictDetailDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = {
    DictSmallMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DictDetailMapper extends BaseMapper<DictDetailDto, DictDetail> {

}