package org.cjcoders.hexfight.utils;

import org.apache.log4j.Logger;

/**
 * Created by mrakr_000 on 2014-06-01.
 */
public class Profiler {

    public static final int MICROS = 1000;
    public static final int NANOS = 1;
    public static final int MILIS = 1000000;

    private Long startTime;
    private Long lastMeasure;
    private Logger l;
    private int units;
    private boolean enabled;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Profiler(String name, int units) {
        l = Logger.getLogger(name);
        this.units = units;
        this.enabled = true;
    }

    public void start(){
        startTime = System.nanoTime();
        lastMeasure = startTime;
    }

    public void log(String msg){
        if(isEnabled()) {
            if (lastMeasure != null) {
                Long currentMeasure = System.nanoTime();
                l.info(msg + " [measured : " + (currentMeasure - lastMeasure) / units + "]");
                lastMeasure = currentMeasure;
            }
        }
    }
    public void logFromStart(String msg){
        if(isEnabled()) {
            if (lastMeasure != null) {
                Long currentMeasure = System.nanoTime();
                l.info(msg + " [measured : " + (currentMeasure - startTime) / units + "]");
                lastMeasure = currentMeasure;
            }
        }
    }
}
