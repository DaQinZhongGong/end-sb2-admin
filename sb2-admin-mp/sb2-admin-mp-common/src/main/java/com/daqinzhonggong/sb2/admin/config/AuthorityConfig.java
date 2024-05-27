package com.daqinzhonggong.sb2.admin.config;

import com.daqinzhonggong.sb2.admin.utils.SecurityUtils;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 使用示例：@PreAuthorize("@el.check('dept:del')") 在Spring
 * Security中，@PreAuthorize是一个用于方法级别的安全注解，它允许你在方法执行之前声明安全表达式。这些表达式通常用于确定当前用户是否有权执行该方法。
 * <p>
 * 这里的语法可以分解为以下几个部分：
 *
 * @PreAuthorize：这是一个Spring Security的注解，用于方法级别的安全控制。
 * @el.check('dept:del')：这是一个SpEL（Spring Expression Language）表达式。
 * @el：这部分引用了一个名为el的Bean（可能是一个AuthorityConfig类的实例，如你在之前代码示例中定义的，但名称改为了el）。这个Bean应该已经在Spring的上下文中被定义，并且可以被SpEL表达式所访问。
 * .check(' dept : del ')：这部分调用了el Bean上的check方法，并传递了一个字符串参数'dept:del'。这通常表示检查用户是否拥有"dept:del"这个权限。
 * 整个表达式的意思是：在调用这个方法之前，先检查当前用户是否拥有"dept:del"这个权限。如果用户没有这个权限，那么方法将不会被执行，并且会抛出一个AccessDeniedException。
 * <p>
 * 注意：要使这种语法工作，你需要确保以下几点：
 * <p>
 * 你已经定义了名为el的Bean，并且它有一个check方法，该方法接受一个字符串参数并返回一个布尔值。 你已经在你的Spring
 * Security配置中启用了方法级别的安全性（通常是通过在配置类上添加@EnableGlobalMethodSecurity(prePostEnabled = true)来实现的）。
 * 你的方法（或类）在Spring的上下文中是可访问的，并且Spring Security的拦截器已经配置为检查这个方法的安全性。
 * </p>
 */
@Service(value = "el")
public class AuthorityConfig {

  public Boolean check(String... permissions) {
    // 获取当前用户的所有权限
    List<String> elPermissions = SecurityUtils.getCurrentUser().getAuthorities().stream()
        .map(GrantedAuthority::getAuthority).collect(Collectors.toList());
    // 判断当前用户的所有权限是否包含接口上定义的权限
    return elPermissions.contains("admin") || Arrays.stream(permissions)
        .anyMatch(elPermissions::contains);
  }

}
