package com.tenetmind.genderdetector.provider;

import com.tenetmind.genderdetector.detector.GenderDetector;

public interface DetectorProvider {

    GenderDetector provide(String detectorVariantName);

}
