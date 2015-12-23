package com.hedwig.jmx;

import javax.management.*;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by patrick on 15/12/5.
 */
public class AccessJMX {

    private static String JMX_URI ="service:jmx:rmi:///jndi/rmi://localhost:8080/jmxrmi";

    public static void main(String[] args) throws IOException, MalformedObjectNameException, AttributeNotFoundException, MBeanException, ReflectionException, InstanceNotFoundException {
        JMXServiceURL url = new JMXServiceURL(JMX_URI);
        JMXConnector jmxConnector = JMXConnectorFactory.connect(url);
        MBeanServerConnection mBeanServerConnection = jmxConnector.getMBeanServerConnection();

        Set<ObjectName> objects=mBeanServerConnection.queryNames(null, null);
        List<ObjectName> gcList = objects.stream().filter(objectName
                -> objectName.toString().contains("GarbageCollector"))
                .collect(Collectors.toList());
        //Fetch GC type collection time
        for(int i=0;i<gcList.size();i++){
            ObjectName queryName = new ObjectName(gcList.get(i).toString());
            System.out.print(queryName + ":");
            System.out.println(mBeanServerConnection.getAttribute(queryName, "CollectionTime"));
        }


        ObjectName uptimeName = new ObjectName("java.lang:type=Runtime");
        System.out.print("Server Uptime" + ":");
        System.out.print(mBeanServerConnection.getAttribute(uptimeName, "Uptime"));
        System.out.println( " Seconds" );

        //Fetch heap Memory usage stat
        //committed=523501568, max=523501568, used=106156848
        CompositeDataSupport cdsHeap;
        ObjectName  heapMemoryUsage= new ObjectName("java.lang:type=Memory");
        System.out.println(cdsHeap = (CompositeDataSupport) mBeanServerConnection.getAttribute(heapMemoryUsage, "HeapMemoryUsage"));
        System.out.println(cdsHeap.get("used"));

        //Fetch nonheap Memory usage stat
        //committed=523501568, max=523501568, used=106156848
        CompositeDataSupport cdsNonHeap;
        ObjectName  nonHeapMemoryUsage= new ObjectName("java.lang:type=Memory");
        System.out.println(cdsNonHeap = (CompositeDataSupport) mBeanServerConnection.getAttribute(nonHeapMemoryUsage, "NonHeapMemoryUsage"));
        System.out.println(cdsNonHeap.get("used"));

    }
}
