package com.tenetmind.genderdetector.repository.provider;

import com.tenetmind.genderdetector.repository.GenderRepository;

public interface RepositoryProvider {

    GenderRepository getFemaleRepository();

    GenderRepository getMaleRepository();

}
