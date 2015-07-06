package com.springdemo.learningMVC.common.src.test.java.com.common.crypto;

import com.common.base.RandomStrings;
import com.common.json.JsonMapper;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Test;

import java.security.Security;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;


public class HmacSha1Test {

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    @Test
    public void testNewInstance() throws Exception {
        String key = RandomStrings.randomAlphanumeric(40);
        HmacSha1 hmacSha1;
        try {
            hmacSha1 = HmacSha1.newInstance(key);
        } catch (Exception ex) {
            // won't happen
            hmacSha1 = null;
        }
        assertThat(hmacSha1, notNullValue());

        try {
            hmacSha1 = HmacSha1.newInstance(key, "BC");
        } catch (Exception ex) {
            // no provider "BC"
            hmacSha1 = null;
        }
        assertThat(hmacSha1, notNullValue());

        try {
            hmacSha1 = HmacSha1.newInstance(null);
        } catch (Exception ex) {
            assertThat(ex, IsInstanceOf.instanceOf(NullPointerException.class));
            hmacSha1 = null;
        }

        assertThat(hmacSha1, nullValue());
    }

    @Test
    public void testMac() throws Exception {
        String key = RandomStrings.randomAlphanumeric(40);
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("scope", "test:data.dat");
        String dataJson = JsonMapper.getDefault().toJSONString(dataMap);
        HmacSha1 hmacSha1 = HmacSha1.newInstance(key);
        String digest = Base64.getEncoder().encodeToString(dataJson.getBytes());
//        String digest = hmacSha1.macBase64Encode(dataJson);
//        String digestHex = hmacSha1.mac2Hex(dataJson.getBytes());
        String digestHex = hmacSha1.mac2Hex(dataJson.getBytes());

        assertThat(digest, notNullValue());
        assertThat(digestHex, notNullValue());
        System.out.println(String.format("source: %s\ndigest: %s", dataJson, digest));
        System.out.println(String.format("source: %s\ndigestHex: %s", dataJson, digestHex));
    }
}