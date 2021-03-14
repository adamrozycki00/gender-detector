package com.tenetmind.genderdetector.repository;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

public interface GenderRepository {

    Stream<String> getTokenStream() throws IOException;

    List<String> findTokensPaginated(long page, long size) throws IOException;

    boolean contains(final String tokenToCheck) throws IOException;

}
