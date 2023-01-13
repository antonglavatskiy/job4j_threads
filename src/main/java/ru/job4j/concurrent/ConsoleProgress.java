package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {

    @Override
    public void run() {
        try {
            int i = 0;
            var process = new char[] {'-', '\\', '|', '/'};
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(500);
                if (i == process.length) {
                    i = 0;
                }
                System.out.print("\rload: " + process[i]);
                i++;
            }
        } catch (InterruptedException e) {
            System.out.println(System.lineSeparator() + "Load completed");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(5000);
        progress.interrupt();
    }
}
