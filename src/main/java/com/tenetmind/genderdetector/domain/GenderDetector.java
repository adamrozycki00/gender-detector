package com.tenetmind.genderdetector.domain;

public interface GenderDetector {

    String FEMALE = "FEMALE";
    String MALE = "MALE";
    String INCONCLUSIVE = "INCONCLUSIVE";
    
    String detect(String nameToCheck);

}
