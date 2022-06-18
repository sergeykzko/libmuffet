package name.seajay.libmuffet;

import lombok.Getter;
import name.seajay.libmuffet.files.TFile;
import name.seajay.libmuffet.tasks.Task;
import name.seajay.libmuffet.tasks.TaskGenerator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

@Getter
public class Muffet {

    List<TFile> scanResults = new ArrayList<>();
    private final MuffetConf conf;

    Queue<Task> tasks = new ConcurrentLinkedDeque<>();
    List<Worker> workers = new ArrayList<>();


    public Muffet(MuffetConf conf) {
        this.conf = conf;

        //workers initialization
        System.out.printf("Creating %d workers... %n", conf.workersCount);
        for (int i = 0; i < conf.workersCount; i++) {
            workers.add(new Worker(i, tasks));
        }
    }

    public void run() {
        for (Worker w : workers) {
            w.start();
        }
    }

    /**
     * Search meta files in configured input directory with filter
     *
     * @param assetsScanTarget
     */
    public void scan(ScanFilter assetsScanTarget) {
        scan(new File(conf.input), assetsScanTarget);
    }

    public void scan(File target, ScanFilter assetsScanTarget) {
        File[] files = target.listFiles();
        if (files == null || files.length == 0) return;
        for (File f : files) {
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
