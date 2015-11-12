package com.hedwig.stepbystep.javatutorial.beanutils.codegenerator.api;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by kewu on 14-1-26.
 */
public class CodeGenerator {
    private final APIClassTemplate apiClassTemplate;
    private WebServiceDescription d;

    public CodeGenerator(APIClassTemplate apiClassTemplate) {
        this.apiClassTemplate = apiClassTemplate;
    }



    public String generateAPICode(String apiDescriptionJson) throws IOException {


        ObjectMapper m = new ObjectMapper();
        m.configure(DeserializationFeature.UNWRAP_ROOT_VALUE,false);
        String path = ClassLoader.getSystemClassLoader().getResource(apiDescriptionJson).getPath();
        this.d = m.readValue(new File(path), WebServiceDescription.class);
        System.out.println(d);
        StringBuilder sb = new StringBuilder();
        sb.append(APIClassTemplate.importMessage);
        String classContent = APIClassTemplate.classTemplate;
        classContent=classContent.replace("$apiName",d.getApiName());
        classContent=classContent.replace("$endpoint",d.getEndPoint());
        classContent=classContent.replace("$methodEnum",apiClassTemplate.getMethodEnum(d.getHttpMethod()));
        classContent=classContent.replace("$headerSettingClause", apiClassTemplate.headerSettingClause(d.getHeaders()));
        classContent=classContent.replace("$parameterSettingClause", apiClassTemplate.parameterSettingClause(d.getParameters()));
        classContent=classContent.replace("$queryParameterSettingClause", apiClassTemplate.queryParameterSettingClause(d.getQueryParameters()));
        classContent=classContent.replace("$apiParameter", apiClassTemplate.APIParameter(d.getParameters(), d.getQueryParameters(), d.getHeaders()));
        classContent=classContent.replace("$parameterList", apiClassTemplate.APIParameter(d.getParameters(), d.getQueryParameters(), d.getHeaders()).replace("String", ""));
        sb.append(classContent);

        return sb.toString();
    }

    public static void main(String[] args) throws IOException {
       CodeGenerator c = new CodeGenerator(new APIClassTemplate());
       //String javaClass = c.generateAPICode(args[0]);
       String javaClass = c.generateAPICode("apidescription.json");
       //File file = new File(args[1]);
       File file = new File(c.d.getApiName()+".java");
       if(file.exists()){
           file.delete();
       }else{
           file.createNewFile();
       }

        FileUtils.write(file,javaClass);

    }

}
