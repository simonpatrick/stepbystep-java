package com.hedwig.stepbystep.javatutorial.helpers;

import com.google.common.collect.Lists;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.IOException;
import java.text.SimpleDateFormat;
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
                        Object o = ReflectionHelper.newInstance(clazzMap.get(className));
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
            data.add(getCellFormatValue(row.getCell(i)));
        }

        return data;
    }

    private HSSFSheet getSheet(String path, String sheetName) {
        FileHelper.checkIfSuitableFile(path,".xls",".xlsx");
        HSSFWorkbook wb ;
        POIFSFileSystem fs ;
        try {
            fs = new POIFSFileSystem(ClassLoader.getSystemResourceAsStream(path));
            wb = new HSSFWorkbook(fs);
        } catch (IOException e) {
            logger.error(e);
            throw new RuntimeException("excel file is not correct, please check the file in "+ path);
        }

        if (StringHelper.isNotEmptyOrNotBlankString(sheetName)) {
            return wb.getSheet(sheetName);
        } else {
            return wb.getSheetAt(0);
        }
    }


    /**
            * 获取单元格数据内容为字符串类型的数据
    *
            * @param cell Excel单元格
    * @return String 单元格数据内容
    */
    private String getStringCellValue(HSSFCell cell) {
        String strCell = "";
        switch (cell.getCellType()) {
            case HSSFCell.CELL_TYPE_STRING:
                strCell = cell.getStringCellValue();
                break;
            case HSSFCell.CELL_TYPE_NUMERIC:
                strCell = String.valueOf(cell.getNumericCellValue());
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN:
                strCell = String.valueOf(cell.getBooleanCellValue());
                break;
            case HSSFCell.CELL_TYPE_BLANK:
                strCell = "";
                break;
            default:
                strCell = "";
                break;
        }
        if (strCell.equals("") || strCell == null) {
            return "";
        }
        if (cell == null) {
            return "";
        }
        return strCell;
    }

    /**
     * 获取单元格数据内容为日期类型的数据
     *
     * @param cell
     *            Excel单元格
     * @return String 单元格数据内容
     */
    private String getDateCellValue(HSSFCell cell) {
        String result = "";
        try {
            int cellType = cell.getCellType();
            if (cellType == HSSFCell.CELL_TYPE_NUMERIC) {
                Date date = cell.getDateCellValue();
                result = (date.getYear() + 1900) + "-" + (date.getMonth() + 1)
                        + "-" + date.getDate();
            } else if (cellType == HSSFCell.CELL_TYPE_STRING) {
                String date = getStringCellValue(cell);
                result = date.replaceAll("[年月]", "-").replace("日", "").trim();
            } else if (cellType == HSSFCell.CELL_TYPE_BLANK) {
                result = "";
            }
        } catch (Exception e) {
            System.out.println("日期格式不正确!");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据HSSFCell类型设置数据
     * @param cell
     * @return
     */
    private String getCellFormatValue(HSSFCell cell) {
        String cellvalue = "";
        if (cell.getCellType()==0) cell.setCellType(1);
        if (cell != null) {
            // 判断当前Cell的Type
            switch (cell.getCellType()) {
                // 如果当前Cell的Type为NUMERIC
                case HSSFCell.CELL_TYPE_NUMERIC:
                case HSSFCell.CELL_TYPE_FORMULA: {
                    // 判断当前的cell是否为Date
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        // 如果是Date类型则，转化为Data格式

                        //方法1：这样子的data格式是带时分秒的：2011-10-12 0:00:00
                        //cellvalue = cell.getDateCellValue().toLocaleString();

                        //方法2：这样子的data格式是不带带时分秒的：2011-10-12
                        Date date = cell.getDateCellValue();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        cellvalue = sdf.format(date);

                    }
                    // 如果是纯数字
                    else {
                        // 取得当前Cell的数值
                        cellvalue = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                }
                // 如果当前Cell的Type为STRIN
                case HSSFCell.CELL_TYPE_STRING:
                    // 取得当前的Cell字符串
                    cellvalue = cell.getRichStringCellValue().getString();
                    break;
                // 默认的Cell值
                default:
                    cellvalue = " ";
            }
        } else {
            cellvalue = "";
        }
        return cellvalue;

    }
}
