package com.tenetmind.genderdetector.domain;

public interface DetectorProvider {

    GenderDetector provide(String detectorVariantName);

}
