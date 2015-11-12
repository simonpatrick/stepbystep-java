package com.hedwig.stepbystep.javatutorial.servicetest.base;

import com.beust.jcommander.internal.Lists;
import com.google.common.base.MoreObjects;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

/**
 * Created by patrick on 15/3/30.
 *
 * @version $Id$
 */


public class RequestData<T> {

    private Map<String,String> queryParameters= Maps.newHashMap();
    private Map<String,String> pathParameters=  Maps.newHashMap();
    private Map<String,String>  headers = Maps.newHashMap();
    private T body;
    private List<String> testList;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("queryParameters", queryParameters)
                .add("pathParameters", pathParameters)
                .add("headers", headers)
                .add("body",body)
                .toString();
    }

    public Map<String, String> getQueryParameters() {
        return queryParameters;
    }

    public void setQueryParameters(Map<String, String> queryParameters) {
        this.queryParameters = queryParameters;
    }

    public Map<String, String> getPathParameters() {
        return pathParameters;
    }

    public void setPathParameters(Map<String, String> pathParameters) {
        this.pathParameters = pathParameters;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public List<String> getTestList() {
        return testList;
    }

    public void setTestList(List<String> testList) {
        this.testList = testList;
    }
}
