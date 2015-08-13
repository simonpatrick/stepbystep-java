package com.hedwig.ut.samples.simpleMethod;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by patrick on 15/8/13.
 *
 * @version $Id$
 */


public class DateUtils {
    public static Day getDayOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_WEEK);
        return Day.values()[day - 1];
    }

}
