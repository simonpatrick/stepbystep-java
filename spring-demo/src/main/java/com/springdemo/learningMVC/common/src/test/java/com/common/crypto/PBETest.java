package com.springdemo.learningMVC.common.src.test.java.com.common.crypto;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * {@link PBE} test case.
 *
 * @author Fuchun
 * @version $Id: PBETest.java 290 2014-10-27 08:48:18Z fuchun $
 */
public class PBETest extends CryptoTest {

    @Test
    public void testMD5AndDES() {
        PBE pbe = PBE.md5AndDES("123456789");
        LOGGER.info("========== transformation: {}, password: {}, salt: {} ==========",
                PBE.MD5_AND_DES, pbe.getPassword(), pbe.getHexSalt());
        testGivenSource(pbe);

        PBE pbe1 = PBE.md5AndDES(pbe.getPassword(), pbe.getSalt());
        String encryptText = pbe.encryptString2Hex(UNICODE);
        String encryptText1 = pbe1.encryptString2Hex(UNICODE);
        assertNotNull(encryptText);
        assertNotNull(encryptText1);
        assertEquals(encryptText, encryptText1);
        String decryptText = pbe.decryptHex2String(encryptText);
        String decryptText1 = pbe1.decryptHex2String(encryptText);
        assertEquals(decryptText, decryptText1);
        assertEquals(UNICODE, decryptText);
    }

    @Test
    public void testSHA1AndDESede() {
        PBE pbe = PBE.sha1AndDESede("1234567890abcdef");
        LOGGER.info("========== transformation: {}, password: {}, salt: {} ==========",
                PBE.SHA1_AND_DESEDE, pbe.getPassword(), pbe.getHexSalt());
        testGivenSource(pbe);
    }

    @Test
    public void testSHA1AndRC2_40() {
        PBE pbe = PBE.sha1AndRC2_40("1234567");
        LOGGER.info("========== transformation: {}, password: {}, salt: {} ==========",
                PBE.SHA1_AND_RC2_40, pbe.getPassword(), pbe.getHexSalt());
        testGivenSource(pbe);
    }
}