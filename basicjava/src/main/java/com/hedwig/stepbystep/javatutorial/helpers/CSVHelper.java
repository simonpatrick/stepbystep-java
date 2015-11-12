package com.hedwig.stepbystep.javatutorial.helpers;

import au.com.bytecode.opencsv.CSVReader;
import org.apache.commons.beanutils.BeanUtils;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * 将CSV文件内容复制到的java bean 类
 */
public class CSVHelper {

    private CSVHelper() {
    }

    private static Object[] convertToObjectArray(String[] header, String[] row, Map<String, Class> clazzMap) throws IllegalAccessException, InstantiationException, InvocationTargetException {

        if (row.length != header.length) throw new RuntimeException(row
                + " data is not correct");
        List<Object> objs = new ArrayList();
        Map<String, Object> classMappingHolder = new HashMap<String, Object>();

        for (int i = 0; i < row.length; i++) {
            int pos = header[i].indexOf(".");
            String className = null, fieldName = null;
            if (pos > 0) {
                className = header[i].substring(0, pos);
                fieldName = header[i].substring(pos + 1);
                if (null == clazzMap.get(className)) { //no mapping class defined
                    objs.add(row[i]);
                } else { //mapping class defined
                    if (null == classMappingHolder.get(className)) { //init
                        Object o = clazzMap.get(className).newInstance();
                        BeanUtils.setProperty(o, fieldName, row[i]);
                        classMappingHolder.put(className, o);
                        objs.add(o);
                    } else { //change the object value
                        BeanUtils.setProperty(classMappingHolder.get(className),
                                fieldName, row[i]);
                    }
                }
            } else {// no class defined
                objs.add(row[i]);
            }
        }

        return objs.toArray();
    }

    public static Iterator<Object[]> loadCSVtoIterator(String filePath, Map<String, Class> clazzMap) throws IOException, IllegalAccessException, InvocationTargetException, InstantiationException {

        String path = CSVHelper.class.getResource("/").getPath() + filePath;
        CSVReader reader = new CSVReader(new FileReader(path));
        List<String[]> inputs = reader.readAll();
        String[] header = inputs.get(0); //get header
        inputs.remove(0);
        List<Object[]> values = new ArrayList<Object[]>();
        for (String[] input : inputs) {
            values.add(convertToObjectArray(header, input, clazzMap));
        }

        return values.iterator();

    }

}
