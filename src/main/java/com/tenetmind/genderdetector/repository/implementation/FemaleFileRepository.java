package com.tenetmind.genderdetector.repository.implementation;

import com.tenetmind.genderdetector.config.CoreConfiguration;
import org.springframework.stereotype.Repository;

import java.nio.file.Paths;

@Repository
public class FemaleFileRepository extends GenericFileRepository {

    public FemaleFileRepository(CoreConfiguration config) {
        setFileContainingTokens(Paths.get(config.getPathToFemaleTokens()));
    }

}
