package io.hedwig.tomcat.mbeans;

/**
 * Created by patrick on 15/11/15.
 */
public interface RequestStatusMBean {
    public long getProcessingTime();
    public long getRequestCount();
    public void resetCounters();
}
