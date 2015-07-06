package com.springdemo.learningMVC.common.src.test.java.com.common.util;

import org.hamcrest.core.Is;
import org.hamcrest.core.IsInstanceOf;
import org.hamcrest.core.IsNull;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class AssemblerTest {

    @Test
    public void testTransform() throws Exception {
        Assembler<String, Integer> stringToInt = Integer::decode;

        Integer value = stringToInt.transform("1");

        assertThat(value, Is.is(1));

        try {
            stringToInt.transform("a");
        } catch (Exception ex) {
            assertThat(ex, IsInstanceOf.instanceOf(NumberFormatException.class));
        }
    }

    @Test
    public void testCompose() throws Exception {
        byte[] bytes = "123".getBytes();
        Assembler<String, Integer> stringToInt = Integer::decode;
        Assembler<byte[], Integer> bytesToInt = stringToInt.compose(String::new);

        Integer value = bytesToInt.transform(bytes);

        assertThat(value, Is.is(123));
    }

    @Test
    public void testAndThen() throws Exception {
        Assembler<String, Integer> stringToInt = Integer::decode;
        Assembler<byte[], String> bytesToString = String::new;

        Assembler<byte[], Integer> bytesToInt = bytesToString.andThen(stringToInt);

        Integer value = bytesToInt.transform("123".getBytes());

        assertThat(value, Is.is(123));
    }

    @Test
    public void testIdentity() throws Exception {
        List<String> names = Arrays.asList("foo", "bar");

        Map<String, String> map = names.stream().collect(Collectors.toMap(
                Assembler.<String>identity().toFunction(),
                Assembler.<String>identity().toFunction()));

        assertThat(map.size(), Is.is(2));
    }

    @Test
    public void testTransformList() throws Exception {
        Assembler<String, Integer> stringToInt = Integer::decode;

        List<Integer> list0 = Assembler.transformList(null, stringToInt);

        assertThat(list0, IsNull.notNullValue());
        assertTrue(list0.isEmpty());

        List<Integer> list1 = Assembler.transformList(Collections.emptyList(), stringToInt);

        assertThat(list1, IsNull.notNullValue());
        assertTrue(list1.isEmpty());

        List<String> source = Arrays.asList("1", "2", null, "3");

        List<Integer> list2 = Assembler.transformList(source, stringToInt);

        assertThat(list2, IsNull.notNullValue());
        assertFalse(list2.isEmpty());

        assertThat(list2.size(), Is.is(
                (int) source.stream()
                        .filter(s -> s != null)
                        .map(Integer::decode).count()));
    }
}