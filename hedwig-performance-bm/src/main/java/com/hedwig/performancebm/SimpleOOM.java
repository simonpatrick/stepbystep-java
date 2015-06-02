package com.hedwig.performancebm;

import java.util.ArrayList;

/**
 * Created by patrick on 15/5/26.
 *
 * @version $Id$
 */


public class SimpleOOM {
    /**
     * -Xmx > heap space
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        ArrayList<byte[]> list=new ArrayList<byte[]>();
        for(int i=0;i<102400;i++){
            Thread.sleep(10L);
            list.add(new byte[1024 * 1024]);
        }
    }
}
