package com.hedwig.algorithm.dsimpl.test;

import static org.junit.Assert.assertTrue;

import java.util.Collection;

import com.hedwig.algorithm.dsimpl.RedBlackTree;
import com.hedwig.algorithm.dsimpl.test.common.JavaCollectionTest;
import com.hedwig.algorithm.dsimpl.test.common.TreeTest;
import com.hedwig.algorithm.dsimpl.test.common.Utils;
import com.hedwig.algorithm.dsimpl.test.common.Utils.Type;
import com.hedwig.algorithm.dsimpl.test.common.Utils.TestData;
import org.junit.Test;


public class RedBlackTreeTests {

    @Test
    public void testRedBlackTree() {
        TestData data = Utils.generateTestData(1000);

        String bstName = "Red-Black Tree";
        RedBlackTree<Integer> bst = new RedBlackTree<Integer>();
        Collection<Integer> bstCollection = bst.toCollection();

        assertTrue(TreeTest.testTree(bst, Type.Integer, bstName,
                data.unsorted, data.invalid));
        assertTrue(JavaCollectionTest.testCollection(bstCollection, Type.Integer, bstName,
                data.unsorted, data.sorted, data.invalid));
    }
}
