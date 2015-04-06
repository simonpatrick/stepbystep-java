
package com.springdemo.learningMVC.common.src.main.java.com.common.i18n;

import org.springframework.context.MessageSource;

import java.util.Locale;

/**
 * The custom {@code MessageSource} extends interface.

 */
public interface CustomMessageSource extends MessageSource {

    /**
     * Try to resolve the specified locale message. Return {@code null} if no message was found.
     *
     * @param code   the code to lookup up, such as 'calculator.noRateSet'.
     * @param locale the Locale in which to do the lookup.
     * @return the resolved message.
     */
    default String getMessage(String code, Locale locale) {
        return getMessage(code, null, null, locale);
    }

    /**
     * Try to resolve the specified locale message. Return default message if no message was found.
     *
     * @param code           the code to lookup up, such as 'calculator.noRateSet'.
     * @param locale         the Locale in which to do the lookup.
     * @param defaultMessage String to return if the lookup fails.
     * @return the resolved message if the lookup was successful;
     * otherwise the default message passed as a parameter
     */
    default String getMessage(String code, Locale locale, String defaultMessage) {
        return getMessage(code, null, defaultMessage, locale);
    }

    /**
     * Try to resolve the default locale message. Return {@code null} if no message was found.
     *
     * @param code the code to lookup up, such as 'calculator.noRateSet'.
     * @return the resolved message
     */
    default String getMessage(String code) {
        return getMessage(code, Locale.getDefault());
    }

    /**
     * Try to resolve the default locale message. Return {@code null} if no message was found.
     *
     * @param code the code to lookup up, such as 'calculator.noRateSet'.
     * @param arg1 argument that will be filled in for param within the message
     *             (params look like "{0}", "{0,date}", "{0,time}" within a message),
     *             or {@code null} if none.
     * @return the resolved message
     */
    default String getMessage(String code, Object arg1) {
        return getMessage(code, new Object[]{arg1}, null, Locale.getDefault());
    }

    /**
     * Try to resolve the default locale message. Return {@code null} if no message was found.
     *
     * @param code the code to lookup up, such as 'calculator.noRateSet'.
     * @param arg1 argument1 that will be filled in for param within the message
     *             (params look like "{0}", "{0,date}", "{0,time}" within a message),
     *             or {@code null} if none.
     * @param arg2 argument2 that will be filled in for param within the message
     *             (params look like "{1}", "{1,date}", "{1,time}" within a message),
     *             or {@code null} if none.
     * @return the resolved message
     */
    default String getMessage(String code, Object arg1, Object arg2) {
        return getMessage(code, new Object[]{arg1, arg2}, null, Locale.getDefault());
    }

    /**
     * Try to resolve the default locale message. Return {@code null} if no message was found.
     *
     * @param code the code to lookup up, such as 'calculator.noRateSet'.
     * @param arg1 argument1 that will be filled in for param within the message
     *             (params look like "{0}", "{0,date}", "{0,time}" within a message),
     *             or {@code null} if none.
     * @param arg2 argument2 that will be filled in for param within the message
     *             (params look like "{1}", "{1,date}", "{1,time}" within a message),
     *             or {@code null} if none.
     * @param arg3 argument2 that will be filled in for param within the message
     *             (params look like "{2}", "{2,date}", "{2,time}" within a message),
     *             or {@code null} if none.
     * @return the resolved message
     */
    default String getMessage(String code, Object arg1, Object arg2, Object arg3) {
        return getMessage(code, new Object[]{arg1, arg2, arg3}, null, Locale.getDefault());
    }
}
