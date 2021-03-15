package com.tenetmind.genderdetector.detector;

import com.tenetmind.genderdetector.repository.provider.RepositoryProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class MajorityRuleDetector implements GenderDetector {

    private final RepositoryProvider repositoryProvider;

    public MajorityRuleDetector(@Qualifier("repositoryProviderImpl") RepositoryProvider repositoryProvider) {
        this.repositoryProvider = repositoryProvider;
    }

    @Override
    public String detect(String sourceStringToCheck) {
        List<String> separateTokensToCheck = getSeparateTokens(sourceStringToCheck);
        final int numberOfTokensToCheck = separateTokensToCheck.size();

        int numberOfPotentialFemaleTokens = numberOfTokensToCheck;
        int numberOfPotentialMaleTokens = numberOfTokensToCheck;

        int numberOfConfirmedFemaleTokens = 0;
        int numberOfConfirmedMaleTokens = 0;

        try {
            for (String tokenToCheck : separateTokensToCheck) {
                if (repositoryProvider.getFemaleRepository().contains(tokenToCheck)) {
                    ++numberOfConfirmedFemaleTokens;
                } else {
                    --numberOfPotentialFemaleTokens;
                }

                if (numberOfConfirmedFemaleTokens > numberOfPotentialMaleTokens) {
                    return FEMALE;
                } else if (numberOfConfirmedMaleTokens > numberOfPotentialFemaleTokens) {
                    return MALE;
                }

                if (repositoryProvider.getMaleRepository().contains(tokenToCheck)) {
                    ++numberOfConfirmedMaleTokens;
                } else {
                    --numberOfPotentialMaleTokens;
                }

                if (numberOfConfirmedFemaleTokens > numberOfPotentialMaleTokens) {
                    return FEMALE;
                } else if (numberOfConfirmedMaleTokens > numberOfPotentialFemaleTokens) {
                    return MALE;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (numberOfConfirmedFemaleTokens > numberOfConfirmedMaleTokens) {
            return FEMALE;
        } else if (numberOfConfirmedMaleTokens > numberOfConfirmedFemaleTokens) {
            return MALE;
        }

        return INCONCLUSIVE;
    }

    private List<String> getSeparateTokens(String sourceStringToCheck) {
        if (sourceStringToCheck == null) {
            return new ArrayList<>();
        }

        return Arrays.asList(sourceStringToCheck.trim().split("\\s+"));
    }

}
