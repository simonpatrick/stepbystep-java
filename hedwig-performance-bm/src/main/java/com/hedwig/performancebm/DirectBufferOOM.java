package com.hedwig.performancebm;

import java.nio.ByteBuffer;

/**
 * Created by patrick on 15/5/26.
 *
 * @version $Id$
 */

/**
 * -XX:+PrintGCDetails
 * -XX:MaxDirectMemorySize
 */
public class DirectBufferOOM {
    public static void main(String[] args) {
        for(int i=0;i<1024;i++){
            ByteBuffer.allocateDirect(1024 * 1024);
            System.out.println(i);
            //System.gc();
        }
    }
}
