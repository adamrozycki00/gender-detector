package com.tenetmind.genderdetector.repository;

import org.junit.jupiter.api.AfterAll;
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

    private static final String FEMALE_FILE = "src/test/resources/female.txt";

    @Autowired
    private RepositoryProviderTestingImpl repositoryProviderTestingImpl;

    @AfterAll
    public static void cleanUp() throws IOException {
        Files.deleteIfExists(Paths.get(FEMALE_FILE));
    }

    @Test
    public void shouldReadTokensFromFile() throws IOException {
        //given
        try (Formatter writer = new Formatter(FEMALE_FILE)) {
            writer.format("Alicja\n");
            writer.format("Katarzyna\n");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        repositoryProviderTestingImpl.setFileForFemaleRepository(Paths.get(FEMALE_FILE));

        //when
        long femaleRepositorySize = repositoryProviderTestingImpl.getFemaleRepository().getTokenStream().count();

        //then
        assertEquals(2, femaleRepositorySize);
    }

    @Test
    public void shouldFindTokensPaginated() throws IOException {
        //given
        try (Formatter writer = new Formatter(FEMALE_FILE)) {
            writer.format("Alicja\n");
            writer.format("Katarzyna\n");
            writer.format("Małgorzata\n");
            writer.format("Magdalena\n");
            writer.format("Krystyna\n");
            writer.format("Monika\n");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        repositoryProviderTestingImpl.setFileForFemaleRepository(Paths.get(FEMALE_FILE));

        //when
        List<String> tokensPaginated = repositoryProviderTestingImpl.getFemaleRepository().findTokensPaginated(2L, 3L);
        int pageSize = tokensPaginated.size();
        String tokenMagdalena = tokensPaginated.get(0);

        //then
        assertEquals(3, pageSize);
        assertEquals(tokenMagdalena, "Magdalena");
    }

    @Test
    public void shouldConfirmExistingToken() throws IOException {
        //given
        try (Formatter writer = new Formatter(FEMALE_FILE)) {
            writer.format("Alicja\n");
            writer.format("Katarzyna\n");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        repositoryProviderTestingImpl.setFileForFemaleRepository(Paths.get(FEMALE_FILE));

        //when
        boolean containsKatarzyna = repositoryProviderTestingImpl.getFemaleRepository().contains("Katarzyna");

        //then
        assertTrue(containsKatarzyna);
    }

    @Test
    public void shouldNotConfirmNonExistingToken() throws IOException {
        //given
        try (Formatter writer = new Formatter(FEMALE_FILE)) {
            writer.format("Alicja\n");
            writer.format("Katarzyna\n");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        repositoryProviderTestingImpl.setFileForFemaleRepository(Paths.get(FEMALE_FILE));

        //when
        boolean containsMonika = repositoryProviderTestingImpl.getFemaleRepository().contains("Monika");

        //then
        assertFalse(containsMonika);
    }

}