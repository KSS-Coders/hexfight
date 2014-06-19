package org.cjforge.hexed.utils.components;

/**
 * Created by mrakr_000 on 2014-05-28.
 */
public class ObservableStatus {
    public final String statusMsg;
    public final double percentage;

    public ObservableStatus(String statusMsg, double percentage) {
        this.statusMsg = statusMsg;
        this.percentage = percentage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ObservableStatus that = (ObservableStatus) o;

        if (Double.compare(that.percentage, percentage) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        long temp = Double.doubleToLongBits(percentage);
        return (int) (temp ^ (temp >>> 32));
    }
}
