package com.daqinzhonggong.sb2.admin.modules.security.rest;

import com.daqinzhonggong.sb2.admin.modules.security.service.OnlineUserService;
import com.daqinzhonggong.sb2.admin.modules.security.service.dto.OnlineUserDto;
import com.daqinzhonggong.sb2.admin.utils.EncryptUtils;
import com.daqinzhonggong.sb2.admin.utils.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/online")
@Api(tags = "系统：在线用户管理")
public class OnlineController {

  private final OnlineUserService onlineUserService;

  @ApiOperation("查询在线用户")
  @GetMapping
  @PreAuthorize("@el.check()")
  public ResponseEntity<PageResult<OnlineUserDto>> queryOnlineUser(String username,
      Pageable pageable) {
    return new ResponseEntity<>(onlineUserService.getAll(username, pageable), HttpStatus.OK);
  }

  @ApiOperation("导出数据")
  @GetMapping(value = "/download")
  @PreAuthorize("@el.check()")
  public void exportOnlineUser(HttpServletResponse response, String username) throws IOException {
    onlineUserService.download(onlineUserService.getAll(username), response);
  }

  @ApiOperation("踢出用户")
  @DeleteMapping
  @PreAuthorize("@el.check()")
  public ResponseEntity<Object> deleteOnlineUser(@RequestBody Set<String> keys) throws Exception {
    for (String token : keys) {
      // 解密Key
      token = EncryptUtils.desDecrypt(token);
      onlineUserService.logout(token);
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }

}
