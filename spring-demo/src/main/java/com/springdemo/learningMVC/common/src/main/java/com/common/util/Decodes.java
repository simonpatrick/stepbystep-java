package com.springdemo.learningMVC.common.src.main.java.com.common.util;

import com.google.common.base.MoreObjects;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import javax.annotation.Nonnull;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Decoding utility class. Provides the {@code URL} decoding, {@code base64} decoding.
 */
public abstract class Decodes {
    private Decodes() { }

    /**
     * Inner invoked.
     */
    protected static String decodeFromBase64(
            byte[] src, Charset charset, Base64.Decoder decoder) {
        if (src == null || src.length == 0) {
            return "";
        }
        Charset c = MoreObjects.firstNonNull(charset, UTF_8);
        try {
            return new String(decoder.decode(src), c);
        } catch (IllegalArgumentException ex) {
            // decode base64 data error
            return null;
        }
    }

    public static String decodeFromBase64Url(byte[] src) {
        return decodeFromBase64Url(src, UTF_8);
    }

    public static String decodeFromBase64Url(byte[] src, Charset charset) {
        return decodeFromBase64(src, charset, Base64.getUrlDecoder());
    }

    /**
     *
     * @param src the base64 format string.
     * @return A newly-allocated string.
     * @see #decodeFromBase64(byte[], java.nio.charset.Charset)
     * @see Encodes#encode2Base64(byte[])
     */
    public static String decodeFromBase64(byte[] src) {
        return decodeFromBase64(src, UTF_8);
    }

    public static String decodeFromBase64(byte[] src, Charset charset) {
        return decodeFromBase64(src, charset, Base64.getDecoder());
    }

    /**
     * Decodes a Base64 encoded url String into a newly-allocated byte array
     * using the {@link java.util.Base64} encoding scheme, and translate it to string
     * using a UTF-8 charset.
     *
     * @param src the base64 format string.
     * @return A newly-allocated string.
     * @see #decodeFromBase64(String, java.nio.charset.Charset)
     */
    public static String decodeFromBase64Url(String src) {
        return decodeFromBase64Url(src, UTF_8);
    }

    /**
     * Decodes a Base64 encoded url String into a newly-allocated byte array
     * using the {@link java.util.Base64} encoding scheme, and translate it to string
     * using a specified charset.
     * <pre>{@code
     * ")(*&"         =&gt;     null (error base64 string)
     * "  \n \r "     =&gt;     null
     * "    "         =&gt;     ""
     * null           =&gt;     ""
     * ""             =&gt;     ""
     * "YT3po57niLc=" =&gt;     "a=飞爷"
     * }</pre>
     *
     * @param src the base64 format string.
     * @param charset the supported charset.
     * @return A newly-allocated string.
     */
    public static String decodeFromBase64Url(String src, Charset charset) {
        if (src == null || (src = src.trim()).length() == 0) {
            return "";
        }
        return decodeFromBase64Url(src.getBytes(charset), charset);
    }

    /**
     * Decodes a Base64 encoded String into a newly-allocated byte array
     * using the {@link java.util.Base64} encoding scheme, and translate it to string
     * using a UTF-8 charset.
     *
     * @param src the base64 format string.
     * @return A newly-allocated string.
     * @see #decodeFromBase64(String, java.nio.charset.Charset)
     * @see Encodes#encode2Base64(String)
     */
    public static String decodeFromBase64(String src) {
        return decodeFromBase64(src, UTF_8);
    }

    /**
     * Decodes a Base64 encoded String into a newly-allocated byte array
     * using the {@link java.util.Base64} encoding scheme, and translate it to string
     * using a specified charset.
     * <pre>{@code
     * ")(*&"         =&gt;     null (error base64 string)
     * "  \n \r "     =&gt;     null
     * "    "         =&gt;     ""
     * null           =&gt;     ""
     * ""             =&gt;     ""
     * "5oiR54ix5L2g" =&gt;     "我爱你"
     * }</pre>
     *
     * @param src the base64 format string.
     * @param charset the supported charset.
     * @return A newly-allocated string.
     */
    public static String decodeFromBase64(String src, Charset charset) {
        if (src == null || (src = src.trim()).length() == 0) {
            return "";
        }
        return decodeFromBase64(src.getBytes(charset), charset);
    }

