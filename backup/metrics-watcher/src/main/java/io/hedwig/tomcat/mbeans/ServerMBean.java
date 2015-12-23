package io.hedwig.tomcat.mbeans;

/**
 * Created by patrick on 15/11/16.
 */
public interface ServerMBean {

    public void setLoggingLevel(int level);
    public long getUpTime();
}
