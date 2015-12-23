package com.hedwig.testng.globallogging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * <p>TestResultLogger class.</p>
 *
 * @author patrick
 * @version $Id: $Id
 */
public class TestResultLogger {

    private static final Logger logger = LogManager.getLogger(TestResultLogger.class.getName());


    private TestResultLogger() {
    }

    /**
     * <p>log.</p>
     *
     * @param message a {@link String} object.
     */
    public static void log(String message){
        logger.info("test_step={}",message);
        TestStepCollectors.pushLog(message);
    }

    /**
     * <p>log.</p>
     *
     * @param marker a {@link String} object.
     * @param keyInfos a {@link String} object.
     */
    public static void log(String marker,String ...keyInfos){
        logger.info("test_step: "+marker,keyInfos);
        TestStepCollectors.pushLog(convertMarker(marker,keyInfos));
    }

    public static void logWOTestStep(String marker,String ...keyInfos){
        logger.info("test_step: "+marker,keyInfos);
    }

    private static String convertMarker(String marker,Object ...keyInfos){
        String msg = marker;
        for (Object message : keyInfos) {
            msg= msg.replaceFirst("\\{\\}",message.toString());
        }
        return msg;
    }

    /**
     * <p>log.</p>
     *
     * @param marker a {@link String} object.
     * @param message a {@link Object} object.
     */
    public static void log(String marker,Object ...message){
        logger.info("test_step:"+marker,message);
        TestStepCollectors.pushLog(convertMarker(marker, message));
    }
    /**
     * <p>error.</p>
     *
     * @param message a {@link String} object.
     */
    public static void error(String message){
        logger.error("error_test_step={}", message);
    }

    /**
     * <p>error.</p>
     *
     * @param marker a {@link String} object.
     * @param keyInfos a {@link Object} object.
     */
    public static void error(String marker,Object ...keyInfos){
        logger.error("error_test_step="+marker,keyInfos);
        TestStepCollectors.pushLog(convertMarker(marker, keyInfos));
    }


    /**
     * <p>error.</p>
     *
     * @param e a {@link Throwable} object.
     */
    public static void error(Throwable e){
        logger.error("error ",e);
        TestStepCollectors.pushLog(e);
    }
    /**
     * <p>warn.</p>
     *
     * @param e a {@link Throwable} object.
     */
    public static void warn(Throwable e){
        logger.warn("error=",e);
        TestStepCollectors.pushLog(e);
    }

    /**
     * <p>warn.</p>
     *
     * @param message a {@link String} object.
     */
    public static void warn(String message){
        logger.info("warn_test_step={}",message);
        TestStepCollectors.pushLog(message);
    }
}
