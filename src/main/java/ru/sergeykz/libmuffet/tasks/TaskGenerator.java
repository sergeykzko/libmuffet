package ru.sergeykz.libmuffet.tasks;

import java.util.Queue;

public abstract class TaskGenerator {
    public abstract Queue<Task> genTasks();
}
