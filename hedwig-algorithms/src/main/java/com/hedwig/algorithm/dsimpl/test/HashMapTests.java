package com.hedwig.algorithm.dsimpl.test;

import static org.junit.Assert.assertTrue;

import com.hedwig.algorithm.dsimpl.HashMap;
import com.hedwig.algorithm.dsimpl.test.common.JavaMapTest;
import com.hedwig.algorithm.dsimpl.test.common.MapTest;
import org.junit.Test;

import com.hedwig.algorithm.dsimpl.test.common.Utils;
import com.hedwig.algorithm.dsimpl.test.common.Utils.Type;
public class HashMapTests {

    @Test
    public void testHashMap() {
        Utils.TestData data = Utils.generateTestData(1000);

        String mapName = "HashMap";
        HashMap<Integer,String> map = new HashMap<Integer,String>();
        java.util.Map<Integer,String> jMap = map.toMap();

        assertTrue(MapTest.testMap(map, Type.Integer, mapName,
                data.unsorted, data.invalid));
        assertTrue(JavaMapTest.testJavaMap(jMap, Type.Integer, mapName,
                data.unsorted, data.sorted, data.invalid));
    }
}
