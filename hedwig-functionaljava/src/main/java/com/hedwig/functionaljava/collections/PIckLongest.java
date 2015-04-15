package com.hedwig.functionaljava.collections;

/**
 * Created by patrick on 15/4/11.
 *
 * @version $Id$
 */

import java.util.Optional;

import static com.hedwig.functionaljava.collections.Folks.*;

public class PIckLongest {

    public static void main(String[] args) {
        System.out.println(friends.stream().mapToInt(name->name.length()).sum());
        System.out.println(friends.stream().map(name-> name.toLowerCase()).distinct());
        final Optional<String> aLongName = friends.stream().reduce((name1,name2)->
                name1.length()>=name2.length()?name1:name2);
        aLongName.ifPresent(name-> System.out.println(String.format("longest name: %s",name)));
        final String steveOrLonger = friends.stream().
                reduce("Steve",(name1,name2)->name1.length()>=name2.length()?name1:name2);
        System.out.println(steveOrLonger);
    }
}
