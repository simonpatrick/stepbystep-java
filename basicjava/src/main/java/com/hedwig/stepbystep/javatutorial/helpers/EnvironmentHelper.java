package com.hedwig.stepbystep.javatutorial.helpers;//package com.hedwig.stepbystep.javatutorial.helpers;
//
////import com.dooioo.automation.Environment;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
///**
// * Created by patrick on 15/3/3.
// *
// * @version $Id$
// */
//
//
//public class EnvironmentHelper {
//    private static final Logger logger = LogManager.getLogger(EnvironmentHelper.class.getName());
//    private static Environment environment;
//    private EnvironmentHelper(){}
//    private static final String JDBC_URL=".jdbc.url";
//    private static final String JDBC_DRIVER=".jdbc.driver";
//    private static final String JDBC_USERNAME=".jdbc.username";
//    private static final String JDBC_PASSWORD=".jdbc.password";
//    private static final String TARGET_ENVIRONMENT_KEY="target.environment";
//    private static String name = PropertiesHelper.getProperty(TARGET_ENVIRONMENT_KEY, "integration");
//
//    static{
//        environment = new Environment(name,name + JDBC_URL,name +
//                JDBC_DRIVER,name + JDBC_USERNAME,name + JDBC_PASSWORD);
//        logger.info("environment {} is initialized",environment.toString());
//    }
//
//    public static Environment build(String name){
//        return new Environment(name,name + JDBC_URL,name +
//                JDBC_DRIVER,name + JDBC_USERNAME,name + JDBC_PASSWORD);
//    }
//
//
//
//    public static String getTargetEnvironmentName(){
//        return name;
//    }
//
//    public static String getDbURL(){
//        return environment.getDbURL();
//    }
//
//    public static String getDbDriver(){
//        return environment.getDbDriver();
//    }
//
//    public static String getDbUserName(){
//        return environment.getDbUserName();
//    }
//
//    public static String getDbPassword(){
//        return environment.getDbPassword();
//    }
//
//}
