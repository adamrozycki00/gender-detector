package com.tenetmind.genderdetector.detector;

import com.tenetmind.genderdetector.config.CoreConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileNotFoundException;
import java.util.Formatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class MajorityRuleDetectorTests {

    @Autowired
    private GenderDetector majorityRuleDetector;

    @Autowired
    private CoreConfiguration config;

    @BeforeEach
    public void setUp() {
        try (Formatter writer = new Formatter(config.getPathToFemaleTokens())) {
            writer.format("Janina\n");
            writer.format("Maria\n");
            writer.format("Alex\n");
            writer.format("Gertruda\n");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try (Formatter writer = new Formatter(config.getPathToMaleTokens())) {
            writer.format("Jan\n");
            writer.format("Maria\n");
            writer.format("Alex\n");
            writer.format("Zbigniew\n");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldDetectMaleWhenMajorityIsMale() {
        //given
        String stringToCheck = "Jan Maria Rokita";

        //when
        String result = majorityRuleDetector.detect(stringToCheck);

        //then
        assertEquals(GenderDetector.MALE, result);
    }

    @Test
    public void shouldDetectMaleRegardlessOfLeadingWhitespace() {
        //given
        String stringToCheck = "\nJan Maria Rokita";

        //when
        String result = majorityRuleDetector.detect(stringToCheck);

        //then
        assertEquals(GenderDetector.MALE, result);
    }

    @Test
    public void shouldDetectMaleRegardlessOfManyTokensUnknown() {
        //given
        String stringToCheck = "Jan One Two Three Four Maria Rokita";

        //when
        String result = majorityRuleDetector.detect(stringToCheck);

        //then
        assertEquals(GenderDetector.MALE, result);
    }

    @Test
    public void shouldDetectFemaleWhenMajorityIsFemale() {
        //given
        String stringToCheck = "Janina Maria Rokita";

        //when
        String result = majorityRuleDetector.detect(stringToCheck);

        //then
        assertEquals(GenderDetector.FEMALE, result);
    }

    @Test
    public void shouldDetectInconclusiveWhenNoMajority() {
        //given
        String stringToCheck = "Maria Alex Rokita";

        //when
        String result = majorityRuleDetector.detect(stringToCheck);

        //then
        assertEquals(GenderDetector.INCONCLUSIVE, result);
    }

    @Test
    public void shouldDetectInconclusiveOnEmptyString() {
        //given
        String stringToCheck = "";

        //when
        String result = majorityRuleDetector.detect(stringToCheck);

        //then
        assertEquals(GenderDetector.INCONCLUSIVE, result);
    }

    @Test
    public void shouldDetectInconclusiveOnWhitespace() {
        //given
        String stringToCheck = "\t\n";

        //when
        String result = majorityRuleDetector.detect(stringToCheck);

        //then
        assertEquals(GenderDetector.INCONCLUSIVE, result);
    }

    @Test
    public void shouldDetectInconclusiveOnNull() {
        //given
        String stringToCheck = null;

        //when
        String result = majorityRuleDetector.detect(stringToCheck);

        //then
        assertEquals(GenderDetector.INCONCLUSIVE, result);
    }

}