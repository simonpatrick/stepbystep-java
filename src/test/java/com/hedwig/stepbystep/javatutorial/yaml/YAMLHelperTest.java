package com.hedwig.stepbystep.javatutorial.yaml;

import com.google.common.collect.Maps;
import com.hedwig.stepbystep.javatutorial.webtest.pageobjects.ElementDescription;
import org.dom4j.Element;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.*;

public class YAMLHelperTest {

    @Test
    public void testParse() throws Exception {
        Map<String,Map<String,String>> map = Maps.newHashMap();
            map= (Map<String,Map<String,String>>)YAMLHelper.parse("pages/baiduhomepage.yaml", HashMap.class);
        assertNotNull(map);
    }

    @Test
    public void testParse_AsMap() throws Exception {
        Map map=YAMLHelper.parseAsMap("pages/baiduhomepage3.yaml");
        assertNotNull(map);
    }

    @Test
    public void testParse_AsClassMap() throws Exception {
        Map map=YAMLHelper.parseAsClassMap("pages/baiduhomepage3.yaml", ElementDescription.class);
        assertNotNull(map);
    }

    @Test
    public void testParse_ElementDescription() throws Exception {
        ElementDescription description= YAMLHelper.parse("pages/baiduhomepage2.yaml", ElementDescription.class);
        System.out.println(description);
        assertNotNull(description);
    }


    @Test
    public void testParseAsList() throws Exception {
        List<ElementDescription> description= YAMLHelper.parseAsList("pages/baiduhomepage2.yaml", ElementDescription.class);
        System.out.println(description);
        assertNotNull(description);
    }
}