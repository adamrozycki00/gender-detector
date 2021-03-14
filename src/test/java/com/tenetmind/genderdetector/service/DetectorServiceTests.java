package com.tenetmind.genderdetector.service;

import com.tenetmind.genderdetector.config.CoreConfiguration;
import com.tenetmind.genderdetector.detector.GenderDetector;
import com.tenetmind.genderdetector.repository.RepositoryProviderImpl;
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

    @Autowired
    private DetectorService detectorService;

    @Autowired
    private CoreConfiguration config;

    @BeforeEach
    public void setUp() {
        try (Formatter writer = new Formatter(config.getPathToFemaleTokens())) {
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

        try (Formatter writer = new Formatter(config.getPathToMaleTokens())) {
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
    public void shouldFindFemaleTokensPaginatedRegardlessOfImproperGenderGiven() throws IOException {
        //given & when
        List<String> tokensPaginated = detectorService.getTokens("", 2L, 3L);
        int pageSize = tokensPaginated.size();
        String tokenKatarzyna = tokensPaginated.get(0);

        //then
        assertEquals(3, pageSize);
        assertEquals(tokenKatarzyna, "Katarzyna");
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