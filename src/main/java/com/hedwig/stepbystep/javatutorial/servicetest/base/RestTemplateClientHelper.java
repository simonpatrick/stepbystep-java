package com.hedwig.stepbystep.javatutorial.servicetest.base;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * 封装过的RestTemplateClient 基于Spring Rest Template
 * @param <T> HttpEntity
 */
public class RestTemplateClientHelper<T> {
    private static final Logger logger = LogManager.getLogger(RestTemplateClientHelper.class.getName());
    private RestTemplate template;
    private HttpHeaders headers;
    private List messageConverters;
    private HttpEntity<T> httpEntity;
    private T body;

    private static final Charset UTF_CHARSET=Charset.forName("utf-8");
    private static final List<MediaType> UTF8s = Lists.newArrayList(
            new MediaType("application","json",UTF_CHARSET)
            ,new MediaType("application","xml",UTF_CHARSET)
            ,new MediaType("text","html",UTF_CHARSET)
            ,new MediaType("text","plain",UTF_CHARSET)
            ,new MediaType("multipart","form-data",UTF_CHARSET)
            ,new MediaType("application","x-www-form-urlencoded",UTF_CHARSET)
    );
    private static final List<MediaType> StringUTF8 = Lists.newArrayList(
            new MediaType("text","html",UTF_CHARSET)
            ,new MediaType("text","plain",UTF_CHARSET)
            ,new MediaType("multipart","form-data",UTF_CHARSET)
            ,new MediaType("application","x-www-form-urlencoded",UTF_CHARSET)
    );

    private RestTemplateClientHelper(){}

    /**
     * 获取RestTemplateClient 实例
     * @return return a new RestTemplateClient
     */
    public static RestTemplateClientHelper getInstance(){
        RestTemplateClientHelper client = new RestTemplateClientHelper();
        client.setTemplate(new RestTemplate());
        return new RestTemplateClientHelper();
    }

    /**
     * 获取默认的RestTemplate
     * @return return a new RestTemplateClient
     */
    public static RestTemplateClientHelper getDefaultInstance(){
        RestTemplateClientHelper client = new RestTemplateClientHelper();
        client.setTemplate(new RestTemplate());
        client.useDefaultHeaders().useDefaultMessageConverter();
        return client;
    }

    /**
     * 获取HTTPClient实现的Rest template
     * @return return a new RestTemplateClient
     */
    public static RestTemplateClientHelper getHttpClientImplInstance(){
        RestTemplateClientHelper client = new RestTemplateClientHelper();
        client.setTemplate(new RestTemplate(new HttpComponentsClientHttpRequestFactory()));
        client.useDefaultHeaders().useDefaultMessageConverter();
        return client;
    }

    /**
     * 获取HTTPClient实现的Rest template
     * @return return a new RestTemplateClient
     */
    public static RestTemplateClientHelper getInstanceWODefaultConfiguration(){
        RestTemplateClientHelper client = new RestTemplateClientHelper();
        client.setTemplate(new RestTemplate(new HttpComponentsClientHttpRequestFactory()));
        return client;
    }
    /**
     * remove message converter
     * @return rest template client
     */
    @SuppressWarnings("unchecked")
    public RestTemplateClientHelper removeMessageConverter(){
        messageConverters = null;
        return this;
    }

    @SuppressWarnings("unchecked")
    public RestTemplateClientHelper useDefaultMessageConverter(){
        if(messageConverters==null) messageConverters =new ArrayList();
        messageConverters.add(new SourceHttpMessageConverter());
        FormHttpMessageConverter converter = new FormHttpMessageConverter();
        converter.setSupportedMediaTypes(UTF8s);
        messageConverters.add(converter);
        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
        stringConverter.setSupportedMediaTypes(StringUTF8);
        messageConverters.add(stringConverter);
        messageConverters.add(new FastJsonHttpMessageConverter());
        AllEncompassingFormHttpMessageConverter encompassingConvert = new AllEncompassingFormHttpMessageConverter();
        encompassingConvert.setSupportedMediaTypes(UTF8s);
        messageConverters.add(encompassingConvert);
        template.setMessageConverters(messageConverters);
        return this;
    }

    /**
     * add customer header
     * @param key header description
     * @param value header value
     * @return RestTemplateClient
     */
    public RestTemplateClientHelper addHeader(String key,String value){
        if(headers==null) headers = new HttpHeaders();
        headers.add(key,value);
        return this;
    }

    /**
     * add customer header
     * @param key header description
     * @param value header value
     * @return RestTemplateClient
     */
    public RestTemplateClientHelper addHeader(String key,List<String> value){
        if(headers==null) headers = new HttpHeaders();
        headers.put(key, value);
        return this;
    }

    /**
     * 生成request
     * @return RestTemplateClient
     */
    public RestTemplateClientHelper buildRequest(){
        if(body==null){
            httpEntity = new HttpEntity(headers);
        }else{
            httpEntity = new HttpEntity<T>(body,headers);
        }

        return this;
    }

    /**
     * 增加请求的body
     * @param body
     * @return RestTemplateClient
     */
    public RestTemplateClientHelper addBody(T body){
        this.body =body;
        return this;
    }

    /**
     * 使用默认的请求头
     * @return RestTemplateClient
     */
    public RestTemplateClientHelper useDefaultHeaders(){
        if(headers==null) headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Lists.newArrayList(MediaType.APPLICATION_JSON,
                MediaType.APPLICATION_XML, MediaType.TEXT_HTML,MediaType.TEXT_PLAIN,MediaType.ALL));
        headers.setAcceptCharset(Lists.newArrayList(Charset.forName("utf-8")));
        headers.add("Accept-Language","zh-CN,zh;q=0.8");
        return this;
    }


    /**
     * 发送请求
     * @param requestUrl
     * @param method
     * @return request's response
     */
    public ResponseEntity call(String requestUrl,HttpMethod method){
        buildRequest();
        logger.info("header={}",httpEntity);
        return template.exchange(requestUrl,method,httpEntity,String.class);
    }

    /**
     * 发送带request body的请求
     * @param requestUrl
     * @param method
     * @return request's response
     */
    public ResponseEntity call(String requestUrl,HttpMethod method,T body){
        addBody(body);
        buildRequest();
        logger.info("header={}",httpEntity);
        return template.exchange(requestUrl,method,httpEntity,String.class);
    }


    public RestTemplateClientHelper setContentType(MediaType mediaType){
        headers.setContentType(mediaType);
        return  this;
    }

    public void setClient(RestTemplate template) {
        this.template = template;
    }

    public RestTemplate getTemplate() {
        return template;
    }
    

    public HttpHeaders getHeaders() {
        return headers;
    }

    public RestTemplateClientHelper setHeaders(HttpHeaders headers) {
        this.headers = headers;
        return this;
    }

    public List getMessageConverters() {
        return messageConverters;
    }

    public void setMessageConverters(List messageConverters) {
        this.messageConverters = messageConverters;
    }

    public HttpEntity<T> getHttpEntity() {
        return httpEntity;
    }

    public void setHttpEntity(HttpEntity<T> httpEntity) {
        this.httpEntity = httpEntity;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public void setTemplate(RestTemplate template) {
        this.template = template;
    }

    //todo need
    public String asString(){
        return "";
    }

    public JSONObject asJson(){
        return null;
    }

    public RestTemplateClientHelper queryParameter(String key,String value){
        return this;
    }

    public RestTemplateClientHelper pathParameter(String key,String value){
        return this;
    }

}
