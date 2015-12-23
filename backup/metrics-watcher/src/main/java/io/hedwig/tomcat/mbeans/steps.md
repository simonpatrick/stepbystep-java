## Step1: Authentication

copy tomcat-user.xml to conf/
visit:  http://localhost:8080/manager/jmxproxy/ after restart tomcat

## Step2: Build your MBean

### Four Types of MBeans 
- Standard
- Dynamic
- Model
- Open

Background

Java Management Extensions (JMX) is a standard for managing and monitoring applications and services. As a specification, JMX offers developers the ability to manage and monitor their application in a vendor-neutral (standard) fashion. The abstraction that JMX offers is similar to the abstractions of JDBC and EJB: most of the coding is vendor-neutral, and only the deployment descriptors are application server specific.

The fundamental entity within JMX is a management bean (MBean). There are four types of MBeans (Standard, Dynamic, Model, and Open), and each provide a different level of sophistication for management and monitoring. While much attention has been given to the client-side aspects of JMX, very little attention has been given to the server-side challenges of developing and deploying an MBean.

Purpose

The purpose of this paper is to document how to deploy a standard MBean in Tomcat. While researching this topic for a customer, I found that most documents described how to use an existing MBean to manage an application. For example, there are numerous documents out there that describe how to start/stop a web application, increase a JDBC connection pool size, or retrieve the number of currently active sessions in the EJB bean pool. However, there is no concise step-by-step guide to developing and deploying an application-level MBean within Tomcat.

This paper will document the exact steps for building and deploying a Standard MBean with Tomcat, and was achieved on 2005/02/17. For a complete list of library versions, click here. Throughout this paper I will make several asides to help the reader in their process. During my investigation, I found numerous undocumented features and dark corners in the Tomcat and mx4j implementations. Through these asides, you will hopefully avoid the pitfalls that I fell into and will be more productive.

Step #1: Authentication

Place the following in your conf/tomcat-users.xml file:

    <?xml version='1.0' encoding='utf-8'?>

    <tomcat-users>
      <role rolename="manager"/>
      <user username="admin" password="admin" roles="manager"/>
    </tomcat-users>
Aside
The default conf/tomcat-users.xml defines users, but fails to include the special role named "manager". Without including this role, you will not be able to identify an administrative user that has access to JMX proxy servlet (more on this later). It is extremely important to make sure you include the manager role, and you specify a user with the manager role so that you can use the JMX Proxy servlet to verify that your MBean has been successfully deployed.

After updating conf/tomcat-users.xml, restart Tomcat, and visit http://localhost:8080/manager/jmxproxy/. You should be prompted for the username and password you provided in the conf/tomcat-users.xml configuration file.

Step #2: Build your MBean

A Standard MBean requires you to develop two Java artifacts: an interface and a class that implements the interface. Here is the source code I used for my interface. It allows me to change the logging level at runtime, as well as retrieve the time (in seconds) that the server has been running:

  package org.wxnet.mbeans;

  public interface ServerMBean {
    public void setLoggingLevel(int level);
    public long getUptime();
  }
The MBean implementation is slightly more difficult.

  package org.wxnet.mbeans;

  import javax.management.MBeanServer;
  import javax.management.MBeanServerFactory;
  import javax.management.ObjectName;

  import java.util.ArrayList;

  public class Server implements ServerMBean {
    private int _logLevel = 1;
    private long _startTime = 0L;

    public Server() {
      MBeanServer server = getServer();

      ObjectName name = null;
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

      ArrayList mbservers = MBeanServerFactory.findMBeanServer(null);

      if (mbservers.size() > 0) {
        mbserver = (MBeanServer) mbservers.get(0);
      }

      if (mbserver != null) {
        System.out.println("Found our MBean server");
      } else {
        mbserver = MBeanServerFactory.createMBeanServer();
      } 

      return mbserver;
    }


    // interface method implementations
    public void setLoggingLevel(int level) { _logLevel = level; }
    public long getUptime() { return System.currentTimeMills() - _startTime; }
  }
Explanation
MBeans are deployed into an MBeanServer. The constructor for the Server class registers one and only one instance of the MBean with the MBeanServer. During this registration process, the MBean prepares an ObjectName instance that is used to identify the MBean being deployed. It is very similar to how JNDI is used to describe EJBs.

The getServer() method uses a factory (implemented by your application server), to locate multiple MBean servers. If the application server does not support JMX (in which case no MBean servers exist), the method creates a new MBeanServer, and returns the newly created server instance.

