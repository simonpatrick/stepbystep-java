package com.hedwig.interviews.strings;

/**
 * Created by patrick on 15/10/22.
 */
public class Fibinaccis {

    public static String getFibListByNum(int n){
        if (n<=0) throw new RuntimeException("invalid input");
        if(n==1) return "1";
        if(n==2) return "1,1";
        int temp1=1;
        int temp2=1;
        String result = "1,1";
        for (int i = 3; i <=n ; i++) {
            int appendNum = temp1+temp2;
            temp1=temp2;
            temp2=appendNum;
            result=result+","+appendNum;
        }

        return result;
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        System.out.println(fibinacciR(40));
        System.out.println(System.currentTimeMillis()-start/1000);
        System.out.println(fibinacci(40));
        //System.out.println(getFibListByNum(100));
    }

    public static Long fibinacciR(int number){
        if(number<=0) return 0L;
        if(number==1) return 1L;
        if(number==2) return 1L;

        return fibinacciR(number - 1)+fibinacciR(number - 2);
    }

    public static long fibinacci(int number){
        long start = System.currentTimeMillis();
        if(number<=0) return 0L;
        if(number==1) return 1L;
        if(number==2) return 1L;
        long num1 =1;
        long num2=1;
        long result = 0;
        for (int i = 3; i <=number; i++) {
            result= num1+num2;
            num1=num2;
            num2=result;
        }
        System.out.println(System.currentTimeMillis()-start/1000);
        return result;
    }
}
