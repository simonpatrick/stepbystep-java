package com.hedwig.stepbystep.javatutorial.excel;

import com.google.common.collect.Lists;
import com.hedwig.stepbystep.javatutorial.io.FileHelper;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import java.io.IOException;
import java.util.*;

/**
 * Created by patrick on 15/3/10.
 *
 * @version $Id$
 */


public class ExcelHelper {

    private ExcelHelper() {
    }
    private HSSFSheet sheet;
    private static final Logger logger = LogManager.getLogger(ExcelHelper.class.getName());

    public static ExcelHelper build(String path, String sheetName) {
        ExcelHelper helper = new ExcelHelper();
        helper.sheet= helper.getSheet(path, sheetName);

        return helper;
    }

    public static ExcelHelper build(String path) {
        ExcelHelper helper = new ExcelHelper();
        helper.sheet= helper.getSheet(path, "");

        return helper;
    }

    private Object[] convertToObjectArray(List<String> header,List<String> data,Map<String,Class> clazzMap)
            throws Exception {
        if (header.size() != data.size()) throw new RuntimeException(data
                + " data is not correct");
        List<Object> objs = new ArrayList();
        Map<String, Object> classMappingHolder = new HashMap<String, Object>();

        for (int i = 0; i < data.size(); i++) {
            int pos = header.get(i).indexOf(".");
            String className = null, fieldName = null;
            if (pos > 0) {
                className = header.get(i).substring(0, pos);
                fieldName = header.get(i).substring(pos + 1);
                if (null == clazzMap.get(className)) { //no mapping class defined
                    objs.add(data.get(i));
                } else { //mapping class defined
                    if (null == classMappingHolder.get(className)) { //init
                        Object o = clazzMap.get(className).newInstance();
                        BeanUtils.setProperty(o, fieldName, data.get(i));
                        classMappingHolder.put(className, o);
                        objs.add(o);
                    } else { //change the object value
                        BeanUtils.setProperty(classMappingHolder.get(className),
                                fieldName, data.get(i));
                    }
                }
            } else {// no class defined
                objs.add(data.get(i));
            }
        }

        return objs.toArray();
    }

    public Iterator<Object[]> loadExcelDataToIterator(Map<String, Class> clazzMap) throws Exception{

        List<String> headers = getHeaders();//get header

        List<Object[]> values = new ArrayList<Object[]>();
        for (int i = 1; i <sheet.getPhysicalNumberOfRows() ; i++) {
            values.add(convertToObjectArray(headers,getData(i),clazzMap));
        }
        return values.iterator();
    }

    private List<String> getHeaders() {
        return getData(0);
    }

    private List<String> getData(int rowNum) {
        HSSFRow row = sheet.getRow(rowNum);
        List<String> data = Lists.newArrayList();
        for (int i = 0; i < row.getPhysicalNumberOfCells(); i++) {
            data.add(row.getCell(i).getStringCellValue());
        }

        return data;
    }

    private HSSFSheet getSheet(String path, String sheetName) {
        FileHelper.checkIfSuitableFile(path, ".xls", ".xlsx");
        HSSFWorkbook wb ;
        POIFSFileSystem fs ;
        try {
            fs = new POIFSFileSystem(ClassLoader.getSystemResourceAsStream(path));
            wb = new HSSFWorkbook(fs);
        } catch (IOException e) {
            logger.error(e);
            throw new RuntimeException("excel file is not correct, please check the file in "+ path);
        }

        if (sheetName=="") {
            return wb.getSheet(sheetName);
        } else {
            return wb.getSheetAt(0);
        }
    }

}
