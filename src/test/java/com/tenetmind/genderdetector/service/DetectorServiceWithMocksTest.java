package com.tenetmind.genderdetector.service;

import com.tenetmind.genderdetector.config.CoreConfiguration;
import com.tenetmind.genderdetector.repository.FemaleRepository;
import com.tenetmind.genderdetector.repository.provider.RepositoryProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;

@SpringBootTest
class DetectorServiceWithMocksTest {

    @Autowired
    private DetectorService detectorService;

    @Autowired
    private CoreConfiguration config;

    @Test
    public void shouldLimitPageSize() throws Exception {
        //given
        FemaleRepository femaleRepositoryMock = mock(FemaleRepository.class);
        RepositoryProvider repositoryProviderMock = mock(RepositoryProvider.class);

        detectorService.setRepositoryProvider(repositoryProviderMock);
        when(repositoryProviderMock.getFemaleRepository()).thenReturn(femaleRepositoryMock);

        String gender = "female";
        long page = 2L;
        long size = 300_000L;

        //when
        detectorService.getTokens(gender, page, size);

        //then
        verify(femaleRepositoryMock).findTokensPaginated(page, config.getPageSizeLimit());
    }

}