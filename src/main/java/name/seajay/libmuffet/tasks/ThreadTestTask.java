package name.seajay.libmuffet.tasks;

public class ThreadTestTask extends Task {
    @Override
    public void run() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
