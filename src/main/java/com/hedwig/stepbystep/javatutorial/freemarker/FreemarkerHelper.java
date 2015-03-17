package com.hedwig.stepbystep.javatutorial.freemarker;

import com.google.common.collect.Maps;
import com.hedwig.stepbystep.javatutorial.io.FileHelper;
import com.hedwig.stepbystep.javatutorial.testng.testmodel.TestCase;
import com.hedwig.stepbystep.javatutorial.testng.testmodel.TestSuite;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by patrick on 15/3/13.
 *
 * @version $Id$
 */


public class FreemarkerHelper {
    private static final String SIMPLE_REPORT_TEMPLATE_DIR="/templates/reports/";
    private static final String SIMPLE_REPORT_TEMPLATE_FILE="testresult.ftl";
    private static final String SIMPLE_REPORT_DIR ="target/test-classes/simple-report/";
    private static final String SIMPLE_REPORT_FILE="testresult.html";
    private static String classPath =FreemarkerHelper.class.getClassLoader().getSystemResource("").getPath();
    private String outputPath;

    private FreemarkerHelper(){}

    public static void processSimpleReport(Map testResultData) throws IOException, TemplateException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);
        cfg.setDirectoryForTemplateLoading(new File(classPath+SIMPLE_REPORT_TEMPLATE_DIR));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
//        Map root = new HashMap<>();
//        root.put("testSuiteMap",testResultData);
//        root.put("isFailed",testResultData.get("isFailed"));
//        root.put("total_suite_count",testResultData.get("total_suite_count"));
//        root.put("total_test_case_count",testResultData.get("total_test_case_count"));
//        root.put("total_failed_case_count",testResultData.get("total_failed_case_count"));
//        root.put("total_passed_case_count",testResultData.get("total_passed_case_count"));
        Writer out = new FileWriter(FileHelper.createFile(SIMPLE_REPORT_DIR,SIMPLE_REPORT_FILE));
        Template template = cfg.getTemplate(SIMPLE_REPORT_TEMPLATE_FILE);
        //template.process(root,out);
        template.process(testResultData,out);


    }

    public static void process(String templatesName) throws IOException, TemplateException {
        String classPath = FreemarkerHelper.class.getClassLoader().getResource("").getPath();
        System.out.println(classPath);
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);
        cfg.setDirectoryForTemplateLoading(new File(classPath+"/templates/reports/"));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        Map<String,TestSuite> testSuiteMap = Maps.newHashMap();
        Map root = new HashMap<>();
        TestSuite suite1 = new TestSuite("suite");
        suite1.setPassedSuite(false);
        TestCase testCase = new TestCase();
        testCase.setStatus(1);
        testCase.setTestMethodName("test1");
        testCase.setTestClassName("com.test.test");
        testCase.setTestDescription("test11234");
        suite1.addTestCase(testCase);
        TestCase testCase1 = new TestCase();
        testCase1.setStatus(2);
        testCase1.setTestMethodName("test2");
        testCase1.setTestClassName("com.test.test2");
        testCase1.setTestDescription("test22222");
        RuntimeException exception = new RuntimeException("this is test excepiton");
        testCase1.setErrors(exception);
        testCase1.addFailedScreenshotPath("screenshots/screenshot-1426553167499.jpg", "screenshots/screenshot-1426553166647.jpg");
        suite1.addTestCase(testCase1);
        System.out.println(suite1.isTestSuiteFailed());
        testSuiteMap.put("suite1",suite1);

        System.out.println(suite1);
        Template template = cfg.getTemplate(templatesName);
        root.put("testSuiteMap",testSuiteMap);
        root.put("isFailed",false);
        root.put("total_suite_count",1);
        root.put("total_test_case_count",1);
        root.put("total_failed_case_count",1);
        root.put("total_passed_case_count",1);
        Writer out = new FileWriter(FileHelper.createFile("target/classes/simple-report/","test.html"));
        template.process(root,out);
        System.out.println("for debugging");
        //template.process(root,new PrintWriter(System.out));
    }

    public static void main(String[] args) throws IOException, TemplateException {

       FreemarkerHelper.process("testresult.ftl");
        String path = ClassLoader.getSystemResource("").getPath();
        File file = new File("reports/templates/");
        System.out.println(file.exists());
        System.out.println(file.getPath());
        System.out.println(path);

    }
}
