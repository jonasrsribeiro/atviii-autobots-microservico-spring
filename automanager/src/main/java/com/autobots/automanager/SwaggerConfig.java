package com.autobots.automanager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket retailBankApi() {

        return new Docket(DocumentationType.SWAGGER_2)
                /* http://localhost:8080/swagger-ui/ */
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.autobots.automanager.controles"))
                .paths(regex("/usuarios.*"))
                .build();
    }
}
