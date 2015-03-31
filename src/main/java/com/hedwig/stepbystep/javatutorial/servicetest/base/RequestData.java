package com.hedwig.stepbystep.javatutorial.servicetest.base;

import com.beust.jcommander.internal.Lists;
import com.google.common.base.MoreObjects;

import java.util.List;
import java.util.Map;

/**
 * Created by patrick on 15/3/30.
 *
 * @version $Id$
 */


public class RequestData {

    private List<Map<String,String>> queryParameters= Lists.newArrayList();
    private List<Map<String,String>> pathParameters= Lists.newArrayList();
    private List<Map<String, String>> headers = Lists.newArrayList();

    public List<Map<String, String>> getQueryParameters() {
        return queryParameters;
    }

    public void setQueryParameters(List<Map<String, String>> queryParameters) {
        this.queryParameters=queryParameters ;
    }

    public List<Map<String, String>> getPathParameters() {
        return pathParameters;
    }

    public void setPathParameters(List<Map<String, String>> pathParameters) {
        this.pathParameters = pathParameters;
    }

    public List<Map<String, String>> getHeaders() {
        return headers;
    }

    public void setHeaders(List<Map<String, String>> headers) {
        this.headers = headers;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("queryParameters", queryParameters)
                .add("pathParameters", pathParameters)
                .add("headers", headers)
                .toString();
    }
}
