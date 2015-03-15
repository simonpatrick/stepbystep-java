package com.hedwig.stepbystep.javatutorial.freemarker;

import com.google.common.collect.Maps;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.*;
import java.util.Map;

/**
 * Created by patrick on 15/3/13.
 *
 * @version $Id$
 */


public class FreemarkerHelper {

    public static void main(String[] args) throws IOException, TemplateException {

        String classPath = FreemarkerHelper.class.getClassLoader().getResource("").getPath();
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);
        cfg.setDirectoryForTemplateLoading(new File(classPath+"/reports/templates/"));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        Map map = Maps.newHashMap();
        map.put("SuiteName","MyFirstTest");
        Template template = cfg.getTemplate("test.ftl");
        Writer out = new FileWriter(new File(classPath+"test.html"));
        template.process(map,out);

    }
}
