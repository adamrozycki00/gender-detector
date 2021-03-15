package com.tenetmind.genderdetector.repository.provider;

import com.tenetmind.genderdetector.repository.GenderRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("repositoryProviderImpl")
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
