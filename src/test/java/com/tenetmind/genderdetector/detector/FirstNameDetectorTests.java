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
class FirstNameDetectorTests {

    @Autowired
    private GenderDetector firstNameDetector;

    @Autowired
    private CoreConfiguration config;

    @BeforeEach
    public void setUp() {
        try (Formatter writer = new Formatter(config.getPathToFemaleTokens())) {
            writer.format("Janina\n");
            writer.format("Maria\n");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try (Formatter writer = new Formatter(config.getPathToMaleTokens())) {
            writer.format("Jan\n");
            writer.format("Maria\n");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldDetectMale() {
        //given
        String stringToCheck = "Jan Maria Rokita";

        //when
        String result = firstNameDetector.detect(stringToCheck);

        //then
        assertEquals(GenderDetector.MALE, result);
    }

    @Test
    public void shouldDetectMaleRegardlessOfLeadingWhitespace() {
        //given
        String stringToCheck = "\nJan Maria Rokita";

        //when
        String result = firstNameDetector.detect(stringToCheck);

        //then
        assertEquals(GenderDetector.MALE, result);
    }

    @Test
    public void shouldDetectFemale() {
        //given
        String stringToCheck = "Janina Maria Rokita";

        //when
        String result = firstNameDetector.detect(stringToCheck);

        //then
        assertEquals(GenderDetector.FEMALE, result);
    }

    @Test
    public void shouldDetectInconclusive() {
        //given
        String stringToCheck = "Maria Jan Rokita";

        //when
        String result = firstNameDetector.detect(stringToCheck);

        //then
        assertEquals(GenderDetector.INCONCLUSIVE, result);
    }

    @Test
    public void shouldDetectInconclusiveOnEmptyString() {
        //given
        String stringToCheck = "";

        //when
        String result = firstNameDetector.detect(stringToCheck);

        //then
        assertEquals(GenderDetector.INCONCLUSIVE, result);
    }

    @Test
    public void shouldDetectInconclusiveOnWhitespace() {
        //given
        String stringToCheck = "\t\n";

        //when
        String result = firstNameDetector.detect(stringToCheck);

        //then
        assertEquals(GenderDetector.INCONCLUSIVE, result);
    }

    @Test
    public void shouldDetectInconclusiveOnNull() {
        //given
        String stringToCheck = null;

        //when
        String result = firstNameDetector.detect(stringToCheck);

        //then
        assertEquals(GenderDetector.INCONCLUSIVE, result);
    }

}