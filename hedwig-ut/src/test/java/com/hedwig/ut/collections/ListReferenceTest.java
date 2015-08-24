package com.hedwig.ut.collections;

import com.google.common.collect.Lists;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Created by patrick on 15/8/24.
 */
public class ListReferenceTest {

    public static class Node{
        String id;
        String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public static Node create(){
            return new Node();
        }
    }

    @Test
    public void test_reference(){
        final List<Node> nodes = Lists.newArrayList();
        Node node ;
        for (int i = 0; i <10 ; i++) {
            node=Node.create();
            node.setId(String.valueOf(i));
            node.setName(String.valueOf(i));
            nodes.add(node);
        }

        System.out.println(nodes);

//        Node node1 = new Node();
//        for (int i = 0; i <10 ; i++) {
//            node.setId(String.valueOf(i));
//            node.setName(String.valueOf(i));
//            nodes.add(node);
//        }
    }
}
