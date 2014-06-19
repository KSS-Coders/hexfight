package org.cjforge.hexed.utils;

/**
 * Created by mrakr_000 on 2014-05-28.
 */
public class MonitorTest {

    public static void main(String... args) throws InterruptedException {
        new MonitorTest().run();
    }

    public void run() throws InterruptedException {
        Observable m = new ObservableTask("Starting", 10) {
            @Override
            public void performTask(){
                for (int i = 0; i <= 10; ++i) {
                    this.enterState("State " + i, i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Monitor mon = new Monitor(m);
        mon.addListener(new TaskProgressListener() {
            @Override
            public void onUpdate(String statusMsg, double percentage) {
                System.out.println(statusMsg + " - " + percentage + "%");
            }

            @Override
            public void onFinish() {
                System.out.println("Finished");
            }

            @Override
            public void onError(Throwable ex) {
                System.out.println("Exception has been thrown: " + ex);
                ex.printStackTrace();
            }
        });
        mon.startSync();
    }
}
