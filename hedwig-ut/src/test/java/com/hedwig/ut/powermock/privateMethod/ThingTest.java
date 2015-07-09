package com.hedwig.ut.powermock.privateMethod;


import org.junit.Test;
import org.powermock.api.easymock.PowerMock;
import org.powermock.api.support.membermodification.MemberModifier;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.powermock.api.easymock.PowerMock.replayAll;
import static org.powermock.api.easymock.PowerMock.verifyAll;

/**
 * Created by patrick on 15/7/9.
 *
 * @version $Id$
 */
public class ThingTest {

    @Test
    public void testDoStuff() throws Exception {


        MemberModifier.suppress(MemberModifier.constructor(Singleton.class));
        Singleton mockSingleton = PowerMock.createMock(Singleton.class);
        expect(mockSingleton.crazyServerStuff()).andReturn(2);
        replayAll();
        Thing t = new Thing();
        assertThat(t.doStuff(mockSingleton),is(42));
        verifyAll();
    }

    @Test
    public void testDoStuff_noMock(){
        Thing t = new Thing();
        assertThat(t.doStuff(Singleton.getInstance()),is(43));
    }
}