package org.cjforge.hexed.utils;

/**
 * Created by mrakr_000 on 2014-05-28.
 */
public interface TaskProgressListener {
    void onUpdate(String statusMsg, double percentage);
    void onFinish();
    void onError(Throwable ex);
}
