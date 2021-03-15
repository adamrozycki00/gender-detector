package com.tenetmind.genderdetector.repository.implementation;

import com.tenetmind.genderdetector.config.CoreConfiguration;
import com.tenetmind.genderdetector.repository.MaleRepository;
import org.springframework.stereotype.Repository;

import java.nio.file.Paths;

@Repository
public class MaleFileRepository extends GenericFileRepository implements MaleRepository {

    public MaleFileRepository(CoreConfiguration config) {
        setFileContainingTokens(Paths.get(config.getPathToMaleTokens()));
    }

}
