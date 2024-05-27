package com.daqinzhonggong.sb2.admin.config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * api页面 /doc.html
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

  @Value("${jwt.header}")
  private String tokenHeader;

  @Value("${swagger.enabled}")
  private Boolean enabled;

  @Bean
  @SuppressWarnings("all")
  public Docket createRestApi() {
    return new Docket(DocumentationType.SWAGGER_2)
        .enable(enabled)
        .pathMapping("/")
        .apiInfo(apiInfo())
        .select()
        .paths(PathSelectors.regex("^(?!/error).*"))
        .paths(PathSelectors.any())
        .build()
        //添加登陆认证
        .securitySchemes(securitySchemes())
        .securityContexts(securityContexts());
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .description("sb2-admin-mp 后台管理框架")
        .title("sb2-admin-mp 接口文档")
        .version("2024.04.02")
        .build();
  }

  private List<SecurityScheme> securitySchemes() {
    //设置请求头信息
    List<SecurityScheme> securitySchemes = new ArrayList<>();
    ApiKey apiKey = new ApiKey(tokenHeader, tokenHeader, "header");
    securitySchemes.add(apiKey);
    return securitySchemes;
  }

  private List<SecurityContext> securityContexts() {
    //设置需要登录认证的路径
    List<SecurityContext> securityContexts = new ArrayList<>();
    securityContexts.add(getContextByPath());
    return securityContexts;
  }

  private SecurityContext getContextByPath() {
    return SecurityContext.builder()
        .securityReferences(defaultAuth())
        // 表示 /auth/code、/auth/login 接口不需要使用securitySchemes即不需要带token
        .operationSelector(o -> o.requestMappingPattern().matches("^(?!/auth/code|/auth/login).*$"))
        .build();
  }

  private List<SecurityReference> defaultAuth() {
    List<SecurityReference> securityReferences = new ArrayList<>();
    AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
    authorizationScopes[0] = authorizationScope;
    securityReferences.add(new SecurityReference(tokenHeader, authorizationScopes));
    return securityReferences;
  }

}
