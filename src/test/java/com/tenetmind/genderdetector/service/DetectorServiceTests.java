package com.tenetmind.genderdetector.service;

import com.tenetmind.genderdetector.detector.GenderDetector;
import com.tenetmind.genderdetector.repository.FemaleRepository;
import com.tenetmind.genderdetector.repository.GenderRepository;
import com.tenetmind.genderdetector.repository.MaleRepository;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DetectorServiceTests {

    private static final String FEMALE_FILE = "src/test/resources/female.txt";
    private static final String MALE_FILE = "src/test/resources/male.txt";

    @Autowired
    private GenderRepository femaleRepository;

    @Autowired
    private GenderRepository maleRepository;

    @Autowired
    private DetectorService detectorService;

    @BeforeEach
    public void setUp() {
        ((FemaleRepository) femaleRepository).setFileContainingTokens(Paths.get(FEMALE_FILE));
        ((MaleRepository) maleRepository).setFileContainingTokens(Paths.get(MALE_FILE));

        try (Formatter writer = new Formatter(FEMALE_FILE)) {
            writer.format("Maria\n");
            writer.format("Dorota\n");
            writer.format("Alicja\n");

            writer.format("Katarzyna\n");
            writer.format("Małgorzata\n");
            writer.format("Magdalena\n");

            writer.format("Krystyna\n");
            writer.format("Monika\n");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try (Formatter writer = new Formatter(MALE_FILE)) {
            writer.format("Jan\n");
            writer.format("Antoni\n");

            writer.format("Zygmunt\n");
            writer.format("Tomasz\n");

            writer.format("Adam\n");
            writer.format("Krzysztof\n");

            writer.format("Patryk\n");
            writer.format("Sebastian\n");
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
    public void shouldFindFemaleTokensPaginated() throws IOException {
        //given & when
        List<String> tokensPaginated = detectorService.getTokens("female", 2L, 3L);
        int pageSize = tokensPaginated.size();
        String tokenKatarzyna = tokensPaginated.get(0);

        //then
        assertEquals(3, pageSize);
        assertEquals(tokenKatarzyna, "Katarzyna");
    }

    @Test
    public void shouldFindMaleTokensPaginated() throws IOException {
        //given & when
        List<String> tokensPaginated = detectorService.getTokens("male", 3L, 2L);
        int pageSize = tokensPaginated.size();
        String tokenAdam = tokensPaginated.get(0);

        //then
        assertEquals(2, pageSize);
        assertEquals(tokenAdam, "Adam");
    }

    @Test
    public void shouldDetectMale() {
        //given
        String detectorVariantName = "first";
        String stringToCheck = "Jan Maria Rokita";

        //when
        String result = detectorService.detectGender(stringToCheck, detectorVariantName);

        //then
        assertEquals(GenderDetector.MALE, result);
    }

    @Test
    public void shouldDetectInconclusive() {
        //given
        String detectorVariantName = "majority";
        String stringToCheck = "Jan Maria Rokita";

        //when
        String result = detectorService.detectGender(stringToCheck, detectorVariantName);

        //then
        assertEquals(GenderDetector.INCONCLUSIVE, result);
    }

}