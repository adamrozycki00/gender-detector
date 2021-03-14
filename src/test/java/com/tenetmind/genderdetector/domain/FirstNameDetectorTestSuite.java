package com.tenetmind.genderdetector.domain;

import com.tenetmind.genderdetector.repository.GenderRepositoryImpl;
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
class FirstNameDetectorTestSuite {

    private static final String FEMALE_FILE = "src/test/resources/female.txt";
    private static final String MALE_FILE = "src/test/resources/male.txt";

    @Autowired
    private GenderDetector firstNameDetector;

    @Autowired
    private GenderRepositoryImpl femaleRepository;

    @Autowired
    private GenderRepositoryImpl maleRepository;

    @BeforeEach
    public void setUp() {
        femaleRepository.setFileContainingTokens(Paths.get(FEMALE_FILE));
        maleRepository.setFileContainingTokens(Paths.get(MALE_FILE));

        try (Formatter writer = new Formatter(FEMALE_FILE)) {
            writer.format("Maria\n");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try (Formatter writer = new Formatter(MALE_FILE)) {
            writer.format("Jan\n");
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
        String stringToCheck = "Maria Jan Rokita";

        //when
        String result = firstNameDetector.detect(stringToCheck);

        //then
        assertEquals(GenderDetector.FEMALE, result);
    }

    @Test
    public void shouldDetectInconclusive() {
        //given
        String stringToCheck = "Alex Jan Rokita";

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