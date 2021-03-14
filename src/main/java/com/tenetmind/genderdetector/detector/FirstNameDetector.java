package com.tenetmind.genderdetector.detector;

import com.tenetmind.genderdetector.repository.RepositoryProviderImpl;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class FirstNameDetector implements GenderDetector {

    private final RepositoryProviderImpl repositoryProviderImpl;
    private final boolean areRepositoriesDisjoint;

    public FirstNameDetector(RepositoryProviderImpl repositoryProviderImpl) throws IOException {
        this.repositoryProviderImpl = repositoryProviderImpl;
        areRepositoriesDisjoint = repositoryProviderImpl.getRepositoriesDisjoint();
    }

    @Override
    public String detect(String sourceStringToCheck) {
        if (areRepositoriesDisjoint) {
            return detectOnDisjoint(sourceStringToCheck);
        }

        return detectOnNonDisjoint(sourceStringToCheck);
    }

    private String detectOnDisjoint(String sourceStringToCheck) {
        String tokenToCheck = getFirstToken(sourceStringToCheck);

        try {
            if (repositoryProviderImpl.getFemaleRepository().contains(tokenToCheck)) {
                return FEMALE;
            }

            if (repositoryProviderImpl.getMaleRepository().contains(tokenToCheck)) {
                return MALE;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return INCONCLUSIVE;
    }

    private String detectOnNonDisjoint(String sourceStringToCheck) {
        String tokenToCheck = getFirstToken(sourceStringToCheck);

        boolean tokenIsFemale = false;
        boolean tokenIsMale = false;

        try {
            if (repositoryProviderImpl.getFemaleRepository().contains(tokenToCheck)) {
                tokenIsFemale = true;
            }

            if (repositoryProviderImpl.getMaleRepository().contains(tokenToCheck)) {
                tokenIsMale = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (tokenIsFemale && !tokenIsMale) {
            return FEMALE;
        }

        if (tokenIsMale && !tokenIsFemale) {
            return MALE;
        }

        return INCONCLUSIVE;
    }

    private String getFirstToken(String sourceStringToCheck) {
        if (sourceStringToCheck != null) {
            String[] separateTokensToCheck = sourceStringToCheck.trim().split("\\s+");

            if (separateTokensToCheck.length > 0) {
                return separateTokensToCheck[0];
            }
        }

        return "";
    }

}
