package com.tenetmind.genderdetector.repository.implementation;

import com.tenetmind.genderdetector.config.CoreConfiguration;
import org.springframework.stereotype.Repository;

import java.nio.file.Paths;

@Repository
public class MaleFileRepository extends GenericFileRepository {

    public MaleFileRepository(CoreConfiguration config) {
        setFileContainingTokens(Paths.get(config.getPathToMaleTokens()));
    }

}
