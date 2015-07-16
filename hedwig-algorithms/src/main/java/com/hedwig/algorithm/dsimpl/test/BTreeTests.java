package com.hedwig.algorithm.dsimpl.test;

import static org.junit.Assert.assertTrue;

import java.util.Collection;

import com.hedwig.algorithm.dsimpl.BTree;
import com.hedwig.algorithm.dsimpl.test.common.JavaCollectionTest;
import com.hedwig.algorithm.dsimpl.test.common.TreeTest;
import com.hedwig.algorithm.dsimpl.test.common.Utils;

import org.junit.Test;


public class BTreeTests {

    @Test
    public void testBTree() {
        Utils.TestData data = Utils.generateTestData(1000);

        String bstName = "B-Tree";
        BTree<Integer> bst = new BTree<Integer>(2);
        Collection<Integer> bstCollection = bst.toCollection();

        assertTrue(TreeTest.testTree(bst, Utils.Type.Integer, bstName, data.unsorted, data.invalid));
        assertTrue(JavaCollectionTest.testCollection(bstCollection, Utils.Type.Integer, bstName,
                data.unsorted, data.sorted, data.invalid));
    }
}
