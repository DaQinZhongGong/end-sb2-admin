package com.daqinzhonggong.sb2.admin.utils;

import java.time.LocalDateTime;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class DateUtilsTest {

  @Test
  public void test1() {
    long l = System.currentTimeMillis() / 1000;
    LocalDateTime localDateTime = DateUtil.fromTimeStamp(l);
    System.out.print(DateUtil.localDateTimeFormatyMdHms(localDateTime));
  }

  @Test
  public void test2() {
    LocalDateTime now = LocalDateTime.now();
    log.info(DateUtil.localDateTimeFormatyMdHms(now));
    Date date = DateUtil.toDate(now);
    LocalDateTime localDateTime = DateUtil.toLocalDateTime(date);
    log.info(DateUtil.localDateTimeFormatyMdHms(localDateTime));
    LocalDateTime localDateTime1 = DateUtil.fromTimeStamp(date.getTime() / 1000);
    log.info(DateUtil.localDateTimeFormatyMdHms(localDateTime1));
  }

}
