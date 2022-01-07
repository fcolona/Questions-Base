package br.com.questionsbase.api.common;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {                                    
    @Bean
    public Docket productApi() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.any())              
          .paths(PathSelectors.ant("/api/v1/**"))                          
          .build();                              
    }

    public ApiInfo metaInfo(){ 
        ApiInfo apiInfo = new ApiInfo(
            "Questions API REST",
            "API REST of a question base",
            "1.0",
            "Terms of Service",
            new Contact("", "", ""),
            "Apache License Version 2.0",
            "https://apache.org/license.html", new ArrayList<VendorExtension>()
        );

        return apiInfo;
    }
}
