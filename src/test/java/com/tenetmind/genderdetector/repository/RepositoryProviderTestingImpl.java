package com.tenetmind.genderdetector.repository;

import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
public class RepositoryProviderTestingImpl extends RepositoryProviderImpl {

    public void setFileForFemaleRepository(Path path) {
        ((FemaleRepository) getFemaleRepository()).setFileContainingTokens(path);
    }

    public void setFileForMaleRepository(Path path) {
        ((MaleRepository) getMaleRepository()).setFileContainingTokens(path);
    }

}
