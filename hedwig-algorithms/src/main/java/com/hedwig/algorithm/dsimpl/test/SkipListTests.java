package com.hedwig.algorithm.dsimpl.test;

import static org.junit.Assert.assertTrue;

import java.util.Collection;

import com.hedwig.algorithm.dsimpl.SkipList;
import com.hedwig.algorithm.dsimpl.test.common.JavaCollectionTest;
import com.hedwig.algorithm.dsimpl.test.common.SetTest;
import com.hedwig.algorithm.dsimpl.test.common.Utils;
import org.junit.Test;


public class SkipListTests {

    @Test
    public void testSkipList() {
        Utils.TestData data = Utils.generateTestData(1000);

        String sName = "SkipList";
        SkipList<Integer> sList = new SkipList<Integer>();
        Collection<Integer> lCollection = sList.toCollection();

        assertTrue(SetTest.testSet(sList, sName,
                data.unsorted, data.invalid));
        assertTrue(JavaCollectionTest.testCollection(lCollection, Utils.Type.Integer, sName,
                data.unsorted, data.sorted, data.invalid));
    }
}
