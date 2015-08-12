package com.hedwig.performancebm.collectionsBM;

import com.google.common.collect.Maps;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

/**
 * Created by patrick on 15/7/8.
 *
 * @version $Id$
 */

// 仔细研究，小心出错，源码一定要看的
public class ContainsKeyAndGet {

    private Map map = Maps.newHashMap();

    @BeforeClass
    public void setUp() {
        for (int i = 0; i < 1000000; i++) {
            map.put("test" + i, "test" + i);
        }
    }

    @Test(dependsOnMethods = "test_get")
    public void test_containsKey() {

        long start = System.currentTimeMillis();
        for (int j = 10; j > 0; j--) {
            for (long i = 0; i < 1000000000; i++) {
                if (map.containsKey("test")) System.out.println("true");
            }

        }
        long end = System.currentTimeMillis();
        System.out.println((end - start) / 10);

    }

    @Test
    public void test_get() {

        long start = System.currentTimeMillis();
        for (int j = 10; j < 0; j--) {
            for (long i = 0; i < 1000000000; i++) {
                if (map.get("test") != null) System.out.println("true");
            }
        }
        long end = System.currentTimeMillis();
        System.out.println((end - start) / 10);
    }
}
