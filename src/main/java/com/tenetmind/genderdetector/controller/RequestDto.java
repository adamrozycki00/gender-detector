package com.tenetmind.genderdetector.controller;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestDto {

    @JsonProperty("name")
    private final String sourceStringToCheck;

    @JsonProperty("variant")
    private final String detectorVariantName;

    public RequestDto(String sourceStringToCheck, String detectorVariantName) {
        this.sourceStringToCheck = sourceStringToCheck;
        this.detectorVariantName = detectorVariantName;
    }

    public String getSourceStringToCheck() {
        return sourceStringToCheck;
    }

    public String getDetectorVariantName() {
        return detectorVariantName;
    }

}
