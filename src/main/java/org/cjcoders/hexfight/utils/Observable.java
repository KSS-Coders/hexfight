package org.cjcoders.hexfight.utils;

import org.cjcoders.hexfight.utils.components.ObservableStatus;

/**
 * Created by mrakr_000 on 2014-05-28.
 */
public interface Observable extends Runnable{
    ObservableStatus getStatus();
    boolean isFinished();
}
