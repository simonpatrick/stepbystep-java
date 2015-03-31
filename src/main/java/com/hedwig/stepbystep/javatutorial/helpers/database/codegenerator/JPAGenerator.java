//package com.hedwig.stepbystep.javatutorial.helpers.database.codegenerator;
//
//import com.google.common.base.Function;
//import com.hedwig.stepbystep.javatutorial.helpers.FileHelper;
//import com.hedwig.stepbystep.javatutorial.helpers.StringHelper;
//import com.hedwig.stepbystep.javatutorial.helpers.database.SpringJdbcTemplateUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by patrick on 15/3/3.
// *
// * @version $Id$
// */
//
//
//public class JPAGenerator implements IJPAGenerator{
//
//    private static final Logger logger = LoggerFactory.getLogger(JPAGenerator.class);
//    private static final String TABLE_ANNOTATION ="@Entity";
//    private static final String ENTITY_ANNOTATION="@Table(name=\"%s\")";
//    private static final String ID_ANNOTATION ="@Id";
//    private static final String GENERATEDVALUE_ANNOTATION="@GeneratedValue(strategy = GenerationType.IDENTITY)";
//    private static final String DEFINE="private ${Type} ${Name};";
//    private static final String END="}";
//    private static final String JPA_IMPORT = "import javax.persistence.*;";
//    private String packageName = "package com.dooioo.automation." ;
//    private final static String TABLE_PREFIX ="T_";
//    private final static String TABLE_PREFIX_1 ="t_";
//    private String domainName ="database" ;
//    private String tableName;
//    private String className;
//    private static final String SQL="select * from INFORMATION_SCHEMA.columns where table_name=?";
//
//    public JPAGenerator(String tableName) {
//        this.tableName = tableName;
//        this.className = generateClassName(tableName);
//    }
//
//    private String classHeader(){
//        StringBuilder sb = new StringBuilder(packageName+domainName+";");
//        sb.append("\n");
//        sb.append(JPA_IMPORT);
//        sb.append("\n");
//        sb.append("\n");
//        sb.append("\n");
//        sb.append("\n");
//        sb.append(TABLE_ANNOTATION);
//        sb.append("\n");
//        sb.append(getEntityAnnotation());
//
//        return sb.toString();
//    }
//
//    private String generateClassContent(){
//        StringBuilder sb = new StringBuilder("public class "+generateClassName(tableName)+"{"+"\n");
//        List<Map<String,Object>> result = SpringJdbcTemplateUtils.getJdbcTemplate().queryForList(SQL,tableName);
//        for (Map<String, Object> map : result) {
//            if(map.get("COLUMN_NAME").toString().equalsIgnoreCase("ID")){
//                sb.append(ID_ANNOTATION);
//                sb.append("\n");
//                sb.append(GENERATEDVALUE_ANNOTATION);
//                sb.append("\n");
//            }
//            String statement =generateDeclaration(map.get("COLUMN_NAME").toString(), map.get("DATA_TYPE").toString());
//            sb.append(statement + "\n");
//        }
//        sb.append(END);
//        return sb.toString();
//    }
//
//
//    private String getEntityAnnotation(){
//        return String.format(ENTITY_ANNOTATION,tableName.toUpperCase());
//    }
//
//    private String generateClassName(String tableName){
//        if(tableName.startsWith(TABLE_PREFIX)||tableName.startsWith(TABLE_PREFIX_1)){
//            tableName = tableName.substring(2,tableName.length());
//        }
//        String[] temp = tableName.split("_");
//        return StringHelper.join(temp, new Function<String, String>() {
//            @Override
//            public String apply(String input) {
//                return StringUtils.capitalize(input.trim());
//            }
//        });
//    }
//
//    private String generateDeclaration(String key,String dataType){
//        logger.info("datatype={}",dataType);
//        String declaration = DEFINE.replace("${Type}",SqlServerJavaTypeHelper.
//                getJavaType(dataType)).replace("${Name}", convertUnderScoreNameToCamel(key));
//        return declaration;
//    }
//
//    private String convertUnderScoreNameToCamel(String key){
//
//        if(key.contains("_")){
//            String[] temp = key.split("_");
//            StringBuilder sb = new StringBuilder();
//            for (String s : temp) {
//                sb.append(StringUtils.capitalize(s.toLowerCase()));
//            }
//
//            return StringUtils.uncapitalize(sb.toString());
//        }else{
//            return key.toLowerCase();
//        }
//
//    }
//
//    @Override
//    public void generate() {
//        StringBuilder sb = new StringBuilder(classHeader());
//        sb.append("\n");
//        sb.append(generateClassContent());
//        try {
//            FileHelper.writeToFile(this.className + ".java", sb.toString());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void main(String[] args) {
//        JPAGenerator g = new JPAGenerator("T_bms_order");
//        g.generate();
//    }
//}
