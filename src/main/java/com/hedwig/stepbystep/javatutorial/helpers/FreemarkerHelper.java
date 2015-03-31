package com.hedwig.stepbystep.javatutorial.helpers;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/**
 * Created by patrick on 15/3/13.
 *
 * @version $Id$
 */


public class FreemarkerHelper {
    private static final String SIMPLE_REPORT_TEMPLATE_DIR = "/templates/reports/";
    private static final String SIMPLE_REPORT_TEMPLATE_FILE = "testresult.ftl";
    private static final String SIMPLE_REPORT_DIR = "/simple-report/";
    private static final String SIMPLE_REPORT_FILE = "testresult.html";
    private static String classPath = FreemarkerHelper.class.getClassLoader().getSystemResource("").getPath();
    private String outputPath;

    private FreemarkerHelper() {
    }

    public static void processSimpleReport(Map testResultData) throws IOException, TemplateException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);
        cfg.setDirectoryForTemplateLoading(new File(classPath + SIMPLE_REPORT_TEMPLATE_DIR));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        Writer out = new FileWriter(FileHelper.createFileInClassPath(SIMPLE_REPORT_DIR, SIMPLE_REPORT_FILE));
        Template template = cfg.getTemplate(SIMPLE_REPORT_TEMPLATE_FILE);
        template.process(testResultData, out);
    }
}
