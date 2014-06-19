package org.cjforge.hexed.utils;

import org.cjforge.hexed.utils.components.ObservableStatus;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by mrakr_000 on 2014-05-28.
 */
public class Monitor {

    private static final long DEFAULT_TIMEOUT = 100;

    private final Observable observable;
    private final Collection<TaskProgressListener> listeners;
    private final long sampleTime;
    private final Thread monitor;

    public Monitor(Observable observable) {
        this(observable, DEFAULT_TIMEOUT);
    }
    public Monitor(Observable observable, long sampleTime) {
        this.observable = observable;
        this.sampleTime = sampleTime;
        listeners = new HashSet<>();
        monitor = new Thread(new MonitorRunnable());;
    }

    public void start(){
        Thread thread = new Thread(observable);
        Thread.UncaughtExceptionHandler h = new Thread.UncaughtExceptionHandler() {
            public void uncaughtException(Thread th, Throwable ex) {
                notifyListenersError(ex);
            }
        };
        thread.setUncaughtExceptionHandler(h);
        monitor.start();
        thread.start();
    }

    public void startSync() throws InterruptedException {
        start();
        waitForTask();
    }

    public void addListener(TaskProgressListener listener){
        listeners.add(listener);
    }

    public void notifyListeners(ObservableStatus status){
        for(TaskProgressListener listener : listeners){
            listener.onUpdate(status.statusMsg, status.percentage);
        }
    }
    public void notifyListenersFinished(){
        for(TaskProgressListener listener : listeners){
            listener.onFinish();
        }
    }
    public void notifyListenersError(Throwable ex){
        for(TaskProgressListener listener : listeners){
            listener.onError(ex);
        }
    }

    public void waitForTask() throws InterruptedException {
        monitor.join();
    }

    private class MonitorRunnable implements Runnable{

        private ObservableStatus lastStatus;
        @Override
        public void run() {
            do {
                ObservableStatus newStatus = observable.getStatus();
                if(!newStatus.equals(lastStatus)){
                    lastStatus = newStatus;
                    notifyListeners(newStatus);
                }
                try {
                    Thread.sleep(sampleTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }while(!observable.isFinished());
            notifyListenersFinished();
        }
    }
}
