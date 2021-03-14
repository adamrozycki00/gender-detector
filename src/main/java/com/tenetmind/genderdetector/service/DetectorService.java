package com.tenetmind.genderdetector.service;

import com.tenetmind.genderdetector.detector.GenderDetector;
import com.tenetmind.genderdetector.provider.DetectorProvider;
import com.tenetmind.genderdetector.repository.GenderRepository;
import com.tenetmind.genderdetector.repository.provider.RepositoryProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class DetectorService {

    @Autowired
    private DetectorProvider detectorProvider;

    @Autowired
    private RepositoryProviderImpl repositoryProviderImpl;

    public String detectGender(String sourceStringToCheck, String detectorVariantName) {
        GenderDetector detector = detectorProvider.provide(detectorVariantName);
        return detector.detect(sourceStringToCheck);
    }

    public List<String> getTokens(String gender, long page, long size) throws IOException {
        GenderRepository repository = repositoryProviderImpl.getFemaleRepository();

        if (gender.toLowerCase().startsWith("m")) {
            repository = repositoryProviderImpl.getMaleRepository();
        }

        return repository.findTokensPaginated(page, size);
    }

}
