package com.tenetmind.genderdetector.detector;

import com.tenetmind.genderdetector.repository.RepositoryProviderImpl;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class MajorityRuleDetector implements GenderDetector {

    private final RepositoryProviderImpl repositoryProviderImpl;
    private final boolean areRepositoriesDisjoint;

    public MajorityRuleDetector(RepositoryProviderImpl repositoryProviderImpl) throws IOException {
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
        List<String> separateTokensToCheck = getSeparateTokens(sourceStringToCheck);
        final double numberOfTokensToCheck = separateTokensToCheck.size();

        int numberOfConfirmedFemaleTokens = 0;
        int numberOfConfirmedMaleTokens = 0;

        final double decisionThreshold = 0.5;

        try {
            for (String tokenToCheck : separateTokensToCheck) {
                if (repositoryProviderImpl.getFemaleRepository().contains(tokenToCheck)) {
                    ++numberOfConfirmedFemaleTokens;
                }

                if (numberOfConfirmedFemaleTokens / numberOfTokensToCheck > decisionThreshold) {
                    return FEMALE;
                }

                if (repositoryProviderImpl.getMaleRepository().contains(tokenToCheck)) {
                    ++numberOfConfirmedMaleTokens;
                }

                if (numberOfConfirmedMaleTokens / numberOfTokensToCheck > decisionThreshold) {
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

    private String detectOnNonDisjoint(String sourceStringToCheck) {
        List<String> separateTokensToCheck = getSeparateTokens(sourceStringToCheck);
        final int numberOfTokensToCheck = separateTokensToCheck.size();

        int numberOfPotentialFemaleTokens = numberOfTokensToCheck;
        int numberOfPotentialMaleTokens = numberOfTokensToCheck;

        int numberOfConfirmedFemaleTokens = 0;
        int numberOfConfirmedMaleTokens = 0;

        try {
            for (String tokenToCheck : separateTokensToCheck) {
                if (repositoryProviderImpl.getFemaleRepository().contains(tokenToCheck)) {
                    ++numberOfConfirmedFemaleTokens;
                } else {
                    --numberOfPotentialFemaleTokens;
                }

                if (numberOfConfirmedFemaleTokens > numberOfPotentialMaleTokens) {
                    return FEMALE;
                } else if (numberOfConfirmedMaleTokens > numberOfPotentialFemaleTokens) {
                    return MALE;
                }

                if (repositoryProviderImpl.getMaleRepository().contains(tokenToCheck)) {
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
