package com.tenetmind.genderdetector.provider;

import com.tenetmind.genderdetector.config.CoreConfiguration;
import com.tenetmind.genderdetector.detector.GenderDetector;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Qualifier("detectorProviderImpl")
public class DetectorProviderImpl implements DetectorProvider {

    private final static String DEFAULT = "default";

    private final Map<String, GenderDetector> mapVariantNameToBean = new HashMap<>();

    public DetectorProviderImpl(CoreConfiguration config,
                                GenderDetector firstNameDetector,
                                GenderDetector majorityRuleDetector) {

        mapVariantNameToBean.put(config.getFirstNameVariantName(), firstNameDetector);
        mapVariantNameToBean.put(config.getMajorityRuleVariantName(), majorityRuleDetector);

        setDefaultVariantName(config.getDefaultVariantName());
    }

    public GenderDetector provide(String detectorVariantName) {
        GenderDetector detector = mapVariantNameToBean.get(detectorVariantName);
        if (detector == null) {
            detector = mapVariantNameToBean.get(DEFAULT);
        }
        return detector;
    }

    public void setDefaultVariantName(String newDefaultVariantName) {
        List<String> availableVariantNames = mapVariantNameToBean.keySet().stream()
                .filter(detectorName -> !detectorName.equals(DEFAULT))
                .collect(Collectors.toList());

        if (availableVariantNames.contains(newDefaultVariantName)) {
            mapVariantNameToBean.put(DEFAULT, mapVariantNameToBean.get(newDefaultVariantName));
        }
    }

}
