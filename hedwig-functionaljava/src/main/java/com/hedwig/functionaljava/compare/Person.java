package com.hedwig.functionaljava.compare;

/**
 * Created by patrick on 15/4/15.
 *
 * @version $Id$
 */


public class Person {
    private  String name;
    private  int age;

    public Person(final String theName, final int theAge) {
        name = theName;
        age = theAge;
    }

    public String getName() { return name; }
    public int getAge() { return age; }

    public int ageDifference(final Person other) {
        return age - other.age;
    }

    public String toString() {
        return String.format("%s - %d", name, age);
    }
}
