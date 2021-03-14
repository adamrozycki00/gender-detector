package com.tenetmind.genderdetector.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.stream.Stream;

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

    public boolean getRepositoriesDisjoint() throws IOException {
        boolean areRepositoriesDisjoint = false;

        long numberOfUniqueFemaleTokens = femaleRepository.getTokenStream().distinct().count();
        long numberOfUniqueMaleTokens = maleRepository.getTokenStream().distinct().count();

        long numberOfUniqueFemaleAndUniqueMaleTokens = numberOfUniqueFemaleTokens + numberOfUniqueMaleTokens;

        long numberOfUniqueTokensInConcatenatedRepositories = Stream.concat(femaleRepository.getTokenStream(),
                maleRepository.getTokenStream())
                .distinct()
                .count();

        if (numberOfUniqueFemaleAndUniqueMaleTokens == numberOfUniqueTokensInConcatenatedRepositories) {
            areRepositoriesDisjoint = true;
        }

        return areRepositoriesDisjoint;
    }

}
