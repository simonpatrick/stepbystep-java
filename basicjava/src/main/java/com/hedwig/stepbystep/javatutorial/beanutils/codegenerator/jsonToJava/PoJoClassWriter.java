package com.hedwig.stepbystep.javatutorial.beanutils.codegenerator.jsonToJava;


import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * Created by kewu on 14-2-7.
 */
public abstract class PoJoClassWriter {


    private static final String KEYWORD_PACKAGE ="package";
    private static final String KEYWORD_ClassDeclaration ="public class $className {\n $classContent \n" +
            "}";

    private static final String KEYWORD_GETDeclaration= "public $type get$FieldName(){\n " +
            "return this.$fieldName;\n" +
            "}\n";

    private static final String KEYWORD_SETDeclaration ="public void set$FieldName($type $fieldName){\n this.$fieldName=$fieldName;\n" +
            "}\n";

    private static final String KEYWORD_FIELDDeclaration ="private $type $fieldName;\n";
    //private static List<String> jdkCollectionImportLib = Arrays.asList("List","Map","ArrayList","HashMap");
    private static String KEYWORD_IMPORTJDK="import java.util.*;\n";

    public Map<String,String> writeAsValue(List<? extends PoJoClassModel> models){

        Map<String,String> pojoClassList = new HashMap<String,String>(); //key/value:className/classContent
        StringBuffer fieldsAndGetSet = new StringBuffer();

        for (PoJoClassModel model : models) {
            StringBuffer sb = new StringBuffer();
            sb.append(generatePackageName(model.getPackageName()));
            sb.append(KEYWORD_IMPORTJDK);
            sb.append(generateImportStatement());
            /* 1. generate field
            2. generate getter/setter*/

            fieldsAndGetSet.append(generateField(model.getFields()));
            fieldsAndGetSet.append(generateGetSetStatement(model.getFields()));
            sb.append(generateClassDeclaration(model.getClassName(),fieldsAndGetSet.toString()));

            fieldsAndGetSet.setLength(0);
            pojoClassList.put(model.getClassName(),sb.toString());
        }

       return pojoClassList;
    }

    public abstract String generateAnnotation(String fieldName);

    public abstract String generateImportStatement();

    private String generatePackageName(String packageName){
        return KEYWORD_PACKAGE+" "+ packageName+";\n";
    }

    private String generateField(Map<String,String> fields){
        StringBuffer sb = new StringBuffer();

        //todo think about write listener
        for (String s : fields.keySet()) {
            sb=sb.append(generateAnnotation(s));
            sb.append(KEYWORD_FIELDDeclaration.replace("$type",fields.get(s)).replace("$fieldName",s));
        }

        return sb.toString();
    }

    private String generateGetSetStatement(Map<String,String> fields){
        StringBuffer sb = new StringBuffer();
        for (String s : fields.keySet()) {
            //todo think about write listener
           sb.append(generateGetStatement(fields.get(s),s));
           sb.append(generateSetStatement(fields.get(s),s));
        }
        return sb.toString();
    }

    private String generateClassDeclaration(String className,String classContent){
        StringBuffer sb = new StringBuffer();
        String temp = KEYWORD_ClassDeclaration;
        sb.append(temp.replace("$className", className).replace("$classContent", classContent));
        return sb.toString();
    }

    private String generateGetStatement(String type,String name){
        StringBuffer sb = new StringBuffer();
        if(type.equalsIgnoreCase("boolean")){
            sb.append(KEYWORD_GETDeclaration.replace("get","is"));
        }else{
            sb.append(KEYWORD_GETDeclaration.replace("$type", type)
                    .replace("$FieldName", StringUtils.capitalize(name)).replace("$fieldName", name));
        }

        return sb.toString();
    }

    private String generateSetStatement(String type,String name){
        StringBuffer sb = new StringBuffer();
        sb.append(KEYWORD_SETDeclaration.replace("$type",type).replace("$fieldName", name).
                replace("$FieldName", StringUtils.capitalize(name)));
        return sb.toString();
    }

}
