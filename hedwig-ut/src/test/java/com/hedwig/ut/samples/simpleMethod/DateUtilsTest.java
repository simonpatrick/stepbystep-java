package com.hedwig.ut.samples.simpleMethod;

import org.testng.annotations.Test;

import java.util.Calendar;
import java.util.Date;

import static org.testng.Assert.*;

/**
 * Created by patrick on 15/8/13.
 *
 * @version $Id$
 */
@Test
public class DateUtilsTest {

    public void secondJuneWasSunday() {
        Date date = createDate(2013, 5, 2);

        Day day = DateUtils.getDayOfWeek(date);

        assertEquals(day, Day.Sunday);
    }

    public void thirdMayWasFriday() {
        Date date = createDate(2013, 4, 3);

        Day day = DateUtils.getDayOfWeek(date);

        assertEquals(day, Day.Friday);
    }

    public void tenthAprilWasWednesday() {
        Date date = createDate(2013, 3, 10);

        Day day = DateUtils.getDayOfWeek(date);

        assertEquals(day, Day.Wednesday);
    }

    private static Date createDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        return cal.getTime();
    }

}