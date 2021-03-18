package com.tenetmind.genderdetector.provider;

import com.tenetmind.genderdetector.config.CoreConfiguration;
import com.tenetmind.genderdetector.detector.GenderDetector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileNotFoundException;
import java.util.Formatter;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DetectorProviderTests {

    @Autowired
    private DetectorProvider detectorProvider;

    @Autowired
    private CoreConfiguration config;

    @BeforeEach
    public void setUp() {
        try (Formatter writer = new Formatter(config.getPathToFemaleTokens())) {
            writer.format("Maria\n");
            writer.format("Anna\n");
            writer.format("Gertruda\n");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try (Formatter writer = new Formatter(config.getPathToMaleTokens())) {
            writer.format("Jan\n");
            writer.format("Zbigniew\n");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldUseFirstNameDetector() {
        //given
        String detectorVariantName = "first";
        String stringToCheck = "Jan Maria Rokita";

        //when
        GenderDetector detector = detectorProvider.provide(detectorVariantName);
        String result = detector.detect(stringToCheck);

        //then
        assertEquals(GenderDetector.MALE, result);
    }

    @Test
    public void shouldUseMajorityRuleDetector() {
        //given
        String detectorVariantName = "majority";
        String stringToCheck = "Jan Maria Rokita";

        //when
        GenderDetector detector = detectorProvider.provide(detectorVariantName);
        String result = detector.detect(stringToCheck);

        //then
        assertEquals(GenderDetector.INCONCLUSIVE, result);
    }

    @Test
    public void shouldSetDefaultDetector() {
        //given
        String detectorVariantName = "default";
        String stringToCheck = "Jan Maria Rokita";

        //when
        ((DetectorProviderImpl) detectorProvider).setDefaultVariantName("first");
        GenderDetector detector = detectorProvider.provide(detectorVariantName);
        String resultFromFirstAsDefault = detector.detect(stringToCheck);

        ((DetectorProviderImpl) detectorProvider).setDefaultVariantName("majority");
        detector = detectorProvider.provide(detectorVariantName);
        String resultFromMajorityAsDefault = detector.detect(stringToCheck);

        //then
        assertEquals(GenderDetector.MALE, resultFromFirstAsDefault);
        assertEquals(GenderDetector.INCONCLUSIVE, resultFromMajorityAsDefault);
    }

    @Test
    public void shouldUseDefaultDetectorWhenImproperDetectorNameProvided() {
        //given
        ((DetectorProviderImpl) detectorProvider).setDefaultVariantName("first");
        String detectorVariantName = "";
        String stringToCheck = "Jan Maria Rokita";

        //when
        GenderDetector detector = detectorProvider.provide(detectorVariantName);
        String result = detector.detect(stringToCheck);

        //then
        assertEquals(GenderDetector.MALE, result);
    }

}