package com.tenetmind.genderdetector.detector;

public interface GenderDetector {

    String FEMALE = "FEMALE";
    String MALE = "MALE";
    String INCONCLUSIVE = "INCONCLUSIVE";
    
    String detect(String nameToCheck);

}
