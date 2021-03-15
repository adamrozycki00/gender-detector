package com.tenetmind.genderdetector.repository;

import com.tenetmind.genderdetector.config.CoreConfiguration;
import org.springframework.stereotype.Repository;

import java.nio.file.Paths;

@Repository
public class FemaleFileRepository extends GenericFileRepository implements FemaleRepository {

    public FemaleFileRepository(CoreConfiguration config) {
        setFileContainingTokens(Paths.get(config.getPathToFemaleTokens()));
    }

}
