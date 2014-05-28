package org.cjcoders.hexfight.utils;

import org.cjcoders.hexfight.utils.components.ObservableStatus;

/**
 * Created by mrakr_000 on 2014-05-28.
 */
public abstract class ObservableTask implements Observable {

    private String status;
    private double counter;
    private final double maxCounter;
    private boolean finished;

    public ObservableTask(String initialStatus, double maxCounter) {
        this.status = initialStatus;
        this.maxCounter = maxCounter;
        counter = 0;
    }

    protected void enterState(String status, double counter){
        this.status = status;
        this.counter = counter;
    }
    abstract public void performTask();

    @Override
    public final void run(){
        finished = false;
        performTask();
        finished = true;
    }

    @Override
    public ObservableStatus getStatus() {
        return new ObservableStatus(status, counter/maxCounter * 100);
    }

    @Override
    public boolean isFinished(){
        return finished;
    }
}
