package ru.sergeykz.libmuffet;

import lombok.Getter;
import ru.sergeykz.libmuffet.files.TFile;
import ru.sergeykz.libmuffet.tasks.Task;
import ru.sergeykz.libmuffet.tasks.TaskGenerator;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.stream.Collectors;

@Getter
public class Muffet {

    List<TFile> scanResults = new ArrayList<>();
    private final MuffetConf conf;

    Queue<Task> tasks = new ConcurrentLinkedDeque<>();
    List<Worker> workers = new ArrayList<>();


    public Muffet(MuffetConf conf) {
        this.conf = conf;

        //workers initialization
        System.out.printf("[muffet] Creating %d workers... %n", conf.workersCount);
        for (int i = 0; i < conf.workersCount; i++) {
            workers.add(new Worker(i, tasks));
        }
    }
    public void run() {
        for (Worker w : workers) {
            w.start();
        }

    }
    public void finishWait() {

        while (!tasks.isEmpty() || hasTasks()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private boolean hasTasks() {
        boolean ch = false;
        for (Worker worker: workers) {
            ch = ch || worker.hasTasks();
        }
        return ch;
    }

    /**
     * Search meta files in configured input directory with filter
     * @param assetsScanTarget
     */
    public void scan(ScanFilter assetsScanTarget) {
        scan(new File(conf.input), assetsScanTarget);
    }


    public void scan3(File target, ScanFilter assetsScanTarget) {
        if (target == null || !target.exists() || !target.isDirectory()) return;

        File[] files = target.listFiles();
        if (files == null || files.length == 0) return;

        List<File> filteredFiles = Arrays.stream(files)
                .filter(f -> assetsScanTarget.isTarget(new TFile(new File(conf.input), f)))
                .collect(Collectors.toList());

        scanResults.addAll(filteredFiles.stream()
                .map(f -> new TFile(new File(conf.input), f))
                .collect(Collectors.toList()));

        filteredFiles.forEach(f -> scan(f, assetsScanTarget));
    }

    public void scan5(File target, ScanFilter assetsScanTarget) {
        if (target == null || !target.exists() || !target.isDirectory()) return;

        File[] files = target.listFiles();
        if (files == null || files.length == 0) return;

        for (File f: files) {
            if (assetsScanTarget.isTarget(new TFile(new File(conf.input), f))) {
                scanResults.add(new TFile(new File(conf.input), f));
            }
            scan(f, assetsScanTarget);
        }
    }
    public void scan2(File target, ScanFilter assetsScanTarget) {
        File[] files = target.listFiles();
        if (files == null || files.length == 0) return;
        for (File f: files) {
            TFile tf = new TFile(new File(conf.input), f);

            if (assetsScanTarget.isTarget(tf)) {
                scanResults.add(tf);
            }
            scan(f, assetsScanTarget);
        }
    }
    public void scan(File target, ScanFilter assetsScanTarget) {
        if (target == null || !target.exists() || !target.isDirectory()) return;

        File[] files = target.listFiles();
        if (files == null || files.length == 0) return;

        for (File f: files) {
            TFile tf = new TFile(new File(conf.input), f);

            if (assetsScanTarget.isTarget(tf)) {
                scanResults.add(tf);
            }
            scan(f, assetsScanTarget);
        }
    }




    public void generateTasks(TaskGenerator taskGen) {
        tasks.addAll(taskGen.genTasks());
    }
}
