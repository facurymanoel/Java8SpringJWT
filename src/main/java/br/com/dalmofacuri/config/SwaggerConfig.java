package br.com.dalmofacuri.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
 
 @Configuration
 @EnableWebMvc
public class SwaggerConfig {
	
	private static final String BASE_PACKAGE = "br.com.dalmofacuri";
	private static final String API_TITLE = "Api Manager Clientes";
	private static final String API_DESCRIPTION = "Cliente API Professional";
	private static final String API_VERSION = "1.0.0";
	private static final String CONTACT_NAME = "Dalmo Facuri";
	private static final String CONTACT_GITHUB = "https://github.com/facuri";
	private static final String CONTACT_EMAIL = "mffacuri@terra.com.br";
	 
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				   .select()
				   .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
				    .paths(PathSelectors.any())
				    .build()
				   .apiInfo(buildApiInfo());
	}
	
	private ApiInfo buildApiInfo() {
		return new ApiInfoBuilder()
				   .title(API_TITLE)
				   .description(API_DESCRIPTION)
				   .version(API_VERSION)
				   //.contact(new Contact(CONTACT_NAME, CONTACT_GITHUB,CONTACT_EMAIL))
				   .contact(new Contact(CONTACT_NAME, CONTACT_GITHUB,CONTACT_EMAIL))
				   .build();
				   
	}
  
}
  