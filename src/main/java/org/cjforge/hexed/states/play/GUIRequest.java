package org.cjforge.hexed.states.play;

/**
 * This object enables asynchronous calls from game logic to graphic interface.
 * It allows one thread to block until Request can return result. When returned to another thread ca be used as
 * synchronizing mechanism between them.
 */
public class GUIRequest<T> {

    private static final long BLOCK_SLEEP_TIME = 10;

    private T result;
    private boolean finished;

    /**
     * Determines if Requested task is finished and value can be returned
     *
     * @return Boolean if task is finished
     */
    public boolean isFinished() {
        return finished;
    }

    /**
     * Set task finished flag
     *
     * @param finished Task finished flag
     */
    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    /**
     * Get request result
     *
     * @return Request result
     */
    public T getResult() {
        return result;
    }

    /**
     * Set request result. This operation should be done before setting task finished to true and not modified
     * after that.
     *
     * @param result Request result
     */
    public void setResult(T result) {
        this.result = result;
    }

    /**
     * This method will put current thread to sleep repeatedly checking if task is finished and return if so.
     *
     * @throws InterruptedException
     */
    public void blockUntilFinished() throws InterruptedException {
        while (true) {
            if (isFinished()) break;
            else Thread.sleep(BLOCK_SLEEP_TIME);
        }
    }
}
