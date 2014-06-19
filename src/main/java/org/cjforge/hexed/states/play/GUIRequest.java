package org.cjforge.hexed.states.play;

/**
 * Created by mrakr_000 on 2014-06-02.
 */
public class GUIRequest<T> {

    private static final long BLOCK_SLEEP_TIME = 10;

    private T result;
    private boolean finished;

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public void blockUntilFinished() throws InterruptedException {
        while(true) {
            if (isFinished()) break;
            else Thread.sleep(BLOCK_SLEEP_TIME);
        }
    }
}
