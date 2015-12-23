package com.hedwig.interviews.strings;

/**
 * Created by patrick on 15/10/22.
 */
public class ReverseWords {

    public static String reverseWords(String input){
        String[] words = input.split(" ");
        StringBuilder sb = new StringBuilder();
        for (int i = words.length-1; i >=0 ; i--) {
            sb.append(words[i]);
            sb.append(" ");
        }

        return sb.toString().trim();
    }

    public static void main(String[] args) {
        System.out.println(reverseWords("Hello World!"));
    }
}
