package com.hedwig.functionaljava.collections;

/**
 * Created by patrick on 15/4/11.
 *
 * @version $Id$
 */


public class Java8ForEach {

    public static void main(String[] args) {
        Folks.friends.forEach(name->{
            name = name.toLowerCase();
            System.out.println(name);
        });
    }
}
