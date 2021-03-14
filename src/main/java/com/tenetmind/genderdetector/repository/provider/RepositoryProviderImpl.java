package com.tenetmind.genderdetector.repository.provider;

import com.tenetmind.genderdetector.repository.GenderRepository;
import org.springframework.stereotype.Service;

@Service
public class RepositoryProviderImpl implements RepositoryProvider {

    private final GenderRepository femaleRepository;
    private final GenderRepository maleRepository;

    public RepositoryProviderImpl(GenderRepository femaleRepository, GenderRepository maleRepository) {
        this.femaleRepository = femaleRepository;
        this.maleRepository = maleRepository;
    }

    public GenderRepository getFemaleRepository() {
        return femaleRepository;
    }

    public GenderRepository getMaleRepository() {
        return maleRepository;
    }

}
