package name.seajay.libmuffet;

import name.seajay.libmuffet.tasks.Task;

import java.util.Queue;

public class Worker extends Thread {
    private final Queue<Task> tasks;

    public Worker(int i, Queue<Task> tasks) {
        this.tasks = tasks;
        this.setName("worker #" + i);
    }

    public void run() {

        while (!tasks.isEmpty()) {
            Task task = tasks.poll();
            if (task != null) task.run();
        }
    }
}
