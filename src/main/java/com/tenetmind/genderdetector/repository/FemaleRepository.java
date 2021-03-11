package com.tenetmind.genderdetector.repository;

import com.tenetmind.genderdetector.config.CoreConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.nio.file.Paths;

@Repository
public class FemaleRepository extends GenderRepositoryImpl {

    @Autowired
    private CoreConfiguration config;

    public FemaleRepository() {
        setFileContainingTokens(Paths.get(config.getPathToFemaleTokens()));
    }

}
