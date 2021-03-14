package com.tenetmind.genderdetector.repository;

import com.tenetmind.genderdetector.config.CoreConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.nio.file.Paths;

@Repository
public class MaleRepository extends GenericGenderRepository {

    @Autowired
    public MaleRepository(CoreConfiguration config) {
        setFileContainingTokens(Paths.get(config.getPathToMaleTokens()));
    }

}
