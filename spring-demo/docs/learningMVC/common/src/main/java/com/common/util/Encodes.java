package com.springdemo.learningMVC.common.src.main.java.com.common.util;

import com.google.common.base.Joiner;
import com.google.common.base.MoreObjects;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import javax.annotation.Nonnull;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Encoding utility class. Provides the {@code URL} encoding, {@code base64} encoding.
 */
public abstract class Encodes {
    private Encodes() {
    }

    /**
     * Inner invoked.
     */
    protected static String encode2Base64(
            byte[] src, Charset charset, Base64.Encoder encoder) {
        if (src == null || src.length == 0) {
            return "";
        }
        Charset c = MoreObjects.firstNonNull(charset, UTF_8);
        try {
            return new String(encoder.encode(src), c);
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    /**
     * 使用指定的编码机制将指定的数据转换为{@code base64} 格式，再使用指定的字符编码
     * 转换{@code base64} 格式数据为字符串。
     * <p>
     * 当{@code src == null || src.length == 0} 时，该方法返回空字符串。
     *
     * @param src     要编码的数据。
     * @return 转换后的 base64 字符串。
     * @see Decodes#decodeFromBase64(byte[])
     * @see #encode2Base64(byte[], java.nio.charset.Charset)
     */
    public static String encode2Base64(byte[] src) {
        return encode2Base64(src, UTF_8);
    }

    /**
     * 使用指定的编码机制将指定的数据转换为{@code base64} 格式，再使用指定的字符编码
     * 转换{@code base64} 格式数据为字符串。
     * <p>
     * 当{@code src == null || src.length == 0} 时，该方法返回空字符串。
     *
     * @param src     要编码的数据。
     * @param charset 指定的字符串编码。
     * @return 转换后的 base64 字符串。
     * @see Decodes#decodeFromBase64(byte[], java.nio.charset.Charset)
     */
    public static String encode2Base64(byte[] src, Charset charset) {
        return encode2Base64(src, charset, Base64.getEncoder());
    }

    /**
     * 使用指定的编码机制将指定的字符串转换为{@code base64} 格式数据，再
     * 转换{@code base64} 格式数据为UTF-8编码的字符串。
     * <pre>{@code
     * null       =&gt;     ""
     * ""         =&gt;     ""
     * "    "     =&gt;     "ICAgIA=="
     * "中国人"    =&gt;     "5Lit5Zu95Lq6"
     * "/中国人/1" =&gt;     "L+S4reWbveS6ui8x"
     * }</pre>
     *
     * @param src 要编码的数符串。
     * @return 转换后的 base64 字符串。
     * @see Decodes#decodeFromBase64(String)
     * @see #encode2Base64(String, java.nio.charset.Charset)
     */
    public static String encode2Base64(String src) {
        return encode2Base64(src, UTF_8);
    }

    /**
     * 使用指定的编码机制将指定的字符串转换为{@code base64} 格式数据，再使用指定的字符编码
     * 转换{@code base64} 格式数据为字符串。
     * <pre>{@code
     * charset = Charset.forName("GBK"):
     * null       =&gt;     ""
     * ""         =&gt;     ""
     * "    "     =&gt;     "ICAgIA==" // 半角字符编码与UTF-8一致
     * "中国人"    =&gt;     "1tC5+sjL"
     * }</pre>
     *
     * @param src     要编码的数符串。
     * @param charset 指定的字符串编码。
     * @return 转换后的 base64 字符串。
     * @see Decodes#decodeFromBase64(String, java.nio.charset.Charset)
     */
    public static String encode2Base64(String src, Charset charset) {
        if (src == null || src.length() == 0) {
            return "";
        }
        return encode2Base64(src.getBytes(charset), charset);
    }

    /**
     * 使用指定的编码机制将指定的数据转换为{@code base64} 格式数据，再使用{@code UTF-8} 字符编码
     * 转换{@code base64} 格式数据为{@code URLSafe} 字符串。
     *
     * @param src     要编码的数符串。
     * @return 转换后的 base64 {@code URLSafe}字符串。
     * @see Decodes#decodeFromBase64Url(byte[])
     * @see #encode2Base64Url(byte[], java.nio.charset.Charset)
     */
    public static String encode2Base64Url(byte[] src) {
        return encode2Base64Url(src, UTF_8);
    }

    /**
     * 使用指定的编码机制将指定的数据转换为{@code base64} 格式数据，再使用指定的字符编码
     * 转换{@code base64} 格式数据为{@code URLSafe} 字符串。
     *
     * @param src     要编码的数符串。
     * @param charset 指定的字符串编码。
     * @return 转换后的 base64 {@code URLSafe}字符串。
     * @see Decodes#decodeFromBase64Url(String, java.nio.charset.Charset)
     */
    public static String encode2Base64Url(byte[] src, Charset charset) {
        return encode2Base64(src, charset, Base64.getUrlEncoder());
    }

    /**
     * 使用指定的编码机制将指定的字符串转换为{@code base64} 格式数据，再使用{@code UTF-8} 字符编码
     * 转换{@code base64} 格式数据为{@code URLSafe} 字符串。
     * <p>
     * 该方法遵循{@code RFC 4686} 标准：转换后的{@code base64} 字符串中的'+'和'/'，被替换为'-'和'_'。
     * <pre>{@code
     * null           =&gt;   ""
     * ""             =&gt;   ""
     * "    "         =&gt;   "ICAgIA=="
     * // normal and url-safe
     * encode2Base64("/中国人/1")    =&gt; "L+S4reWbveS6ui8x"
     * encode2Base64Url("/中国人/1") =&gt; "L-S4reWbveS6ui8x"
     * }</pre>
     *
     * @param src 要编码的数符串。
     * @return 转换后的 base64 {@code URLSafe}字符串。
     * @see Decodes#decodeFromBase64Url(String)
     * @see #encode2Base64Url(String, java.nio.charset.Charset)
     */
    public static String encode2Base64Url(String src) {
        return encode2Base64Url(src.getBytes(UTF_8), UTF_8);
    }

    /**
     * 使用指定的编码机制将指定的字符串转换为{@code base64} 格式数据，再使用指定的字符编码
     * 转换{@code base64} 格式数据为{@code URLSafe} 字符串。
     * <p>
     * 该方法遵循{@code RFC 4686} 标准：转换后的{@code base64} 字符串中的'+'和'/'，被替换为'-'和'_'。
     * <pre>{@code
     * null           =&gt;   ""
     * ""             =&gt;   ""
     * "    "         =&gt;   "ICAgIA=="
     * // normal and url-safe
     * Charset c = Charset.forName("GBK");
     * encode2Base64("/中國人/a", c)    =&gt; "L9bQh/jIyy9h"
     * encode2Base64Url("/中國人/a", c) =&gt; "L9bQh_jIyy9h"
     * }</pre>
     *
     * @param src     要编码的数符串。
     * @param charset 字符串编码。
     * @return 转换后的 base64 {@code URLSafe}字符串。
     * @see Decodes#decodeFromBase64Url(String, java.nio.charset.Charset)
     */
    public static String encode2Base64Url(String src, Charset charset) {
        if (src == null || src.length() == 0) {
            return "";
        }
        return encode2Base64Url(src.getBytes(charset), charset);
    }

    /**
     * Translates a Map&lt;String, String&gt; into
     * {@code application/x-www-form-urlencoded} format using UTF-8 scheme.
     *
     * @param src the map to encode.
     * @return the translated {@code String}.
     * @see #encode2QueryString(java.util.Map, java.nio.charset.Charset)
     * @see Decodes#decodeQueryString(String)
     */
    public static String encode2QueryString(Map<String, String> src) {
        return encode2QueryString(src, UTF_8);
    }

    /**
     * Translates a Map&lt;String, String&gt; into
     * {@code application/x-www-form-urlencoded} format using a specific encoding scheme.
     * <pre>
     * Map&lt;String, String&gt; params = new HashMap&lt;&gt;();
     * params.put("name", "名字");
     * params.put("gender", "男");
     * encode2QueryString(params, StandardCharsets.UTF_8);
     * // =&gt; "name=%E5%90%8D%E5%AD%97&gender=%E7%94%B7"
     * </pre>
     *
     * @param src     the map to encode.
     * @param charset the supported charset.
     * @return the translated {@code String}.
     * @see Decodes#decodeQueryString(String, java.nio.charset.Charset)
     */
    public static String encode2QueryString(Map<String, String> src, Charset charset) {
        Map<String, String> encodedMap = encodeUrlParams(src, charset);
        return encodedMap.isEmpty() ? null :
                Joiner.on("&").withKeyValueSeparator("=").join(encodedMap);
    }

    /**
     * Translates a Map&lt;String, String&gt; into
     * {@code application/x-www-form-urlencoded} format using a UTF-8 scheme.
     * <p>
     * {@link #encodeUrlParams(java.util.Map, java.nio.charset.Charset) encodeParamsMap(map, UTF_8)} 的快捷调用。
     *
     * @param src the map to encode.
     * @return the translated map.
     * @see #encodeUrlParams(java.util.Map, java.nio.charset.Charset)
     * @see Decodes#decodeUrlParams(java.util.Map)
     */
    @Nonnull
    public static Map<String, String> encodeUrlParams(Map<String, String> src) {
        return encodeUrlParams(src, UTF_8);
    }

    /**
     * Translates a Map&lt;String, String&gt; into
     * {@code application/x-www-form-urlencoded} format using a specific encoding scheme.
     * <pre>
     * Map&lt;String, String&gt; params = new HashMap&lt;&gt;();
     * params.put("name", "名字");
     * params.put("gender", "男");
     * encodeUrlParams(params, StandardCharsets.UTF_8);
     * // ==&gt; {"name":"%E5%90%8D%E5%AD%97","gender":"%E7%94%B7"}
     * </pre>
     *
     * @param src     the map to encode.
     * @param charset the supported charset.
     * @return the translated map.
     * @see Decodes#decodeUrlParams(java.util.Map, java.nio.charset.Charset)
     */
    public static Map<String, String> encodeUrlParams(
            Map<String, String> src, Charset charset) {
        if (src == null || src.isEmpty()) {
            return ImmutableMap.of();
        }
        Map<String, String> result = Maps.newLinkedHashMap();
        for (Map.Entry<String, String> entry : src.entrySet()) {
            String encodedName = encodeUrl(entry.getKey(), charset);
            String encodedValue = encodeUrl(entry.getValue(), charset);
            if (encodedValue != null) {
                result.put(encodedName, encodedValue);
            }
        }
        return result;
    }

    /**
     * 使用指定的编码机制将查询字符串转换为{@code application/x-www-form-urlencoded} 格式。
     * 该方法使用提供的编码机制获取不安全字符的字节。默认使用{@code UTF-8} 字符编码。
     *
     * @param qs 请求的查询字符串。
     * @return 转换好的字符串。
     * @see #encodeQueryString(String, java.nio.charset.Charset)
     */
    public static String encodeQueryString(String qs) {
        return encodeQueryString(qs, UTF_8);
    }

    /**
     * 使用指定的编码机制将查询字符串转换为{@code application/x-www-form-urlencoded} 格式。
     * 该方法使用提供的编码机制获取不安全字符的字节。
     * <pre>{@code
     * 1. "&amp;amp;" 转换为 "&"
     * 2. 对每个键值对的值进行URL编码
     * // 见下例：
     * String s = encodeQueryString("unit=%&amp;value=5/38", UTF_8);
     * // "unit=%25&value=5%2F38"
     * }</pre>
     *
     * @param qs      请求的查询字符串。
     * @param charset 指定的字符编码。
     * @return 转换好的字符串。
     */
    public static String encodeQueryString(String qs, Charset charset) {
        if (qs == null || qs.length() == 0) {
            return qs;
        }
        final String qs0 = qs.replaceAll("&amp;", "&");
        Map<String, String> params = Splitter.on("&").trimResults()
                .withKeyValueSeparator("=").split(qs0);
        return encode2QueryString(params, charset);
    }

    /**
     * 使用指定的编码机制将字符串转换为{@code application/x-www-form-urlencoded} 格式。该方法使用提供的编码机
     * 制获取不安全字符的字节。默认使用{@code UTF-8} 编码。
     * <pre>{@code
     * null        =&gt;  null
     * ""          =&gt;  ""
     * "    "      =&gt;  "++++"
     * "  \n\r"    =&gt;  "++%0A%0D"
     * "中国人"     =&gt;  "%E4%B8%AD%E5%9B%BD%E4%BA%BA"
     * }</pre>
     *
     * @param src 要转换的 String。
     * @return 转换好的 String。
     * @see Decodes#decodeUrl(String)
     * @see #encodeUrl(String, java.nio.charset.Charset)
     */
    public static String encodeUrl(String src) {
        return encodeUrl(src, UTF_8);
    }

    /**
     * 使用指定的编码机制将字符串转换为{@code application/x-www-form-urlencoded} 格式。该方法使用提供的编码机
     * 制获取不安全字符的字节。
     * <pre>{@code
     * null        =&gt;  null
     * ""          =&gt;  ""
     * "    "      =&gt;  "++++"
     * "  \n\r"    =&gt;  "++%0A%0D"
     * encodeUrl("中国人", Charset.forName("GBK")) = "%D6%D0%B9%FA%C8%CB"
     * encodeUrl("中国人", Charset.forName("UTF-8")= "%E4%B8%AD%E5%9B%BD%E4%BA%BA"
     * }</pre>
     *
     * @param src     要转换的 String。
     * @param charset 所支持的 字符编码。
     * @return 已转换的 String。
     * @throws IllegalArgumentException 如果不支持指定的字符编码。
     * @see Decodes#decodeUrl(String, java.nio.charset.Charset)
     */
    public static String encodeUrl(String src, Charset charset) {
        if (src == null || src.length() == 0) {
            return src;
        }
        Charset c = MoreObjects.firstNonNull(charset, UTF_8);
        try {
            return URLEncoder.encode(src, c.displayName());
        } catch (UnsupportedEncodingException ex) {
            throw new IllegalArgumentException(ex);
        }
    }
}
