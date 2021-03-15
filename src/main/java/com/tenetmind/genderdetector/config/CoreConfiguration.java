package com.tenetmind.genderdetector.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class CoreConfiguration implements WebMvcConfigurer {

    @Value("${female.tokens.path}")
    private String pathToFemaleTokens;

    @Value("${male.tokens.path}")
    private String pathToMaleTokens;

    @Value("${firstname.variant.name}")
    private String firstNameVariantName;

    @Value("${majorityrule.variant.name}")
    private String majorityRuleVariantName;

    @Value("${default.variant.name}")
    private String defaultVariantName;

    @Value("${page.size.limit}")
    private long pageSizeLimit;

    public String getPathToFemaleTokens() {
        return pathToFemaleTokens;
    }

    public String getPathToMaleTokens() {
        return pathToMaleTokens;
    }

    public String getFirstNameVariantName() {
        return firstNameVariantName;
    }

    public String getMajorityRuleVariantName() {
        return majorityRuleVariantName;
    }

    public String getDefaultVariantName() {
        return defaultVariantName;
    }

    public long getPageSizeLimit() {
        return pageSizeLimit;
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        // Required by Swagger UI configuration
        registry.addResourceHandler("/lib/**").addResourceLocations("/lib/").setCachePeriod(0);
        registry.addResourceHandler("/images/**").addResourceLocations("/images/").setCachePeriod(0);
        registry.addResourceHandler("/css/**").addResourceLocations("/css/").setCachePeriod(0);
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}
