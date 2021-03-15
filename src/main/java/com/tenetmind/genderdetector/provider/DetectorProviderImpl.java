package com.tenetmind.genderdetector.provider;

import com.tenetmind.genderdetector.config.CoreConfiguration;
import com.tenetmind.genderdetector.detector.GenderDetector;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Qualifier("detectorProviderImpl")
public class DetectorProviderImpl implements DetectorProvider {

    private final Map<String, GenderDetector> mapVariantNameToBean = new HashMap<>();

    private String defaultVariantName;

    public DetectorProviderImpl(CoreConfiguration config,
                                GenderDetector firstNameDetector,
                                GenderDetector majorityRuleDetector) {

        defaultVariantName = config.getDefaultVariantName();

        mapVariantNameToBean.put(config.getFirstNameVariantName(), firstNameDetector);
        mapVariantNameToBean.put(config.getMajorityRuleVariantName(), majorityRuleDetector);

        mapVariantNameToBean.put("default", mapVariantNameToBean.get(defaultVariantName));
    }

    public GenderDetector provide(String detectorVariantName) {
        GenderDetector detector = mapVariantNameToBean.get(detectorVariantName);
        if (detector == null) {
            detector = mapVariantNameToBean.get("default");
        }
        return detector;
    }

    public void setDefaultVariantName(String defaultVariantName) {
        this.defaultVariantName = defaultVariantName;
        mapVariantNameToBean.put("default", mapVariantNameToBean.get(defaultVariantName));
    }

}
