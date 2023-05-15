package ru.sergeykz.libmuffet;

import ru.sergeykz.libmuffet.tasks.Task;

import java.util.Queue;

public class Worker extends Thread {
    private final Queue<Task> tasks;
    Task task;

    public Worker(int i, Queue<Task> tasks) {
        this.tasks = tasks;
        this.setName("worker #" + i);
    }

    public void run() {

        while (!tasks.isEmpty()) {
            task = tasks.poll();
            if (task != null) task.run();
            task = null;
        }
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
    }

    public boolean hasTasks() {
        return task != null;
    }
}
