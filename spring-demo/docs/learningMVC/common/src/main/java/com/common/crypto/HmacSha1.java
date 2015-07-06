/*
 * Copyright (c) 2009-2014. 上海诺诺镑客 All rights reserved.
 * @(#) HmacSha1.java 2014-10-27 16:47
 */

package com.springdemo.learningMVC.common.src.main.java.com.common.crypto;

import com.google.common.util.concurrent.Monitor;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import static com.google.common.base.Preconditions.checkNotNull;

public class HmacSha1 {

    private static final String ALGORITHM = "HmacSHA1";

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 新构建一个新的指定密钥和{@code MAC} 算法实现的提供者名称的 {@link com.springdemo.learningMVC.common.src.main.java.com.common.crypto.HmacSha1}。
     *
     * @param key 密钥。
     * @param provider 提供者名称（maybe null）。
     * @return 一个新的指定密钥和{@code MAC} 算法实现的提供者名称的{@code HmacSha1}。
     * @throws NullPointerException 如果指定的密钥是{@code null}。
     */
    public static HmacSha1 newInstance(String key, String provider) {
        return new HmacSha1(key, provider);
    }

    /**
     * 新构建一个新的指定密钥和默认{@code MAC} 算法实现的 {@link com.springdemo.learningMVC.common.src.main.java.com.common.crypto.HmacSha1}。
     *
     * @param key 密钥。
     * @return 一个新的指定密钥和默认{@code MAC} 算法实现的{@code HmacSha1}。
     * @throws NullPointerException 如果指定的密钥是{@code null}。
     */
    public static HmacSha1 newInstance(String key) {
        return new HmacSha1(key, null);
    }

    private final Monitor MAC_MONITOR = new Monitor();
    private final Mac mac;

    private SecretKeySpec keySpec;

    private HmacSha1(String key, @Nullable String provider) {
        checkNotNull(key, "The given HmacSHA1 key must not be null!");
        keySpec = new SecretKeySpec(key.getBytes(), ALGORITHM);
        this.mac = getAndInitMac(provider);
    }

    private Mac getAndInitMac(@Nullable String provider) {
        String error;
        Mac mac;
        try {
            mac = provider == null ? Mac.getInstance(ALGORITHM) :
                    Mac.getInstance(ALGORITHM, provider);

            mac.init(keySpec);
        } catch (NoSuchAlgorithmException ex) {
            error = String.format("No such algorithm '%s' in current runtime jvm.", ALGORITHM);
            logger.error(error);
            throw new CryptoException(error, ex);
        } catch (NoSuchProviderException ex) {
            error = String.format("No such provider '%s' in current runtime env.", provider);
            logger.error(error);
            throw new CryptoException(error, ex);
        } catch (InvalidKeyException ex) {
            throw new CryptoException(String.format(
                    "Invalid key '%s' for '%s' algorithm", keySpec, ALGORITHM), ex);
        }
        return mac;
    }

    /**
     * 使用新的密钥重新初始化{@code Mac}。
     *
     * @param newKey 新的密钥字符串。
     * @return 返回重新初始化{@code Mac} 后的{@code HmacSHA1}。
     * @throws CryptoException 如果密钥校验错误。
     */
    public HmacSha1 init(String newKey) {
        MAC_MONITOR.enter();
        try {
            keySpec = new SecretKeySpec(newKey.getBytes(), ALGORITHM);

            mac.init(keySpec);
        } catch (InvalidKeyException ex) {
            throw new CryptoException(String.format(
                    "Invalid key '%s' for '%s' algorithm", keySpec, ALGORITHM), ex);
        } finally {
            MAC_MONITOR.leave();
        }
        return this;
    }

    /**
     * 处理给定的{@code byte} 数组并完成{@code MAC} 操作。
     *
     * @param data 字节中的数据。
     * @return MAC 的结果。
     * @throws IllegalStateException 如果此{@code MAC} 还没有初始化。
     */
    public byte[] mac(byte[] data) {
        MAC_MONITOR.enter();
        try {
            return mac.doFinal(data);
        } catch (IllegalStateException ex) {
            logger.error("The current mac have no initialize. Cause: {}", ex.getMessage());
            throw ex;
        } finally {
            MAC_MONITOR.leave();
        }
    }

    public String mac2Hex(byte[] data) {
        byte[] result = mac(data);
        return result != null ? Hex.encodeHexString(data) : null;
    }

//    public String macBase64Encode(byte[] data) {
//        byte[] result = mac(data);
//        return result != null ? UrlEncodes.base64Encode2String(result) : null;
//    }
//
//    public String macBase64Encode(String str) {
//        return macBase64Encode(str, StandardCharsets.UTF_8);
//    }
//
//    public String macBase64Encode(String str, Charset charset) {
//        if (str == null) {
//            return null;
//        }
//        if (charset == null) {
//            charset = StandardCharsets.UTF_8;
//        }
//        return macBase64Encode(str.getBytes(charset));
//    }

    /**
     * 重置当前基于{@code SHA1} 算法的{@code Mac}。
     * <p />
     * 当前的{@code Mac} 被重置，并可通过重新调用{@link #mac(byte[])}（如果需要）从同一个密钥生成另一个{@code MAC}。
     */
    public HmacSha1 reset() {
        MAC_MONITOR.enter();
        try {
            mac.reset();
        } finally {
            MAC_MONITOR.leave();
        }
        return this;
    }
}