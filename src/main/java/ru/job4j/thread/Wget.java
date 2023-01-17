package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.regex.Pattern;

public class Wget implements Runnable {
    private final String url;
    private final int speed;
    private final String fileName;
    public static final Pattern FILE_NAME =
            Pattern.compile("^[\\w,\\s-]+[.][A-Za-z0-9]{3}$");
    public static final int BUFFER = 1024;
    public static final int ONE_SECOND = 1000;

    public Wget(String url, int speed, String fileName) {
        this.url = url;
        this.speed = speed;
        this.fileName = fileName;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
            byte[] dataBuffer = new byte[BUFFER];
            int bytesRead;
            int downloadData = 0;
            long startTime = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, BUFFER)) != -1) {
                downloadData += bytesRead;
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                if (downloadData >= speed) {
                    long workTime = System.currentTimeMillis() - startTime;
                    if (workTime < ONE_SECOND) {
                        Thread.sleep(ONE_SECOND - workTime);
                    }
                    downloadData = 0;
                    startTime = System.currentTimeMillis();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void validate(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("Enter url, speed and file name as parameters");
        }
        if (!args[0].startsWith("http")) {
            throw new IllegalArgumentException("Enter url as first parameter");
        }
        if (Integer.parseInt(args[1]) < 1) {
            throw new IllegalArgumentException("Enter speed more than zero");
        }
        if (!FILE_NAME.matcher(args[2]).matches()) {
            throw new IllegalArgumentException("Enter correct file name");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        validate(args);
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        String fileName = args[2];
        Thread wget = new Thread(new Wget(url, speed, fileName));
        wget.start();
        wget.join();
        long finish = System.currentTimeMillis();
        System.out.println(finish - start);
    }
}
