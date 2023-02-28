package com.esoluzion.service.product.manager.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SwaggerConfig {

    @Value("${server.servlet.context-path}")
    private String apiBaseUrl;

    @Value("${bg.swagger.enabled}")
    private Boolean swaggerEnabled;

    private static final String HTTPURL= "https://esoluzion.com/es/";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.esoluzion.service.product.manager"))
                .build()
                .apiInfo(getApiInfo())
                .enable(swaggerEnabled);
    }

    @SuppressWarnings("rawtypes")
    private ApiInfo getApiInfo() {

        List<VendorExtension> vendorExtensions = new ArrayList<>();
        Contact contact = new Contact("Esoluzion Consulting", HTTPURL, "contact@esoluzion.com");
        return new ApiInfo("API Rest | Product management microservices",
                "Product management API",
                "1.0.0",
                HTTPURL,
                contact,
                "Copyright",
                HTTPURL,
                vendorExtensions);
    }
}
