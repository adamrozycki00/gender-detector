package com.tenetmind.genderdetector.repository;

import java.io.IOException;

public interface RepositoryProvider {

    GenderRepository getFemaleRepository();

    GenderRepository getMaleRepository();

    boolean getRepositoriesDisjoint() throws IOException;

}
