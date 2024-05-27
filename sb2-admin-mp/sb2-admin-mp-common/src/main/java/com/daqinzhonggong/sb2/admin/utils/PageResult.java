package com.daqinzhonggong.sb2.admin.utils;

import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class PageResult<T> {

  private final List<T> content;

  private final long totalElements;
}
