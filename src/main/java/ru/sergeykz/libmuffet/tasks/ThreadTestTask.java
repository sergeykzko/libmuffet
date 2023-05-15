package ru.sergeykz.libmuffet.tasks;

public class ThreadTestTask extends Task {
    @Override
    public void run() {
//        System.out.println("Hello from thread - " + Thread.currentThread().getName());
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
