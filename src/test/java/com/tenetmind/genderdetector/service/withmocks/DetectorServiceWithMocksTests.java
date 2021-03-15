package com.tenetmind.genderdetector.service.withmocks;

import com.tenetmind.genderdetector.config.CoreConfiguration;
import com.tenetmind.genderdetector.repository.implementation.FemaleFileRepository;
import com.tenetmind.genderdetector.repository.provider.RepositoryProvider;
import com.tenetmind.genderdetector.service.DetectorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;

@SpringBootTest
class DetectorServiceWithMocksTests {

    @Autowired
    private DetectorService detectorService;

    @Autowired
    private CoreConfiguration config;

    @Test
    public void shouldLimitPageSize() throws Exception {
        //given
        FemaleFileRepository femaleFileRepositoryMock = mock(FemaleFileRepository.class);
        RepositoryProvider repositoryProviderMock = mock(RepositoryProvider.class);

        detectorService.setRepositoryProvider(repositoryProviderMock);
        when(repositoryProviderMock.getFemaleRepository()).thenReturn(femaleFileRepositoryMock);

        String gender = "female";
        long page = 2L;
        long size = 300_000L;

        //when
        detectorService.getTokens(gender, page, size);

        //then
        verify(femaleFileRepositoryMock).findTokensPaginated(page, config.getPageSizeLimit());
    }

}