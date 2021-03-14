package com.tenetmind.genderdetector.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RepositoryProviderImpl implements RepositoryProvider {

    @Autowired
    private GenderRepository femaleRepository;

    @Autowired
    private GenderRepository maleRepository;

    public GenderRepository getFemaleRepository() {
        return femaleRepository;
    }

    public GenderRepository getMaleRepository() {
        return maleRepository;
    }

}
