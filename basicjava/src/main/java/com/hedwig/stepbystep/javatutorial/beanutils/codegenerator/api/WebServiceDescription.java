package com.hedwig.stepbystep.javatutorial.beanutils.codegenerator.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kewu on 14-1-26.
 */
public class WebServiceDescription {


    private String apiName;
    private String endPoint;
    private List<String> parameters;
    private List<String> queryParameters;
    private List<String> headers;
    private String requestBody ;
    private String httpMethod;
    private String response;

    public static void main(String[] args) throws JsonProcessingException {
        WebServiceDescription s = new WebServiceDescription();
        s.apiName="PdfParserAPI";
        s.endPoint="https://api-dev.poolNo.com/fulfillment/pdf/v2/parsing/listingId/{%listingId%}";
        List<String> headers = new ArrayList<String>();
        headers.add("listingId");
        s.queryParameters = Lists.newArrayList("fileInfoIds","eventId");
        s.httpMethod ="GET";
        System.out.println(s.toString());
        ObjectMapper m = new ObjectMapper();
        //m.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String jsonTemplate =m.writeValueAsString(s);
        System.out.println(jsonTemplate);

    }

    public String getAPIParameter(){
        StringBuilder sb = new StringBuilder();
        for (String parameter : parameters) {
            sb.append("String ");
            sb.append(parameter);
        }

        for (String queryParameter : queryParameters) {
            sb.append("String ");
            sb.append("queryParameters");
        }

        return sb.toString();
    }


    @Override
    public String toString() {
        return "WebServiceDescription{" +
                "apiName='" + apiName + '\'' +
                ", endPoint='" + endPoint + '\'' +
                ", parameters=" + parameters +
                ", queryParameters=" + queryParameters +
                ", headers=" + headers +
                ", requestBody='" + requestBody + '\'' +
                ", httpMethod='" + httpMethod + '\'' +
                '}';
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public List<String> getParameters() {
        return parameters;
    }

    public void setParameters(List<String> parameters) {
        this.parameters = parameters;
    }

    public List<String> getQueryParameters() {
        return queryParameters;
    }

    public void setQueryParameters(List<String> queryParameters) {
        this.queryParameters = queryParameters;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
