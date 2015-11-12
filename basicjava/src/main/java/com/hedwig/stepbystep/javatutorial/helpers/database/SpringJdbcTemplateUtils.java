package com.hedwig.stepbystep.javatutorial.helpers.database;//package com.hedwig.stepbystep.javatutorial.helpers.database;
//
//import com.google.common.base.Predicate;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.dao.EmptyResultDataAccessException;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//
//import javax.persistence.Id;
//import javax.persistence.Table;
//import java.lang.annotation.Annotation;
//import java.lang.reflect.Field;
//import java.sql.Date;
//import java.util.Collections;
//import java.util.List;
//import java.util.Map;
//
///**
// * 数据库操作类，基于spring jdbc template
// */
//public class SpringJdbcTemplateUtils {
//
//    private static final Logger logger = LogManager.getLogger(SpringJdbcTemplateUtils.class);
//    private static JdbcTemplate instance;
//    private static DriverManagerDataSource dataSource;
//
//
//    private SpringJdbcTemplateUtils() {
//    }
//
//    private synchronized static void initDataSource(){
//        //1. according different environment to init different
//        logger.info("start init data source");
//        dataSource= new DriverManagerDataSource();
//        dataSource.setUrl(EnvironmentHelper.getDbURL());
//        dataSource.setDriverClassName(EnvironmentHelper.getDbDriver());
//        dataSource.setUsername(EnvironmentHelper.getDbUserName());
//        dataSource.setPassword(EnvironmentHelper.getDbPassword());
//
//    }
//
//    private synchronized static JdbcTemplate initJdbcTemplate() {
//        if (null == instance) {
//            instance = new JdbcTemplate();
//            initDataSource();
//            instance.setDataSource(dataSource);
//        }
//        return instance;
//    }
//
//    /**
//     * query for return a object
//     * @param sql
//     * @param clazz
//     * @param args
//     * @param <T>
//     * @return
//     */
//    public static <T> T queryForObject(String sql, Class<T> clazz, Object... args) {
//        RowMapper<T> rowMapper = BeanPropertyRowMapper.newInstance(clazz);
//        try {
//            return initJdbcTemplate().queryForObject(sql, rowMapper, args);
//        } catch (EmptyResultDataAccessException e) {
//            return null;
//        }
//
//    }
//
//     public static int update(String sql, Object... args) {
//       return initJdbcTemplate().update(sql, args);
//    }
//
//      public static <T> void insert(T instance) throws Exception {
//        String sql = "Insert Into ";
//        String tableName =null ;
//        for (Annotation annotation : instance.getClass().getAnnotations()) {
//            if(annotation instanceof Table){
//                tableName = ((Table) annotation).name();
//            }
//        }
//
//        if(tableName==null) {
//            throw new RuntimeException("no table found for entity "+ instance.toString());
//        }
//
//        StringBuilder values = new StringBuilder("( ");
//        StringBuilder columns = new StringBuilder("(");
//        for (Field field : instance.getClass().getDeclaredFields()) {
//
//            if(field.getAnnotation(Id.class )==null){
//                columns.append(field.getName());
//                values.append("'");
//                field.setAccessible(true);
//                if(field.getType().equals(Date.class)){
//                    values.append(DateHelper.formatISODateTime((Date) field.get(instance)));
//                }else{
//                    values.append(field.get(instance));
//                }
//                values.append("'");
//                values.append(",");
//                columns.append(",");
//            }
//
//        }
//
//        String valueClause= values.substring(0,values.length()-1);
//        valueClause = valueClause+")";
//        String columnClause = columns.substring(0,columns.length()-1);
//        columnClause=columnClause+")";
//
//        if(tableName!=null){
//            sql = sql+tableName+columnClause+"values"+valueClause;
//        }else{
//            throw new Exception("no table found!");
//        }
//
//        logger.info("sql={}",sql);
//        initJdbcTemplate().execute(sql);
//
//    }
//
//    /**
//     * query for list of object
//     * @param sql
//     * @param clazz
//     * @param args
//     * @param <T>
//     * @return
//     */
//    public static <T> List<T> queryForList(String sql, Class<T> clazz, Object... args) {
//        RowMapper<T> rowMapper = BeanPropertyRowMapper.newInstance(clazz);
//        try {
//
//            return  initJdbcTemplate().query(sql, rowMapper, args);
//
//        } catch (EmptyResultDataAccessException e) {
//
//            return  Collections.emptyList();
//        }
//
//    }
//
//    public static JdbcTemplate getJdbcTemplate(){
//        return initJdbcTemplate();
//    }
//
//    public static String getStringValue(String sql,String key){
//
//        return (String)getJdbcTemplate().queryForMap(sql).get(key);
//    }
//
//    public static String getStringValue(String sql,String key,Object...args){
//
//        return (String)getJdbcTemplate().queryForMap(sql,args).get(key);
//    }
//
//
//    public static Object getValue(String sql,String key){
//
//        return getJdbcTemplate().queryForMap(sql).get(key);
//    }
//
//    public static Object getValue(String sql,String key,Object...args){
//
//        return getJdbcTemplate().queryForMap(sql,args).get(key);
//    }
//
//    public static List<Map<String,Object>> getAllRawResult(String sql,Object...args){
//
//        return getJdbcTemplate().queryForList(sql,args);
//    }
//
//    public static  List<Map<String,Object>> getResultByCriteria(String sql
//            , Predicate<Map<String,Object>> criteria,Object...args){
//        List<Map<String,Object>> originalResult = getJdbcTemplate().queryForList(sql,args);
//        return CollectionsHelper.filter(originalResult,criteria);
//    }
//
//    public static Map<String,Object> getSingleResultByCriteria(String sql
//            , Predicate<Map<String,Object>> criteria,Object ...args){
//        List<Map<String,Object>> originalResult = getJdbcTemplate().queryForList(sql,args);
//        return CollectionsHelper.filterByCondition(originalResult, criteria);
//    }
//}
