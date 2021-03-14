package com.tenetmind.genderdetector.detector;

import com.tenetmind.genderdetector.provider.DetectorProviderImpl;
import com.tenetmind.genderdetector.repository.GenericGenderRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Formatter;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DetectorProviderTests {

    private static final String FEMALE_FILE = "src/test/resources/female.txt";
    private static final String MALE_FILE = "src/test/resources/male.txt";

    @Autowired
    private DetectorProviderImpl detectorProvider;

    @Autowired
    private GenericGenderRepository femaleRepository;

    @Autowired
    private GenericGenderRepository maleRepository;

    @BeforeEach
    public void setUp() {
        femaleRepository.setFileContainingTokens(Paths.get(FEMALE_FILE));
        maleRepository.setFileContainingTokens(Paths.get(MALE_FILE));

        try (Formatter writer = new Formatter(FEMALE_FILE)) {
            writer.format("Maria\n");
            writer.format("Anna\n");
            writer.format("Gertruda\n");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try (Formatter writer = new Formatter(MALE_FILE)) {
            writer.format("Jan\n");
            writer.format("Zbigniew\n");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    public static void cleanUp() throws IOException {
        Files.deleteIfExists(Paths.get(FEMALE_FILE));
        Files.deleteIfExists(Paths.get(MALE_FILE));
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

}