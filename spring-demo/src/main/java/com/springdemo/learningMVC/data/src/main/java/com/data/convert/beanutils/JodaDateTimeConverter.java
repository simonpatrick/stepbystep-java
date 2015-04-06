package com.springdemo.learningMVC.data.src.main.java.com.data.convert.beanutils;

import com.google.common.base.MoreObjects;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.converters.AbstractConverter;
import org.joda.time.*;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;


public class JodaDateTimeConverter extends AbstractConverter {

    private transient Logger logger;

    private DateTimeZone timeZone;
    private DateTimeFormatter dateTimeFormatter;

    /**
     * Construct a JODA {@code DateTime} that throws a
     * <code>ConversionException</code> if an error occurs.
     */
    public JodaDateTimeConverter() {
        super();
        initialize();
    }

    /**
     * Construct a JODA {@code DateTime} that returns a default
     * value if an error occurs.
     *
     * @param defaultValue The default value to be returned.
     *                     if the value to be converted is missing or an error
     *                     occurs converting the value.
     */
    public JodaDateTimeConverter(Object defaultValue) {
        super(defaultValue);
        initialize();
    }

    protected void initialize() {
        timeZone = DateTimeZone.getDefault();
    }


    public DateTimeFormatter getDateTimeFormatter() {
        return dateTimeFormatter;
    }

    public void setDateTimeFormatter(DateTimeFormatter dateTimeFormatter) {
        this.dateTimeFormatter = dateTimeFormatter;
    }

    public DateTimeZone getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(DateTimeZone timeZone) {
        this.timeZone = timeZone;
    }

    protected DateTimeFormatter getFormatter() {
        return MoreObjects.firstNonNull(getDateTimeFormatter(),
                ISODateTimeFormat.dateTime());
    }

    @Override
    protected Class<?> getDefaultType() {
        return DateTime.class;
    }

    @Override
    protected <T> T convertToType(Class<T> type, Object value) throws Throwable {
//        Class<?> sourceType = value.getClass();

        if (value instanceof java.sql.Timestamp) {
            java.sql.Timestamp timestamp = (java.sql.Timestamp) value;
            long timeInMillis = ((timestamp.getTime() / 1000) * 1000);
            timeInMillis += timestamp.getNanos() / 1000000;
            return toDateTime(type, timeInMillis);
        }

        if (value instanceof java.util.Date) {
            java.util.Date date = (java.util.Date) value;
            return toDateTime(type, date.getTime());
        }

        if (value instanceof Calendar) {
            Calendar cal = (Calendar) value;
            return toDateTime(type, cal.getTime().getTime());
        }

        if (value instanceof Long) {
            Long longObj = (Long) value;
            return toDateTime(type, longObj);
        }

        // Convert all other types to String & handle
        String strValue = value.toString().trim();
        if (strValue.length() == 0) {
            return handleMissing(type);
        }
        return toDateTime(type, strValue);
    }

    Logger getLogger() {
        if (logger == null) {
            logger = LoggerFactory.getLogger(getClass());
        }
        return logger;
    }

    protected <T> T toDateTime(Class<T> type, long value) {
        // org.joda.time.DateTime
        if (type.equals(DateTime.class)) {
            return type.cast(new DateTime(value, timeZone));
        }

        // org.joda.time.LocalDate
        if (type.equals(LocalDate.class)) {
            return type.cast(new LocalDate(value, timeZone));
        }

        // org.joda.time.LocalTime
        if (type.equals(LocalTime.class)) {
            return type.cast(new LocalTime(value, timeZone));
        }

        if (type.equals(LocalDateTime.class)) {
            return type.cast(new LocalDateTime(value));
        }

        String msg = String.format("%s cannot handle conversion to '%s'",
                getClass().getSimpleName(), type);
        if (getLogger().isWarnEnabled()) {

            getLogger().warn("    {}", msg);
        }

        throw new ConversionException(msg);
    }

    protected <T> T toDateTime(Class<T> type, String input) {
        if (type.equals(DateTime.class)) {
            try {
                return type.cast(DateTime.parse(input, getFormatter()));
            } catch (IllegalArgumentException ex) {
                throw new ConversionException(ex.getMessage());
            }
        }
        if (type.equals(LocalDate.class)) {
            try {
                return type.cast(LocalDate.parse(input, getFormatter()));
            } catch (IllegalArgumentException ex) {
                throw new ConversionException(ex.getMessage());
            }
        }
        if (type.equals(LocalTime.class)) {
            try {
                return type.cast(LocalTime.parse(input, getFormatter()));
            } catch (IllegalArgumentException ex) {
                throw new ConversionException(ex.getMessage());
            }
        }
        if (type.equals(LocalDateTime.class)) {
            try {
                return type.cast(LocalDateTime.parse(input, getFormatter()));
            } catch (IllegalArgumentException ex) {
                throw new ConversionException(ex.getMessage());
            }
        }

        String msg = String.format("%s does not support default String to '%s' conversion.",
                getClass().getSimpleName(), type);
        if (getLogger().isWarnEnabled()) {
            getLogger().warn("    " + msg);
            getLogger().warn("    (N.B. Re-configure Converter or use alternative implementation)");
        }
        throw new ConversionException(msg);
    }

    @Override
    public String convertToString(Object value) throws Throwable {
        if (value instanceof DateTime) {
            DateTime dt = (DateTime) value;
            return dt.toString(getFormatter());
        }
        if (value instanceof LocalDate) {
            LocalDate ld = (LocalDate) value;
            return ld.toString(getFormatter());
        }
        if (value instanceof LocalTime) {
            LocalTime lt = (LocalTime) value;
            return lt.toString(getFormatter());
        }
        if (value instanceof LocalDateTime) {
            LocalDateTime ldt = (LocalDateTime) value;
            return ldt.toString(getFormatter());
        }

        return super.convertToString(value);
    }
}
