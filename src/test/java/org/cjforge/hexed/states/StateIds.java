package org.cjforge.hexed.states;

/**
 * Created by mrakr_000 on 2014-09-07.
 */
public class StateIds {
    private static int nextId;
    private StateIds(){}

    public static synchronized int next() {
        return nextId++;
    }
}
