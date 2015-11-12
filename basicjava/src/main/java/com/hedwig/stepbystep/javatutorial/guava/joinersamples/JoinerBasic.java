package com.hedwig.stepbystep.javatutorial.guava.joinersamples;

import com.google.common.base.Joiner;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by patrick on 15/3/5.
 *
 * @version $Id$
 */


public class JoinerBasic {

    public static String join(String separator,Object[] objects){
        return Joiner.on(separator).skipNulls().join(objects);
    }

    public static String join(String separator,Iterable<?> objects){
       return Joiner.on(separator).skipNulls().join(objects);
    }

    public static String joinCountNull(String separator,String userForNull,Iterable<?> objects){
        return Joiner.on(separator).useForNull(userForNull).join(objects);
    }

    public static String join(String separator,String keyValueSeparator,Map<?,?> map){
        return Joiner.on(separator).withKeyValueSeparator(keyValueSeparator)
                .join(map);
    }
}
