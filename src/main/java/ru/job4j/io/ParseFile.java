package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public final class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    private synchronized String getContent(Predicate<Character> filter) {
        StringBuilder output = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            int data;
            while ((data = in.read()) > 0) {
                if (filter.test((char) data)) {
                    output.append((char) data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toString();
    }

    public synchronized String getContentWithUnicode() {
        return getContent(character -> true);
    }

    public String getContentWithoutUnicode() {
        return getContent(character -> character < 0x80);
    }
}
