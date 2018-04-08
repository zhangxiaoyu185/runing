package com.xiaoyu.lingdian.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/* 
 * Restful API 访问路径: 
 * http://IP:port/{context-path}/swagger-ui.html 
 @EnableWebMvc
 @EnableSwagger2 // 使swagger2生效
 @ComponentScan(basePackages = { "com.xiaoyu.lingdian" }) // 需要扫描的包路径
 @Configuration // 配置注解，自动在本类上下文加载一些环境变量信息
 */
@Configuration
@EnableSwagger2
public class RestApiConfig {
	@Bean
	public Docket buildDocket() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(buildApiInf())
				.select()
				.apis(RequestHandlerSelectors
						.basePackage("com.xiaoyu.lingdian.controller"))
				.paths(PathSelectors.any()).build()
				.ignoredParameterTypes(ApiIgnore.class);
	}

	private ApiInfo buildApiInf() {
		return new ApiInfoBuilder()
				.title("oilcard api")
				.termsOfServiceUrl("https://oil.webaiyun.com/api-docs")
				.description("<a href=\"/apidocs/api.html\" title=\"接口说明文档\" target=\"_blank\">接口说明文档</a>")
				.contact(
						new Contact("zhangyu",
								"https://oil.webaiyun.com/apidocs/api.html",
								"zy135185@163.com")
				)
				.version("1.0.0").build();

	}
}
