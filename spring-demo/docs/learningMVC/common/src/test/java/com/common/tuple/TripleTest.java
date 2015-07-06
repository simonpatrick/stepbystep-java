package com.springdemo.learningMVC.common.src.test.java.com.common.tuple;

import com.fasterxml.jackson.core.type.TypeReference;
import com.common.json.JsonMapper;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsInstanceOf;
import org.hamcrest.core.IsNull;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * {@link Triple} test case.
 *
 * @author fuchun
 */
public class TripleTest {

    @Test
    public void testJsonProperty() throws Exception {
        Triple<Short, Integer, Long> nt =
                Triple.of((short) 1, 2, 3L);
        String numTripleJson = JsonMapper.getDefault().toJSONString(nt);
        String targetJson = "{\"l\":1,\"m\":2,\"r\":3}";

        assertNotNull(numTripleJson);
        assertThat(numTripleJson, Is.is(targetJson));

        Triple<Short, Integer, Long> nt2 =
                JsonMapper.getDefault().readValue(targetJson,
                        new TypeReference<Triple<Short, Integer, Long>>(){}.getType());

        assertThat(nt2, IsNull.notNullValue());
        assertThat(nt2, IsEqual.equalTo(nt));

        assertThat(nt2, IsInstanceOf.instanceOf(ImmutableTriple.class));

        assertThat(nt.toString(), Is.is(String.format(
                "(%s,%s,%s)", nt.getLeft(), nt.getMiddle(), nt.getRight())));
    }
}