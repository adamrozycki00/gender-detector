package com.tenetmind.genderdetector.service;

import com.tenetmind.genderdetector.detector.GenderDetector;
import com.tenetmind.genderdetector.provider.DetectorProvider;
import com.tenetmind.genderdetector.repository.GenderRepository;
import com.tenetmind.genderdetector.repository.provider.RepositoryProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class DetectorService {

    private final DetectorProvider detectorProvider;

    private final RepositoryProvider repositoryProvider;

    public DetectorService(@Qualifier("detectorProviderImpl") DetectorProvider detectorProvider,
                           @Qualifier("repositoryProviderImpl") RepositoryProvider repositoryProvider) {
        this.detectorProvider = detectorProvider;
        this.repositoryProvider = repositoryProvider;
    }

    public String detectGender(String sourceStringToCheck, String detectorVariantName) {
        GenderDetector detector = detectorProvider.provide(detectorVariantName);
        return detector.detect(sourceStringToCheck);
    }

    public List<String> getTokens(String gender, long page, long size) throws IOException {
        GenderRepository repository = repositoryProvider.getFemaleRepository();

        if (gender.toLowerCase().startsWith("m")) {
            repository = repositoryProvider.getMaleRepository();
        }

        return repository.findTokensPaginated(page, size);
    }

}
