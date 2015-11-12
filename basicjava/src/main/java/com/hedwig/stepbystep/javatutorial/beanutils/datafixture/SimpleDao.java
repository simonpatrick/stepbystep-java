package com.hedwig.stepbystep.javatutorial.beanutils.datafixture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kewu on 14-2-27.
 */
public class SimpleDao<T extends BaseEntity> {

    private static final Logger logger= LoggerFactory.getLogger(SimpleDao.class);
    private static final String SELECT="SELECT ";
    private static final String FROM = " FROM ";
    private static final String WHERE =" WHERE ";
    private static final String AND=" AND ";
    private static final String UPDATE="UPDATE ";
    private static final String DELETE="DELETE ";
    private static final String SET=" SET ";
    private static final String INSERT="INSERT INTO ";
    private static final String VALUES=" VALUES ";
    private static Map<String,String> tableClassMap = new HashMap<String, String>();
    private String seqName;
    private String tableName;
    private String primaryKeyName;
    private Map<String,String> rowMap; //to do think the type

    static {
        tableClassMap.put("com.simpleDataFixture.SampleEntity","sample_entity");
    }

    private SimpleDao(){

    }

    public static SimpleDao createDao(){
       return new SimpleDao();
    }

    private void initDBTableMeta(T instance) throws Exception {
        this.seqName=instance.getSeqName();
        this.primaryKeyName=instance.getPrimaryKeyName();
        setTableNameIfExist(instance);
        this.rowMap=instance.getRowMap();
    }

    private void setTableNameIfExist(T instance) throws Exception {
        String table=tableClassMap.get(instance.getClass().getName());
        if(null!=table){
            this.tableName =table;
        }else{
            throw new Exception("There is no table existing!");//todo handle exception
        }
    }

     public void update(T instance) throws Exception {
      //update
      //return if there is no primary key value
        String primaryKeyValue=ReflectionUtils.getFieldValue(instance,instance.getPrimaryKeyName());
        if(null==primaryKeyValue){
            throw new Exception("This is no primary key value");
        }

        initDBTableMeta(instance);
        String setClause=buildSQLKeyValueMap(instance,",");
        String whereClause =instance.getPrimaryKeyName()+"="+primaryKeyValue;
        String sql = UPDATE+tableName+SET+setClause+WHERE+whereClause;
        logger.info("usedSql={}", sql);

    }

    public void insert(T instance) throws Exception {

        initDBTableMeta(instance);
        String columns=rowMap.get(instance.getPrimaryKeyName())+",";

        Map<String,String> map= new HashMap<String, String>();
        map.putAll(rowMap);
        String values="";
        map.remove(instance.getPrimaryKeyName());

        for (String s : map.keySet()) {
           columns=columns+map.get(s)+",";
           String value = ReflectionUtils.getFieldValue(instance,map.get(s));
           if(null==value||"NULL".equalsIgnoreCase(value)){
               value="";
           }

           values =values+"'"+value+"',"; //todo typeMap
        }
        columns = columns.substring(0,columns.length()-1);
        values=values.substring(0,values.length()-1);

        String sql= INSERT+tableName+"("+columns+")"+VALUES+"("
                +seqName+".nextval,"+values+")";

        logger.info("{}",sql);
    }

    public void delete(T instance) throws Exception {
        initDBTableMeta(instance);
        String whereClause="";

        whereClause =buildSQLKeyValueMap(instance,AND);
        if(null==whereClause||"".equalsIgnoreCase(whereClause)) return;
        String sql =DELETE+tableName+WHERE+whereClause;
        logger.info("{}",sql);

    }

    private String buildSQLKeyValueMap(T instance,String delimiter) throws IllegalAccessException {
       StringBuilder sb = new StringBuilder();
       Map<String,String> KVMap= instance.getRowMap();
        for (String key : KVMap.keySet()) {
            //filter rowsMap,rowMap,primaryKey,
            if(!"rowsMap".equalsIgnoreCase(key)&&!"primaryKeyName".equalsIgnoreCase(key)
                    &&!"seqName".equalsIgnoreCase(key)){
                String value = ReflectionUtils.getFieldValue(instance,key);
                if(null!=value){
                    sb.append(key).append("=").append("'"+value+"'").append(delimiter);
                }
            }
        }


        return sb.toString().substring(0,sb.toString().length()-delimiter.length());
    }

}
