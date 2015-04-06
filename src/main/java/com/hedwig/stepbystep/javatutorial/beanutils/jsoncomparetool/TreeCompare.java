package com.hedwig.stepbystep.javatutorial.beanutils.jsoncomparetool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TreeCompare {

    private List<AbstractAPIResponseFilter> filters = new ArrayList<AbstractAPIResponseFilter>();

    private static final Comparator<TreeNode> NODE_NAME_COMPARATOR = new Comparator<TreeNode>() {

        @Override
        public int compare(TreeNode o1, TreeNode o2) {
            String name1 = o1 != null ? o1.getName() : null;
            String name2 = o2 != null ? o2.getName() : null;
            if (name1 != null) {
                return name1.compareTo(name2);
            } else if (name2 != null) {
                return name2.compareTo(name1);
            } else {
                return 0;
            }
        }
    };

    public void addFilter(AbstractAPIResponseFilter filter){
        filters.add(filter);
    }

    public static String readFile(String fileName) throws IOException {

        String filePath = ClassLoader.getSystemClassLoader().getResource(fileName).getPath();
        File file = new File(filePath);
        return readFile(file);
    }

    public static String readFile(File name) throws IOException {
        StringBuilder builder = new StringBuilder();
        String line = null;
        BufferedReader reader = new BufferedReader(new FileReader(name));
        try {
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } finally {
            reader.close();
        }
        return builder.toString();
    }

    public  boolean  compare(TreeNode template, TreeNode actual, boolean isStructOnly) {

        for (AbstractAPIResponseFilter filter : this.filters) {
            filter.apply(template,actual);
        }
        boolean isStructEqual = compareName(template.getName(), actual.getName())
                && compareChildren(template.getChildren(), actual.getChildren(), isStructOnly);
        //Logging.info("API Response Structure match is "+isStructEqual);
        if (!isStructOnly) {
            return isStructEqual && compareType(template.getValueType(), actual.getValueType())
                    && compareValue(template.getValue(), actual.getValue());
        }
        return isStructEqual;
    }

    private static boolean compareType(String a, String b) {
        return compareObject(a, b);
    }

    private static boolean compareName(String a, String b) {
        return a.equals(b);
    }

    private static boolean compareValue(String template, String actual) {
        if (template == null && actual == null) {
            return true;
        }
        if ((template == null && actual != null) || (template != null && actual == null)) {
            return false;
        }
        if (template.startsWith("$")) {
            return matchPattern(template, actual);
        } else {
            return template.equals(actual);
        }
    }

    private static boolean matchPattern(String template, String actual) {
        String variable = template.substring(1);
        if ("boolean".equals(variable)) {
            return "true".equalsIgnoreCase(actual) || "false".equalsIgnoreCase(actual);
        } else if ("number".equals(variable)) {
            try {
                Float.parseFloat(actual);
                return true;
            } catch (NumberFormatException e) {
                System.out.println(template+":"+actual);
                return false;
            }
        } else if ("any".equals(variable)) {
            return true;
        } else if (variable.startsWith("date:")) {
            SimpleDateFormat format = new SimpleDateFormat(variable.substring(5, variable.length()));
            try {
                format.parse(actual);
                return true;
            } catch (ParseException e) {
                System.out.println(template+":"+actual);
                return false;
            }
        }
        return template.equals(actual);
    }

    private static boolean compareObject(Object a, Object b) {
        if (a == null && b == null) {
            return true;
        }
        if ((a == null && b != null) || (a != null && b == null)) {
            return false;
        }
        return a.equals(b);
    }

    private boolean compareChildren(List<TreeNode> a, List<TreeNode> b, boolean isStructOnly) {
        Collections.sort(a, NODE_NAME_COMPARATOR);
        Collections.sort(b, NODE_NAME_COMPARATOR);
        if (a == null && b == null) {
            return true;
        }
        if ((a == null && b != null) || (a != null && b == null)) {
            return false;
        }

        if (a.size() != b.size()) {
            System.out.println(a.size());
            System.out.println(b.size());
            return false;
        }

        for (int i = 0; i < a.size(); ++i) {
            if (!compare(a.get(i), b.get(i), isStructOnly)) {
                System.out.println(a.get(i).getName());
                System.out.println(b.get(i).getName());
                return false;
            }
        }
        return true;
    }

    public boolean compareStructureOnly(String templateName,String jsonResponse) throws IOException {

        return compare(JsonNode.getRoot(readFile(templateName)),JsonNode.getRoot(jsonResponse),true);

    }

    public static void main(String[] args) throws Exception {
        TreeCompare compare = new TreeCompare();
        compare.addFilter(new APIResponseRepeatableFilter("listing"));
       // compare.addFilter(new APIResponseIgnorableFilter("splitVector"));
        //compare.addFilter(new APIResponseIgnorableFilter("listingAttributeList"));
        boolean result =compare.compare(JsonNode.getRoot(readFile("a.json")),
                JsonNode.getRoot(readFile("d.json")),true);
        System.out.println(result);
    }
}
