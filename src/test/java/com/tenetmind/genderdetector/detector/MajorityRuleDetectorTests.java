package com.tenetmind.genderdetector.detector;

import com.tenetmind.genderdetector.repository.RepositoryProviderImpl;
import com.tenetmind.genderdetector.repository.RepositoryProviderTestingImpl;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class MajorityRuleDetectorTests {

    private static final String FEMALE_FILE = "src/test/resources/female.txt";
    private static final String MALE_FILE = "src/test/resources/male.txt";

    @Autowired
    private GenderDetector majorityRuleDetector;

    @Autowired
    private RepositoryProviderTestingImpl repositoryProviderTestingImpl;

    @BeforeEach
    public void setUp() {
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

        repositoryProviderTestingImpl.setFileForFemaleRepository(Paths.get(FEMALE_FILE));
        repositoryProviderTestingImpl.setFileForMaleRepository(Paths.get(MALE_FILE));
    }

    @AfterAll
    public static void cleanUp() throws IOException {
        Files.deleteIfExists(Paths.get(FEMALE_FILE));
        Files.deleteIfExists(Paths.get(MALE_FILE));
    }

    @Test
    public void shouldDetectFemale() {
        //given
        String stringToCheck = "Anna Zbigniew Gertruda";

        //when
        String result = majorityRuleDetector.detect(stringToCheck);

        //then
        assertEquals(GenderDetector.FEMALE, result);
    }

    @Test
    public void shouldDetectFemaleRegardlessOfLeadingWhitespace() {
        //given
        String stringToCheck = "\nAnna Zbigniew Gertruda";

        //when
        String result = majorityRuleDetector.detect(stringToCheck);

        //then
        assertEquals(GenderDetector.FEMALE, result);
    }

    @Test
    public void shouldDetectFemaleRegardlessOfManyTokensUnknown() {
        //given
        String stringToCheck = "Zbigniew One Two Three Four Anna Maria";

        //when
        String result = majorityRuleDetector.detect(stringToCheck);

        //then
        assertEquals(GenderDetector.FEMALE, result);
    }

    @Test
    public void shouldDetectMale() {
        //given
        String stringToCheck = "Jan Anna Zbigniew";

        //when
        String result = majorityRuleDetector.detect(stringToCheck);

        //then
        assertEquals(GenderDetector.MALE, result);
    }

    @Test
    public void shouldDetectInconclusive() {
        //given
        String stringToCheck = "Jan Maria Rokita";

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