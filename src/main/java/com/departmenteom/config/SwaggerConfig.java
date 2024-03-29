package com.departmenteom.config;

import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import static springfox.documentation.builders.PathSelectors.regex;

//@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.departmenteom.web"))
                .paths(paths())
                .build();
    }

    private Predicate paths() {
        return regex("/api/.*");
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Diploma API specification")
                .contact(new Contact("Maria",
                        "https://github.com/MissHorlinson",
                        "mary24018@gmail.com"))
                .description("Description")
                .version("1.0")
                .build();
    }
}
