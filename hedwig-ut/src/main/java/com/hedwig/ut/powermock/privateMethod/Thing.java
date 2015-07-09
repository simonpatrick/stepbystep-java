package com.hedwig.ut.powermock.privateMethod;

public class Thing {
    public Thing() {}

    public int doStuff(Singleton s) {
        return s.crazyServerStuff() + 42;
    }
}