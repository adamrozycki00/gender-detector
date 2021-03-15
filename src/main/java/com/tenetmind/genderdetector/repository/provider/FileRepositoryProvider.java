package com.tenetmind.genderdetector.repository.provider;

import com.tenetmind.genderdetector.repository.GenderRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("fileRepositoryProvider")
public class FileRepositoryProvider implements RepositoryProvider {

    private final GenderRepository femaleRepository;
    private final GenderRepository maleRepository;

    public FileRepositoryProvider(GenderRepository femaleFileRepository, GenderRepository maleFileRepository) {
        this.femaleRepository = femaleFileRepository;
        this.maleRepository = maleFileRepository;
    }

    public GenderRepository getFemaleRepository() {
        return femaleRepository;
    }

    public GenderRepository getMaleRepository() {
        return maleRepository;
    }

}
