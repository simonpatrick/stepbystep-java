package com.hedwig.functionaljava.collections;

/**
 * Created by patrick on 15/4/11.
 *
 * @version $Id$
 */

import static com.hedwig.functionaljava.collections.Folks.*;

public class Iterations {

    public static void main(String[] args) {
        for (int i = 0; i <friends.size() ; i++) {
            System.out.println(Folks.friends.get(i));
        }

        for (String friend : friends) {
            System.out.println(friend);
        }

        System.out.println("//"+"START:INTERNAL_FOR_EACH_OUTPUT");
        friends.forEach((final String name)-> System.out.println(name));

        friends.forEach(name-> System.out.println(name));

        friends.forEach(System.out::println);
    }
}
