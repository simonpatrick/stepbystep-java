package com.hedwig.stepbystep.javatutorial.servicetest.base;

import com.alibaba.fastjson.JSON;
import com.beust.jcommander.internal.Lists;
import com.hedwig.stepbystep.javatutorial.helpers.FileHelper;
import org.springframework.http.HttpMethod;

import java.util.List;
import java.util.Map;

/**
 * 类的说明: define service
 *
 * @author wuke
 * @version $Id$
 * @since 2.0
 */

public class ServiceDescription extends BaseJsonEntity {

    private List<String> queryParameters=Lists.newArrayList();
    private List<String> pathParameters= Lists.newArrayList();
    private List<Map<String, String>> headers = Lists.newArrayList();
    private String resourceURL;
    private String method;
    private String bodyClass; // # class name: com.dooioo.**.**
    private String body;

    /**
     * Load  service definition file
     * @param path
     * @return
     */
    public static ServiceDescription loadServiceDescriptionFromFile(String path){
        return JSON.parseObject(FileHelper.readFileToString(path),ServiceDescription.class);
    }

    /**
     * load service definition from String
     * @param serviceDescription
     * @return
     */
    public static ServiceDescription loadServiceDescription(String serviceDescription){
        return JSON.parseObject(serviceDescription,ServiceDescription.class);
    }

    public HttpMethod getMethod(){
        if(this.method.equalsIgnoreCase("GET")) return HttpMethod.GET;
        if(this.method.equalsIgnoreCase("POST")) return HttpMethod.POST;
        if(this.method.equalsIgnoreCase("DELETE")) return HttpMethod.DELETE;
        if(this.method.equalsIgnoreCase("PUT")) return HttpMethod.PUT;
        throw new RuntimeException(String.format("HTTP method {} is not defined",method));
    }


    public String getResourceURL() {
        return resourceURL;
    }

    public void setResourceURL(String resourceURL) {
        this.resourceURL = resourceURL;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<Map<String, String>> getHeaders() {
        return headers;
    }

    public void setHeaders(List<Map<String, String>> headers) {
        this.headers = headers;
    }

    public String getBodyClass() {
        return bodyClass;
    }

    public void setBodyClass(String bodyClass) {
        this.bodyClass = bodyClass;
    }


    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<String> getQueryParameters() {
        return queryParameters;
    }

    public void setQueryParameters(List<String> queryParameters) {
        this.queryParameters = queryParameters;
    }

    public List<String> getPathParameters() {
        return pathParameters;
    }

    public void setPathParameters(List<String> pathParameters) {
        this.pathParameters = pathParameters;
    }
}

