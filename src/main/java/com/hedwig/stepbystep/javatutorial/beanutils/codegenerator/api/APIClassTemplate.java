package com.hedwig.stepbystep.javatutorial.beanutils.codegenerator.api;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by kewu on 14-1-26.
 */
public class APIClassTemplate {

    public static String importMessage ="import com.ebay.maui.controller.Logging;\n" +
            "import com.stubhub.platform.api.RestAPI;\n" +
            "import com.stubhub.platform.api.request.Header;\n" +
            "import com.stubhub.platform.api.request.Method;\n" +
            "import com.stubhub.platform.api.request.Request;\n" +
            "import com.stubhub.platform.api.request.Response;\n" ;

    public final static String classTemplate="public class $apiName extends RestAPI{\n" +
            "\n" +
            "    private final static String endpoint =\"$endpoint\";\n" +
            "    private String endPointUrl ;\n" +
            "\n" +
            "    public $apiName() {\n" +
            "        super($methodEnum, endpoint);\n" +
            "\n" +
            "    }\n" +
            "\n" +
            "    public $apiName(String endPointUrl){\n" +
            "        super($methodEnum,endPointUrl);\n" +
            "        this.endPointUrl=endPointUrl;\n" +
            "    }\n" +
            "\n" +
            "    public Request buildRequest($apiParameter){\n" +
            "\n" +
            "        Request request = _buildRequest();\n" +
            "        $queryParameterSettingClause\n" +
            "        $parameterSettingClause\n" +
            "        $headerSettingClause\n" +
            "\n" +
            "        request.addHeader(new Header(\"Content-Type\", \"application/json\"));\n" +
            "        request.addHeader(new Header(\"Accept\",\"application/json\")) ;\n" +
            "\n" +
            "        Logging.log(request.toLog());\n" +
            "        return request;\n" +
            "    }\n" +
            "\n" +
            "    public Response getAPIResponse($apiParameter) throws Exception{\n" +
            "\n" +
            "        Request req = buildRequest($parameterList);\n" +
            "        return req.request();\n" +
            "    }\n" +
            "\n" +
            "}\n" +
            "\n";

    public String APIParameter(List<String> parameters,List<String> queryParameters,List<String> headers){
        StringBuilder sb = new StringBuilder();
       if(null!=parameters) {
        for (String parameter : parameters) {
            sb.append("String ");
            sb.append(parameter);
            sb.append(",");
        }
       }

       if(null!=queryParameters){
        for (String queryParameter : queryParameters) {
            sb.append("String ");
            sb.append(queryParameter);
            sb.append(",");
        }
       }
       if(null!=headers) {
        for (String header : headers) {
            sb.append("String ");
            sb.append(header);
            sb.append(",");
        }
       }

       return sb.toString().substring(0,sb.toString().length()-1);

    }

    public String getMethodEnum(String method){
        return "Method."+method;
    }

    public String parameterSettingClause(List<String> parameters){
        if(null==parameters) return "";
        String sampleSettingClause ="  if($parameter!=null){\n" +
                "           request.getEndpoint().addInlineParam(\"$parameter\",$parameter);\n" +
                "  }\n";
        StringBuilder sb = new StringBuilder();
        for (String parameter : parameters) {
            sb.append(sampleSettingClause.replace("$parameter",parameter));
        }

        return sb.toString();
    }

    public String queryParameterSettingClause(List<String> queryParameters){
        if(null==queryParameters) return "";
        String sampleSettingClause ="  if($parameter!=null){\n" +
                "           request.getEndpoint().addParam(\"$parameter\",$parameter);\n" +
                "  }\n";
        StringBuilder sb = new StringBuilder();
        for (String queryParameter : queryParameters) {
            sb.append(sampleSettingClause.replace("$parameter",queryParameter));
        }

        return sb.toString();
    }

    public String headerSettingClause(List<String> headers){
        String headerSettingClause ="request.addHeader(new Header(\"$parameter\",$parameter));\n";
        StringBuilder sb = new StringBuilder();

        if(null==headers) return "";

        for (String header : headers) {
            sb.append(headerSettingClause.replace("$parameter",header));
        }

        return sb.toString();
    }


    public static void main(String[] args) throws IOException {
        APIClassTemplate a = new APIClassTemplate();
        ObjectMapper m = new ObjectMapper();
        m.configure(DeserializationFeature.UNWRAP_ROOT_VALUE,false);
        String path = ClassLoader.getSystemClassLoader().getResource("apidescription.json").getPath();
        WebServiceDescription d = m.readValue(new File(path),WebServiceDescription.class);
        System.out.println(d);
        StringBuilder sb = new StringBuilder();
        sb.append(importMessage);
        String classContent = classTemplate;
        classContent=classContent.replace("$apiName",d.getApiName());
        classContent=classContent.replace("$endpoint",d.getEndPoint());
        classContent=classContent.replace("$methodEnum",a.getMethodEnum(d.getHttpMethod()));
        classContent=classContent.replace("$headerSettingClause", a.headerSettingClause(d.getHeaders()));
        classContent=classContent.replace("$parameterSettingClause",a.parameterSettingClause(d.getParameters()));
        classContent=classContent.replace("$queryParameterSettingClause",a.queryParameterSettingClause(d.getQueryParameters()));
        classContent=classContent.replace("$apiParameter",a.APIParameter(d.getParameters(), d.getQueryParameters(), d.getHeaders()));
        classContent=classContent.replace("$parameterList",a.APIParameter(d.getParameters(),d.getQueryParameters(),d.getHeaders()).replace("String",""));
        sb.append(classContent);

        System.out.println(sb.toString());
    }

}
