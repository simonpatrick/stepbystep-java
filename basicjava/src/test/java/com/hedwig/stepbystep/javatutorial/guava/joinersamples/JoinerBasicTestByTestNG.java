package com.hedwig.stepbystep.javatutorial.guava.joinersamples;

import com.google.common.collect.Maps;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.*;

public class JoinerBasicTestByTestNG {

    private String[] testArray = new String[] {"445","433","332"};
    private List<String> testList= new ArrayList<String>();
    private Map<String,String> map = Maps.newHashMap();
    private String SEPARATOR="-";
    private String EXPECTED_RESULT ="445-433-332";
    private int counter =0;

    @BeforeClass
    public void init(){
        testList.addAll(Arrays.asList(testArray));
        for (String s : testArray) {
            map.put("key"+s,"value"+s);
        }
    }

    @BeforeTest
    public void beforeTest(){
        System.out.println("before test");
    }

    @DataProvider(name = "test1")
    public static Object[][] primeNumbers() {
        return new Object[][] {{2, true}, {3, false}, {2, true}, {22, false}, {23,false}};
    }

    // This test will run 4 times since we have 5 parameters defined
    @Test(dataProvider = "test1")
    public void testPrimeNumberChecker(Integer inputNumber, Boolean expectedResult) {
        System.out.println(inputNumber + " " + expectedResult);
        Assert.assertEquals(expectedResult,Boolean.valueOf(inputNumber.equals(Integer.valueOf(2))));
    }

    @Test(description = "Joiner测试")
    public void testJoin() throws Exception {
        Assert.assertEquals(JoinerBasic.join("-", testArray),EXPECTED_RESULT);
    }

    @Test(description = "Joiner测试_1")
    public void testJoin1() throws Exception {
        if(counter==0){
            counter++;
            Assert.assertTrue(false);
        }
        System.out.println("start retry ......");
        Assert.assertEquals(JoinerBasic.join(SEPARATOR, testList),EXPECTED_RESULT);
    }

    @Test(description = "Joiner测试_2")
    public void testJoinCountNull() throws Exception {
        testList.add(null);
        Assert.assertEquals(JoinerBasic.joinCountNull(SEPARATOR, "none", testList),EXPECTED_RESULT+"-none");
    }

    @Test
    public void testJoin2() throws Exception {
        Assert.assertEquals(JoinerBasic.join("#","=",map),"key433=value433#key445=value445#key332=value332");
    }

    @AfterTest
    public void AfterTest(){
        System.out.println("after test!!!");
    }

    @AfterClass
    public void destroyed(){
        System.out.println("destroy something!!!");
    }

}