While all of this seems awfully expensive work in a constructor, temper your frustration by understanding the JMX usage idiom. Only one MBean instance is deployed within the server. MBeans are essentially Singletons (although this is not by any means a requirement), and so the expensive cost of construction is not recurring and thus not as costly as one would initially believe.

Aside
Most code examples for registering an MBean with the server are application server specific. Given that we're using JMX as a way to make our management logic more portable, it is counter-intuitive (outright backwards) to forego application-server neutrality for the convenience of a few lines of code. I highly recommend the above application-server neutral mechanism for resolving the server, and deploying your MBean.

An extremely dark corner in the Tomcat implementation is it's use of an ancient version of MX4J. The MX4J implementation (contained within the bin/jmx.jar file) is so old that you cannot even download it anymore. The dark corner within MX4J is it's use of nomenclature to guarantee consistency. I'm not certain if this is required in the JMX spec or not, but you must name your interface FooMBean if your implementing class is named Foo. If you do not do this, you will receive an exception upon deployment stating: MBean is not compliant. This is extremely important!!!

Step #3: Build a ContextListener

Unlike other application servers, Tomcat is not particularly conducive to MBean server-side development. Most other application servers will perform a Class.forName(mbeanName).newInstance() when the MBean is deployed. Because the code in the MBean implementation's constructor registers the object as an MBean, deployment is virtually transparent to the developer. Tomcat doesn't play that nice.

To load your MBean implementation at deployment time, you can either use a servlet and specify load-at-start in the web.xml deployment descriptor, or you can implement your own ContextListener. I recommend implementing a ContextListener for no reason other than aesthetics. ContextListeners were developed exclusively for the purpose of creating a callback to the application when it is deployed. My ContextListener implementation follows:

package org.wxnet; 

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.wxnet.mbeans.Server;

public final class ContextListener
  implements ServletContextListener {

  public void contextInitialized(ServletContextEvent event) {
    Server mbean = new Server();
  }

  public void contextDestroyed(ServletContextEvent event) { }

}
Notes
This code should be compiled and placed in a JAR file contained within your webapp's WEB-INF/lib directory. To underscore the importance of this, I have intentionally placed my ContextListener class in a separate package from my MBeans. You will also need to augment your existing web.xml deployment descriptor and add a hook to your newly created ContextListener:

<?xml version="1.0" encoding="ISO-8859-1"?>

<!DOCTYPE web-app
    PUBLIC "-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN"
    "http://java.sun.com/dtd/web-app_2_3.dtd">

<web-app>
  <display-name>My Web Application</display-name>

  <listener>
    <listener-class>org.wxnet.ContextListener</listener-class>
  </listener>
</web-app>
Aside
The web-app 2.2 DTD does not define the listener element as a child of the web-app root element. In order for you to validate the syntax of your web.xml deployment descriptor, make sure you have identified the web-app 2.3 DTD correctly both in the public and system identifiers in the XML preamble. If you enable syntax checking in Tomcat's server.xml, and your web.xml is not correct (perhaps your order is not correct), you will receive exceptions.

Step #4: Prepare the mbeans-descriptor.xml

You now need to prepare a Tomcat specific deployment descriptor named mbeans-descriptor.xml. This descriptor tells Tomcat about your MBean, and is examined during the deployment process. Be careful and make sure the values you provide reflect your implementation, because if they are not you will receive MBean is not compliant exceptions. My mbeans-descriptor.xml looks like this:

<?xml version="1.0"?>

<mbeans-descriptors>
  <mbean name="Server"
         className="org.apache.catalina.mbeans.ClassNameMBean"
         description="Server statistics and configuration"
         domain="Catalina"
         type="org.wxnet.mbeans.Server">
    
    <attribute name="className"
               description="Fully qualified class name of the managed object"
               type="java.lang.String"
               writeable="false"/>

    <attribute name="debug"
               description="The debugging detail level for this component"
               type="int"/>

    <operation name="setLoggingLevel"
               description="Sets the logging level for the application."
               impact="ACTION"
               return="void">
      <parameter name="level"
                 description="The logging level."
                 type="int"/>
    </operation>

    <operation name="getUptime"
               description="Returns the uptime (in seconds) for the application."
               impact="ACTION"
               return="long">
    </operation>

  </mbean>
