package com.hedwig.algorithm.dsimpl.test;

import static org.junit.Assert.assertTrue;

import java.util.Collection;

import com.hedwig.algorithm.dsimpl.PatriciaTrie;
import com.hedwig.algorithm.dsimpl.test.common.JavaCollectionTest;
import com.hedwig.algorithm.dsimpl.test.common.TreeTest;
import com.hedwig.algorithm.dsimpl.test.common.Utils;
import com.hedwig.algorithm.dsimpl.test.common.Utils.Type;
import org.junit.Test;


public class PatriciaTreeTests {

    @Test
    public void testPatriciaTrie() {
        Utils.TestData data = Utils.generateTestData(1000);

        String bstName = "PatriciaTrie";
        PatriciaTrie<String> bst = new PatriciaTrie<String>();
        Collection<String> bstCollection = bst.toCollection();

        assertTrue(TreeTest.testTree(bst, Type.String, bstName,
                data.unsorted, data.invalid));
        assertTrue(JavaCollectionTest.testCollection(bstCollection, Type.String, bstName,
                data.unsorted, data.sorted, data.invalid));
    }
}
