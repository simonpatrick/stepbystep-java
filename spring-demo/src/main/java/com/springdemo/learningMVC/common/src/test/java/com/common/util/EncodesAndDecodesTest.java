package com.springdemo.learningMVC.common.src.test.java.com.common.util;

import com.google.common.collect.ImmutableMap;
import org.hamcrest.core.Is;
import org.junit.Test;

import java.nio.charset.Charset;
import java.util.Map;

import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * {@link Encodes} and {@link Decodes} test cases.
 *
 * @author fuchun
 */
public class EncodesAndDecodesTest {

    private static final Charset GBK = Charset.forName("GBK");

    @Test
    public void testEncodeUrlAndDecodeUrl() throws Exception {
        String s2Src = "", s3Src = "    ", s4Src = "  \n\r";
        String s1 = Encodes.encodeUrl(null);
        String s2 = Encodes.encodeUrl(s2Src);
        String s3 = Encodes.encodeUrl(s3Src);
        String s4 = Encodes.encodeUrl(s4Src);

        assertThat(s1, nullValue());
        assertThat(s2, notNullValue());
        assertThat(s2, Is.is(s2Src));
        assertThat(s3, notNullValue());
        assertThat(s4, notNullValue());

        String s3Src0 = Decodes.decodeUrl(s3);
        String s4Src0 = Decodes.decodeUrl(s4);

        assertThat(s3Src0, notNullValue());
        assertThat(s4Src0, notNullValue());
        assertThat(s3Src0, Is.is(s3Src));
        assertThat(s4Src0, Is.is(s4Src));

        String s4_0 = null;
        try {
            s4_0 = Encodes.encodeUrl(s4Src, Charset.forName("OK"));
        } catch (Exception ex) {
            assertThat(ex, instanceOf(IllegalArgumentException.class));
        }

        assertThat(s4_0, nullValue());

        s3 = Encodes.encodeUrl("中国人", GBK);
        s4 = Encodes.encodeUrl("中国人");
        System.out.println(String.format("s1=%s, s2=%s, s3=%s, s4=%s",
                s1, s2, s3, s4));
    }

    @Test
    public void testEncodeAndDecodeQueryString() throws Exception {
        String s1Src0 = "unit=%&amp;value=5/38";
        String s1 = Encodes.encodeQueryString(s1Src0);

        assertThat(s1, notNullValue());
        String s1Src1 = Decodes.decodeUrl(s1);

        assertThat(s1Src1, notNullValue());
        assertThat(s1Src0.replaceAll("&amp;", "&"), Is.is(s1Src1));

        Map<String, String> params = Decodes.decodeQueryString(s1);

        assertThat(params, notNullValue());
        assertTrue(params.size() == 2);
    }

    @Test
    public void testDecodeUrl() throws Exception {
        String s2Code = "    ", s3Code = "  \n\r", s4Src = "/path/user/中國人/2";
        String s0 = Decodes.decodeUrl(null);
        String s1 = Decodes.decodeUrl("");
        String s2 = Decodes.decodeUrl(s2Code);
        String s3 = Decodes.decodeUrl(s3Code);
        String s4Code = Encodes.encodeUrl(s4Src);

        assertThat(s0, nullValue());
        assertThat(s1, notNullValue());
        assertTrue(s1.isEmpty());

        assertThat(s2, notNullValue());
        assertThat(s2, Is.is(s2Code));
        assertThat(s3, notNullValue());
        assertThat(s3, Is.is(s3Code));

        assertThat(s4Code, notNullValue());
        String s4 = Decodes.decodeUrl(s4Code);
        assertThat(s4, notNullValue());
        assertThat(s4, Is.is(s4Src));

        // 编码指定错误：
        try {
            Decodes.decodeUrl(s4Src, Charset.forName("OK"));
        } catch (Exception ex) {
            assertThat(ex, instanceOf(IllegalArgumentException.class));
        }

        // decode url 格式错误时，返回null
        String s5 = Decodes.decodeUrl("%7sDB9xy%7$");
        assertThat(s5, nullValue());
    }

    @Test
    public void testEncodeAndDecodeParams() throws Exception {
        Map<String, String> map = ImmutableMap.of(
                "name", "闷", "singer", "王菲");
        Map<String, String> encodedMap = Encodes.encodeUrlParams(map);

        assertThat(encodedMap, notNullValue());
        assertThat(encodedMap.size(), Is.is(map.size()));

        Map<String, String> srcMap = Decodes.decodeUrlParams(encodedMap);

        assertThat(srcMap, notNullValue());
        assertFalse(srcMap.isEmpty());
        assertThat(srcMap, Is.is(map));
    }
}