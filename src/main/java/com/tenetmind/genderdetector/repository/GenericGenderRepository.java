package com.tenetmind.genderdetector.repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class GenericGenderRepository implements GenderRepository {

    private Path fileContainingTokens;

    public Stream<String> getTokenStream() throws IOException {
        return Files.lines(fileContainingTokens);
    }

    public List<String> findTokensPaginated(long page, long size) throws IOException {
        return getTokenStream()
                .skip(size * (page - 1))
                .limit(size)
                .collect(Collectors.toList());
    }

    public boolean contains(final String tokenToCheck) throws IOException {
        long count = getTokenStream()
                .filter(token -> token.equals(tokenToCheck))
                .count();
        return count > 0;
    }

    public Path getFileContainingTokens() {
        return fileContainingTokens;
    }

    public void setFileContainingTokens(Path fileContainingTokens) {
        this.fileContainingTokens = fileContainingTokens;
    }

}
