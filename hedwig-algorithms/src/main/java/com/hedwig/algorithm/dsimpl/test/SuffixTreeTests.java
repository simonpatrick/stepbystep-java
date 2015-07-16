package com.hedwig.algorithm.dsimpl.test;

import static org.junit.Assert.assertTrue;

import com.hedwig.algorithm.dsimpl.SuffixTree;
import com.hedwig.algorithm.dsimpl.test.common.SuffixTreeTest;
import org.junit.Test;


public class SuffixTreeTests {

    @Test
    public void testSuffixTree() {
        String bookkeeper = "bookkeeper";
        SuffixTree<String> tree = new SuffixTree<String>(bookkeeper);
        assertTrue(SuffixTreeTest.suffixTreeTest(tree, bookkeeper));
    }
}
