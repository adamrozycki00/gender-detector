package com.tenetmind.genderdetector.repository;

import com.tenetmind.genderdetector.config.CoreConfiguration;
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
class GenderRepositoryTests {

    @Autowired
    private CoreConfiguration config;

    @Autowired
    private RepositoryProviderImpl repositoryProvider;

    @BeforeEach
    public void setUp() {
        try (Formatter writer = new Formatter(config.getPathToFemaleTokens())) {
            writer.format("Alicja\n");
            writer.format("Katarzyna\n");
            writer.format("Małgorzata\n");
            writer.format("Magdalena\n");
            writer.format("Krystyna\n");
            writer.format("Monika\n");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldReadTokensFromFile() throws IOException {
        //given & when
        long femaleRepositorySize = repositoryProvider.getFemaleRepository().getTokenStream().count();

        //then
        assertEquals(6, femaleRepositorySize);
    }

    @Test
    public void shouldFindTokensPaginated() throws IOException {
        //given & when
        List<String> tokensPaginated = repositoryProvider.getFemaleRepository().findTokensPaginated(2L, 3L);
        int pageSize = tokensPaginated.size();
        String tokenMagdalena = tokensPaginated.get(0);

        //then
        assertEquals(3, pageSize);
        assertEquals(tokenMagdalena, "Magdalena");
    }

    @Test
    public void shouldConfirmExistingToken() throws IOException {
        //given & when
        boolean containsKatarzyna = repositoryProvider.getFemaleRepository().contains("Katarzyna");

        //then
        assertTrue(containsKatarzyna);
    }

    @Test
    public void shouldNotConfirmNonExistingToken() throws IOException {
        //given & when
        boolean containsJoanna = repositoryProvider.getFemaleRepository().contains("Joanna");

        //then
        assertFalse(containsJoanna);
    }

}