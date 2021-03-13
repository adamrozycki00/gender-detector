package com.tenetmind.genderdetector.domain;

import com.tenetmind.genderdetector.repository.GenderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class MajorityRuleDetector implements GenderDetector {

    @Value("${disjoint.repositories}")
    private boolean repositoriesAreDisjoint;

    private final GenderRepository femaleRepository;

    private final GenderRepository maleRepository;

    @Autowired
    public MajorityRuleDetector(GenderRepository femaleRepository, GenderRepository maleRepository) {
        this.femaleRepository = femaleRepository;
        this.maleRepository = maleRepository;
    }

    @Override
    public String detect(String sourceStringToCheck) {
        if (repositoriesAreDisjoint) {
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
                if (femaleRepository.contains(tokenToCheck)) {
                    ++numberOfConfirmedFemaleTokens;
                }

                if (numberOfConfirmedFemaleTokens / numberOfTokensToCheck > decisionThreshold) {
                    return FEMALE;
                }

                if (maleRepository.contains(tokenToCheck)) {
                    ++numberOfConfirmedMaleTokens;
                }

                if (numberOfConfirmedMaleTokens / numberOfTokensToCheck > decisionThreshold) {
                    return MALE;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
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
                if (femaleRepository.contains(tokenToCheck)) {
                    ++numberOfConfirmedFemaleTokens;
                } else {
                    --numberOfPotentialFemaleTokens;
                }

                if (numberOfConfirmedFemaleTokens > numberOfPotentialMaleTokens) {
                    return FEMALE;
                } else if (numberOfConfirmedMaleTokens > numberOfPotentialFemaleTokens) {
                    return MALE;
                }

                if (maleRepository.contains(tokenToCheck)) {
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

        return INCONCLUSIVE;
    }

    private List<String> getSeparateTokens(String sourceStringToCheck) {
        return Arrays.asList(sourceStringToCheck.split("\\s+"));
    }

}
