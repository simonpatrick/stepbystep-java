package com.hedwig.stepbystep.javatutorial.servicetest.base;


/**
 * Created by patrick on 15/3/30.
 *
 * @version $Id$
 */


public class RequestBuilder {

    private RestTemplateClientHelper restHelper = RestTemplateClientHelper.getHttpClientImplInstance();

    public RequestBuilder buildRequest(String descriptionPath,RequestData data){

        ServiceDescription sd = ServiceDescription.loadServiceDescriptionFromFile(descriptionPath);

        return this;
    }


}
