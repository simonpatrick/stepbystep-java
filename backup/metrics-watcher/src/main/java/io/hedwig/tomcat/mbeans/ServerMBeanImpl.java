package io.hedwig.tomcat.mbeans;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;
import java.util.List;

/**
 * Created by patrick on 15/11/16.
 */
public class ServerMBeanImpl implements ServerMBean {
    private int _logLevel = 1;
    private long _startTime = 0L;

    public ServerMBeanImpl() {
        MBeanServer server = getServer();

        ObjectName name;
        try {
            name = new ObjectName("Application:Name=Server,Type=Server");
            server.registerMBean(this, name);
        } catch (Exception e) {
            e.printStackTrace();
        }

        _startTime = System.currentTimeMillis();
    }


    private MBeanServer getServer() {
        MBeanServer mbserver = null;

        List<MBeanServer> mbservers = MBeanServerFactory.findMBeanServer(null);

        if (mbservers.size() > 0) {
            mbserver = mbservers.get(0);
        }

        if (mbserver != null) {
            System.out.println("Found our MBean server");
        } else {
            mbserver = MBeanServerFactory.createMBeanServer();
        }

        return mbserver;
    }

    // interface method implementations
    @Override
    public void setLoggingLevel(int level) {
        _logLevel = level;
    }

    @Override
    public long getUpTime() {
        return System.currentTimeMillis() - _startTime;
    }
}
