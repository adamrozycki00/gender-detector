package com.tenetmind.genderdetector.domain;

import com.tenetmind.genderdetector.repository.GenderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class FirstNameDetector implements GenderDetector {

    @Value("${disjoint.repositories}")
    private boolean repositoriesAreDisjoint;

    private final GenderRepository femaleRepository;

    private final GenderRepository maleRepository;

    @Autowired
    public FirstNameDetector(GenderRepository femaleRepository, GenderRepository maleRepository) {
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
        String tokenToCheck = getFirstToken(sourceStringToCheck);

        try {
            if (femaleRepository.contains(tokenToCheck)) {
                return FEMALE;
            }

            if (maleRepository.contains(tokenToCheck)) {
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
            if (femaleRepository.contains(tokenToCheck)) {
                tokenIsFemale = true;
            }

            if (maleRepository.contains(tokenToCheck)) {
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
        String[] singleNamesToCheck = sourceStringToCheck.split("\\s+");
        return singleNamesToCheck[0];
    }

}