    public static Map<String, String> decodeUrlParams(Map<String, String> params) {
        return decodeUrlParams(params, UTF_8);
    }

    @Nonnull
    public static Map<String, String> decodeUrlParams(
            Map<String, String> params, Charset charset) {
        if (params == null || params.isEmpty()) {
            return ImmutableMap.of();
        }
        Charset c = MoreObjects.firstNonNull(charset, UTF_8);
        Map<String, String> result = Maps.newHashMapWithExpectedSize(params.size());

        for (Map.Entry<String, String> entry : params.entrySet()) {
            String name = decodeUrl(entry.getKey(), c);
            String value = decodeUrl(entry.getValue(), c);
            if (name != null && value != null) {
                result.put(name, value);
            }
        }
        return result;
    }

    /**
     * Translates a url query string to (name to value) map using UTF-8 scheme.
     *
     * @param qs the url query string.
     * @return the translated map.
     * @see #decodeQueryString(String, java.nio.charset.Charset)
     * @see Encodes#encode2QueryString(java.util.Map)
     */
    public static Map<String, String> decodeQueryString(String qs) {
        return decodeQueryString(qs, UTF_8);
    }

    /**
     * Translates a url query string to (name to value) map using a specific encoding scheme.
     * <pre>{@code
     * String qs1 = "username=%E5%A4%A7%E5%85%89%E5%A4%B4&realname=%E9%83%AD%E5%B0%8F%E5%88%9A";
     * Map<String, String> map = decodeQueryString(qs1, UTF_8);
     * // map -> {username=大光头, realname=郭小刚}
     *
     * String qs2 = "q=%E4%B8%AD%E5%9B%BD%E4%BA%BA&amp;type=1";
     * Map<String, String> map2 = decodeQueryString(qs2, UTF_8);
     * // map2 -> {q=中国人, type=1}
     * }</pre>
     *
     * @param qs the url query string.
     * @param charset the supported charset.
     * @return the translated map.
     * @see Encodes#encode2QueryString(java.util.Map, java.nio.charset.Charset)
     */
    @Nonnull
    public static Map<String, String> decodeQueryString(String qs, Charset charset) {
        if (qs == null || (qs = qs.trim()).length() == 0) {
            return ImmutableMap.of();
        }

        String qs0 = qs.replaceAll("&amp;", "&");

        Map<String, String> name2ValueMap = Splitter.on("&")
                .trimResults().withKeyValueSeparator("=").split(qs0);
        return decodeUrlParams(name2ValueMap, charset);
    }

    /**
     * 使用指定的编码机制对{@code application/x-www-form-urlencoded} 字符串解码。{@code UTF-8} 编码用于确定任何
     * " %xy" 格式的连续序列表示的字符。
     * <p>
     * {@link #decodeUrl(String, java.nio.charset.Charset) decodeUrl(src, StandardCharsets.UTF_8)}
     * 方法的快捷调用。
     *
     * @param src 要解码的 String。
     * @return 新解码的 String。
     * @see #decodeUrl(String, java.nio.charset.Charset)
     * @see Encodes#encodeUrl(String)
     */
    public static String decodeUrl(String src) {
        return decodeUrl(src, UTF_8);
    }

    /**
     * 使用指定的编码机制对{@code application/x-www-form-urlencoded} 字符串解码。给定的编码用于确定任何
     * " %xy" 格式的连续序列表示的字符。
     * <pre>{@code
     * null     =&gt;  null
     * ""       =&gt;  ""
     * "%7sbb9" =&gt;  null   // decode error
     * NOTE:
     * 如果确定{@code src != null}，就可以使用{@code null} 判断转换过程是否出错。
     * }</pre>
     *
     * @param src     要解码的 String。
     * @param charset 所支持的 字符编码。
     * @return 新解码的 String。
     * @throws IllegalArgumentException 如果需要参考字符编码，而指定的字符编码不被支持。
     * @see Encodes#encodeUrl(String, java.nio.charset.Charset)
     */
    public static String decodeUrl(String src, Charset charset) {
        if (src == null || src.length() == 0) {
            return src;
        }
        Charset c = MoreObjects.firstNonNull(charset, UTF_8);
        try {
            return URLDecoder.decode(src, c.displayName());
        } catch (UnsupportedEncodingException ex) {
            throw new IllegalArgumentException(ex);
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }
}
