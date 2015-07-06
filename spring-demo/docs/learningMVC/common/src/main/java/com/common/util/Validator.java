package com.springdemo.learningMVC.common.src.main.java.com.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * 常用验证(持续添加)
 *
 */
public class Validator {

    /**
     * 验证邮箱地址格式
     */
    public static boolean isEmail(String str) {
        if (str == null || "".equals(str.trim()))
            return true;
        String regex = "^([\\w-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        return match(regex, str);
    }

    /**
     * 验证URL地址格式
     */
    public static boolean isUrl(String str) {
        String regex = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
        return match(regex, str);
    }

    /**
     * 验证是否IP地址
     */
    public static boolean isIP(String str) {
        String num = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";
        String regex = "^" + num + "\\." + num + "\\." + num + "\\." + num + "$";
        return match(regex, str);
    }

    /**
     * 验证固定电话号码
     */
    public static boolean isTelephone(String str) {
        String regex = "^((\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1})|(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1}))$";
        return match(regex, str);
    }

    /**
     * 验证手机号码
     *
     * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
     * 联通：130、131、132、152、155、156、185、186
     * 电信：133、153、180、189、（1349卫通）
     */
    public static boolean isMobile(String str) {
        String regex = "^((13[0-9])|(15[^4,\\d])|(18[0,5-9]))\\d{8}$";
        return match(regex, str);
    }

    private static boolean match(String regex, String str) {
        if (isNullOrEmpty(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.lookingAt();
    }

    public static void main(String[] args) {

    }
}
