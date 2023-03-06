package ru.job4j.io.piped;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class PipedUsage {
    public static void main(String[] args) throws IOException {
        final PipedInputStream in = new PipedInputStream();
        final PipedOutputStream out = new PipedOutputStream();
        Thread first = new Thread(
                () -> {
                    try {
                        out.write("Hello".getBytes());
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        );
        Thread second = new Thread(
                () -> {
                    try {
                        int ch;
                        while ((ch = in.read()) != -1) {
                            System.out.println((char) ch);
                        }
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        );
        in.connect(out);
        first.start();
        second.start();
    }
}
