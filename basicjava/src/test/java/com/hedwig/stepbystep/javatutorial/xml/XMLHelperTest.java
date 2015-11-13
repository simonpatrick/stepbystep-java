package com.hedwig.stepbystep.javatutorial.xml;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class XMLHelperTest {

    @Test
    public void testBuild() throws Exception {
       XMLHelper helper = XMLHelper.build("pages/baiduHomePageResource.xml");
       assertNotNull(helper);
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void testBuild_exception() throws Exception {
        XMLHelper helper = XMLHelper.build("pages/aiduHomePageResource.xml");
        assertNotNull(helper);
    }

    @Test
    public void testGetText() throws Exception {
        assertEquals(XMLHelper.build("pages/baiduHomePageResource.xml").getText("keyword"),"id=\"kw\"");
    }

    @Test
    public void testGetAttribute() throws Exception {
        assertEquals(XMLHelper.build("pages/BaiduHomePageResource.xml").getAttribute("keyword","desc"),"keyword");
    }

    @Test
    public void testGetAttributes() throws Exception {
        assertEquals(XMLHelper.build("pages/BaiduHomePageResource.xml").
                getAttributes("keyword").get("desc"),"keyword");
    }

    @Test
    public void testGetElementNameAndTextMap() throws Exception {
        assertEquals(XMLHelper.build("pages/BaiduHomePageResource.xml").getNameAndTextForAllElements().get("keyword"),"id=\"kw\"");
    }

    @Test
    public void testGetAllElementsNameAndText() throws Exception {
        assertEquals(XMLHelper.build("pages/BaiduHomePageResource.xml").
                getNameAndTextForElement("keyword").get("test"),"test");
    }
}