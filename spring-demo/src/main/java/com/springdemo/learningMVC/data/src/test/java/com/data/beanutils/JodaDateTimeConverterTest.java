
package com.springdemo.learningMVC.data.src.test.java.com.data.beanutils;

import com.data.convert.beanutils.EnumConverter;
import com.data.convert.beanutils.JodaDateTimeConverter;
import com.data.convert.beanutils.JodaLocalDateConverter;
import com.data.convert.beanutils.JodaLocalTimeConverter;
import com.data.domain.TUserStatus;
import com.data.domain.TestDomain;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.hamcrest.core.Is;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Test;

import java.util.Map;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

public class JodaDateTimeConverterTest {

    private final JodaDateTimeConverter dateTimeConverter =
            new JodaDateTimeConverter();
    private final JodaLocalDateConverter localDateConverter =
            new JodaLocalDateConverter();
    private final JodaLocalTimeConverter localTimeConverter =
            new JodaLocalTimeConverter();

    @Test
    public void testConvert() throws Throwable {
        // string to DateTime
        String input = "2014-10-10 12:50:43";
        dateTimeConverter.setDateTimeFormatter(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));

        DateTime dt = dateTimeConverter.convert(DateTime.class, input);

        assertThat(dt, notNullValue());
        assertThat(input, Is.is(dateTimeConverter.convertToString(dt)));

        input = "2014-10-10";
        localDateConverter.setDateTimeFormatter(DateTimeFormat.forPattern("yyyy-MM-dd"));

        LocalDate ld = localDateConverter.convert(LocalDate.class, input);
        assertThat(ld, notNullValue());
        assertThat(input, Is.is(localDateConverter.convertToString(ld)));

        input = "12:50:43";
        localTimeConverter.setDateTimeFormatter(DateTimeFormat.forPattern("HH:mm:ss"));

        LocalTime lt = localTimeConverter.convert(LocalTime.class, input);
        assertThat(lt, notNullValue());
        assertThat(input, Is.is(localTimeConverter.convertToString(lt)));
    }

    @Test
    public void testMapBean() throws Exception {
        TestDomain td = TestDomain.newInstance("Tomas", TUserStatus.VALIDATED);
        td.setId(3);

        BeanUtilsBean bub = BeanUtilsBean.getInstance();
        bub.getConvertUtils().register(new JodaDateTimeConverter(), DateTime.class);
        bub.getConvertUtils().register(new EnumConverter<>(
                TUserStatus.class, TUserStatus.CREATED), TUserStatus.class);

        Map<String, String> map = bub.describe(td);

        assertThat(map, notNullValue());

        TestDomain target = new TestDomain();
        bub.populate(target, map);

        assertThat(target, Is.is(td));
    }
}