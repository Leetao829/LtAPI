package com.leetao.ltapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@Configuration //配置类
@EnableSwagger2WebMvc// 开启Swagger2的自动配置
public class SwaggerConfig {
	@Bean(value = "defaultApi2") //配置docket以配置Swagger具体参数
	public Docket docket() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.leetao.ltapi.controller"))
				.paths(PathSelectors.any())
				.build();
	}

	//配置文档信息
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("用户中心")
				.description("用户中心接口文档")
				.termsOfServiceUrl("https://github.com/Leetao829")
				.contact(new Contact("leetao","https://github.com/Leetao829","xxx@qq.com"))
				.version("1.0")
				.build();
	}
}

