package com.tenetmind.genderdetector.repository.implementation;

import com.tenetmind.genderdetector.config.CoreConfiguration;
import com.tenetmind.genderdetector.repository.FemaleRepository;
import org.springframework.stereotype.Repository;

import java.nio.file.Paths;

@Repository
public class FemaleFileRepository extends GenericFileRepository implements FemaleRepository {

    public FemaleFileRepository(CoreConfiguration config) {
        setFileContainingTokens(Paths.get(config.getPathToFemaleTokens()));
    }

}
