package com.tenetmind.genderdetector.repository;

public interface RepositoryProvider {

    GenderRepository getFemaleRepository();

    GenderRepository getMaleRepository();

}
