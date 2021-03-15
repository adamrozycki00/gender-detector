package com.tenetmind.genderdetector.detector;

import com.tenetmind.genderdetector.repository.provider.RepositoryProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class FirstNameDetector implements GenderDetector {

    private final RepositoryProvider repositoryProvider;

    public FirstNameDetector(@Qualifier("repositoryProviderImpl") RepositoryProvider repositoryProvider) {
        this.repositoryProvider = repositoryProvider;
    }

    @Override
    public String detect(String sourceStringToCheck) {
        String tokenToCheck = getFirstToken(sourceStringToCheck);

        boolean tokenIsFemale = false;
        boolean tokenIsMale = false;

        try {
            if (repositoryProvider.getFemaleRepository().contains(tokenToCheck)) {
                tokenIsFemale = true;
            }

            if (repositoryProvider.getMaleRepository().contains(tokenToCheck)) {
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
