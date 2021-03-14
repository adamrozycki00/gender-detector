package com.tenetmind.genderdetector.domain;

import com.tenetmind.genderdetector.config.CoreConfiguration;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DetectorProviderImpl implements DetectorProvider {

    private final Map<String, GenderDetector> detectorVariantNameToBeanMapping = new HashMap<>();

    public DetectorProviderImpl(CoreConfiguration config,
                                GenderDetector firstNameDetector,
                                GenderDetector majorityRuleDetector) {

        detectorVariantNameToBeanMapping.put(config.getFirstNameVariantName(), firstNameDetector);
        detectorVariantNameToBeanMapping.put(config.getMajorityRuleVariantName(), majorityRuleDetector);
    }

    public GenderDetector provide(String detectorVariantName) {
        return detectorVariantNameToBeanMapping.get(detectorVariantName);
    }

}
