package com.hedwig.stepbystep.javatutorial.helpers;

import com.alibaba.fastjson.JSON;

/**
 * Created by patrick on 15/3/13.
 *
 * @version $Id$
 */


public class JSONHelper {

    private JSONHelper() {
    }

    public static <T> T toBean(String jsonString, Class<T> clazz) {

        return JSON.parseObject(jsonString, clazz);
    }

    //to xpath 查询
}
