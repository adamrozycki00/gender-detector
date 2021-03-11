package com.tenetmind.genderdetector.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoreConfiguration {

    @Value("${female.tokens.path}")
    String pathToFemaleTokens;

    @Value("${male.tokens.path}")
    String pathToMaleTokens;

    public String getPathToFemaleTokens() {
        return pathToFemaleTokens;
    }

    public String getPathToMaleTokens() {
        return pathToMaleTokens;
    }

}
