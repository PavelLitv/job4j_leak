package ru.job4j.cache;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class DirFileCache extends AbstractCache<String, String> {
    private final String cachingDir;

    public DirFileCache(String cachingDir) {
        this.cachingDir = cachingDir;
    }

    @Override
    protected String load(String key) {
        String content = "";
        Path filePath = Path.of(cachingDir, key);
        try {
            content = Files.readString(filePath, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println(e);
        }
        return content;
    }
}
