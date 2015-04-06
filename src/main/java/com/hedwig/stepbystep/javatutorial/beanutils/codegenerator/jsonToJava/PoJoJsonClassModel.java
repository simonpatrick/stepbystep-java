package com.hedwig.stepbystep.javatutorial.beanutils.codegenerator.jsonToJava;

import org.apache.commons.lang3.StringUtils;
import org.modeshape.common.text.Inflector;

import java.util.*;

/**
 * Created by kewu on 14-2-7.
 */
public class PoJoJsonClassModel extends PoJoClassModel{

    private static Map<String,String> typeMap = new HashMap<String,String>();
    static{
        typeMap.put("ARRAY","List");
        typeMap.put("NUMBER","Long");
        typeMap.put("OBJECT","Object");
        typeMap.put("STRING","String");
    }

    /**
     * adapt To PoJoClassModel
     * @param packageName
     * @param classContent
     * @return
     */
    public static List<PoJoJsonClassModel> AdaptToModel(String packageName,Map<String,Map<String,String>> classContent){
        List<PoJoJsonClassModel> adaptedModels = new ArrayList<PoJoJsonClassModel>();
        for (String s : classContent.keySet()) {
            PoJoJsonClassModel model = new PoJoJsonClassModel();
            model.setClassName(Inflector.getInstance().singularize(StringUtils.capitalize(s)));
            Map<String,String> fieldsOriginal= classContent.get(s);
            model.setPackageName(StringUtils.capitalize(packageName));
            for (String fieldName : fieldsOriginal.keySet()) {
                if(typeMap.get(fieldsOriginal.get(fieldName)).equalsIgnoreCase("OBJECT")){//todo filter??
                   model.getFields().put(fieldName,Inflector.getInstance().singularize(StringUtils.capitalize(fieldName)));
                }else if(typeMap.get(fieldsOriginal.get(fieldName)).equalsIgnoreCase("List")){
                    model.getFields().put(fieldName,"List<"+Inflector.getInstance().singularize(StringUtils.capitalize(fieldName))+">");
                }else{
                    model.getFields().put(fieldName,typeMap.get(fieldsOriginal.get(fieldName)));
                }

            }
            adaptedModels.add(model);
        }
        return adaptedModels;
    }


}
