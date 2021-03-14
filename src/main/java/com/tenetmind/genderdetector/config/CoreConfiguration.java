package com.tenetmind.genderdetector.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoreConfiguration {

    @Value("${female.tokens.path}")
    String pathToFemaleTokens;

    @Value("${male.tokens.path}")
    String pathToMaleTokens;

    @Value("${firstname.variant.name}")
    String firstNameVariantName;

    @Value("${majorityrule.variant.name}")
    String majorityRuleVariantName;

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

}