</mbeans-descriptors>
Notes
As far as I can tell, the name attribute on the mbean element is not used for validation of the MBean implementation. You can name your MBean whatever you like. The type attribute defines which class implements your MBean. I don't believe the two attribute fields are required, but I left them there because every example I found included them. The two operations elements are a 1-1 match up against the methods defined in your MBean interface and are required.

When you have completed your MBean implementation class, interface, and deployment descriptor, place the compiled files and the deployment descriptor in a JAR file. Do not include this JAR file in your webapp's WEB-INF/lib directory! Instead, copy the JAR to Tomcat's common/lib directory. The Tomcat common Classloader must have access to your MBean interface and implementatation, as well as the deployment descriptor.

Aside
Do not mistake the server/lib directory for the common/lib directory. The MBean JAR file should be placed in the common/lib directory.

Step #5: Prepare the server.xml

At this point most of the work is complete. Verify that you have completed the following just to be safe:

You have enabled administrator authentication in Tomcat using the conf/tomcat-users.xml file.
Your MBean interface and implementation class, along with the mbeans-descriptor.xml are JAR'ed together and have been copied to the common/lib directory.
You have written a custom ContextListener that instantiates your MBean (thereby registering it with the server), and hooked your ContextListener by identifying it as listener-class in your web.xml deployment descriptor.
The last step in the process is to make a minor adjustment to Tomcat's conf/server.xml configuration file. You need to point Tomcat to your MBean deployment descriptor using a Class-Path style path. Additionally, you'll want set unpackWARs="false" and xmlValidation="true". My segment of server.xml that covers this is as follows:
  <Listener className="org.apache.catalina.mbeans.ServerLifecycleListener"
            debug="0"
            descriptors="/mbeans-descriptor.xml"/>

  ...

  <Host name="localhost" 
        debug="0" 
        appBase="webapps"
        unpackWARs="false" 
        autoDeploy="true"
        xmlValidation="true" 
        xmlNamespaceAware="false">
Notes
Even the best developers make mistakes, and a very common mistake developers make while working with Tomcat is failing to remove their old exploded web application. The default configuration for Tomcat (unpackWARs="true") instructs Tomcat to explode your .war file on startup. However, upon the next restart Tomcat will disregard your .war file if an exploded copy is found. Tomcat disregards the .war file even if it is newer than the timestamp on the directory for your exploded web application. It is quite common for a developer to make a mistake during the development process, re-assemble the WAR file, copy it to the webapps directory, and restart Tomcat without deleting the previously exploded copy of the WAR. As a result, I highly recommend you instruct Tomcat to not unpack the WAR file.

Aside
I also enabled xmlValidation as a way to troubleshoot any syntax problems in my web.xml. During my investigation I placed my ContextListener hook in web.xml along with System.out.println() statements in my ContextListener. Upon restarting Tomcat I did not witness my debug statements, leading me to believe that I did not properly hook my ContextListener. After awhile of debugging, I realized that my web.xml syntax was in fact valid and that my real problem was that Tomcat was loading the previously exploded copy of my webapp.

Make sure you include / as the starting prefix in your descriptors attribute. If you placed the mbeans-descriptor.xml deployment descriptor in the root of the JAR file containing your MBean, then /mbeans-descriptor.xml will work. If you placed the descriptor in the same package as your MBean (org.wxnet.mbeans in my case), you'll want to use /org/wxnet/mbeans/mbeans-descriptor.xml as the value.

Verify the Deployment

Start the server, and verify the deployment by going to the JMX Proxy Servlet located at http://localhost:8080/manager/jmxproxy/. Search for your MBean's fully qualified classname, and you should find your MBean deployed and ready for use.

Conclusions

Very little end-to-end information on Tomcat and custom MBeans exists (or existed at the time of this writing). It took me roughly a day to trace through Tomcat source code (some of which is god-awful), along with ancient versions of MX4J (also quite poor) in order to determine which mystical artifacts I needed to assemble in order to deploy my own MBean into Tomcat. This guide serves as documentation of the process I followed, and will hopefully help the reader get a head start on deploying his/her own MBeans within Tomcat.

Version Information

The above document relates to these exact versions, and cannot be guaranteed to work on other platforms (although it should work).
Tomcat v5.0.28
J2SDK 1.4.2
Stock mx4j implementation contained within Tomcat (bin/jmx.jar)

Updates

2006-07-18: Derek Van Assche reports the following environment also works:
Tomcat v5.5.7
Sun JRE 1.5.0_06

Feedback:
Christopher Blunck
$Date$
$Revision$
