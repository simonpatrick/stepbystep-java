package com.springdemo.learningMVC.common.src.main.java.com.common.web.method;

import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.ServletRequestParameterPropertyValues;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.util.WebUtils;
import com.common.web.annotation.FormModel;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 */
public class FormModelMethodArgumentResolver extends BaseMethodArgumentResolver {

    /**
     * 提取索引的模式 如[0].
     */
    private final Pattern INDEX_PATTERN = Pattern.compile("\\[(\\d+)\\]\\.?");

    private int autoGrowCollectionLimit = Integer.MAX_VALUE;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(FormModel.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory)
            throws Exception {

        FormModel formModel = parameter.getParameterAnnotation(FormModel.class);
        String name = formModel.value().length() == 0 ?
                parameter.getParameterName() : formModel.value();

        Object target = mavContainer.containsAttribute(name) ?
                mavContainer.getModel().get(name) :
                createAttribute(name, parameter, webRequest, binderFactory);

        WebDataBinder binder = binderFactory.createBinder(webRequest, target, name);
        target = binder.getTarget();
        if (target != null) {
            bindRequestParameters(mavContainer, binderFactory, binder, webRequest, parameter);

            validateIfApplicable(binder, parameter);
            if (binder.getBindingResult().hasErrors()) {
                if (isBindExceptionRequired(binder, parameter)) {
                    throw new BindException(binder.getBindingResult());
                }
            }
        }

        target = binder.convertIfNecessary(binder.getTarget(), parameter.getParameterType());
//        mavContainer.addAttribute(name, target);
        Map<String, Object> bindingResultModel = binder.getBindingResult().getModel();
        mavContainer.removeAttributes(bindingResultModel);
        mavContainer.addAllAttributes(bindingResultModel);

        return target;
    }

    private Object createAttribute(String name,
                                   MethodParameter parameter,
                                   NativeWebRequest webRequest,
                                   WebDataBinderFactory binderFactory) throws Exception {
        String value = getRequestValueForAttribute(name, webRequest);

        if (value != null) {
            Object attribute = createAttributeFromRequestValue(
                    value, name, parameter, binderFactory, webRequest);
            if (attribute != null) {
                return attribute;
            }
        }
        Class<?> parameterType = parameter.getParameterType();
        if (parameterType.isArray() || List.class.isAssignableFrom(parameterType)) {
            return new ArrayList<>();
        }
        if (Set.class.isAssignableFrom(parameterType)) {
            return new HashSet<>();
        }

//        if (MapWapper.class.isAssignableFrom(parameterType)) {
//            return MapWapper.class.newInstance();
//        }

        return BeanUtils.instantiateClass(parameter.getParameterType());
    }

    /**
     * Obtain a value from the request that may be used to instantiate the
     * model attribute through type conversion from String to the target type.
     * <p>The default implementation looks for the attribute name to match
     * a URI variable first and then a request parameter.
     *
     * @param attributeName the model attribute name
     * @param request       the current request
     * @return the request value to try to convert or {@code null}
     */
    protected String getRequestValueForAttribute(String attributeName, NativeWebRequest request) {
        Map<String, String> variables = getUriTemplateVariables(request);
        String value = variables.get(attributeName);
        if (StringUtils.hasText(value)) {
            return value;
        } else if (StringUtils.hasText((value = request.getParameter(attributeName)))) {
            return value;
        } else {
            return null;
        }
    }

    /**
     * Create a model attribute from a String request value (e.g. URI template
     * variable, request parameter) using type conversion.
     * <p>The default implementation converts only if there a registered
     * {@link org.springframework.core.convert.converter.Converter} that can perform the conversion.
     *
     * @param sourceValue   the source value to create the model attribute from
     * @param attributeName the name of the attribute, never {@code null}
     * @param parameter     the method parameter
     * @param binderFactory for creating WebDataBinder instance
     * @param request       the current request
     * @return the created model attribute, or {@code null}
     * @throws Exception
     */
    protected Object createAttributeFromRequestValue(String sourceValue,
                                                     String attributeName,
                                                     MethodParameter parameter,
                                                     WebDataBinderFactory binderFactory,
                                                     NativeWebRequest request) throws Exception {
        DataBinder binder = binderFactory.createBinder(request, null, attributeName);
        ConversionService conversionService = binder.getConversionService();
        if (conversionService != null) {
            TypeDescriptor source = TypeDescriptor.valueOf(String.class);
            TypeDescriptor target = new TypeDescriptor(parameter);
            if (conversionService.canConvert(source, target)) {
                return binder.convertIfNecessary(sourceValue, parameter.getParameterType(), parameter);
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     * <p>Downcast {@link org.springframework.web.bind.WebDataBinder} to {@link org.springframework.web.bind.ServletRequestDataBinder} before binding.
     *
     * @throws Exception
     * @see org.springframework.web.servlet.mvc.method.annotation.ServletRequestDataBinderFactory
     */
    protected void bindRequestParameters(
            ModelAndViewContainer mavContainer,
            WebDataBinderFactory binderFactory,
            WebDataBinder binder,
            NativeWebRequest request,
            MethodParameter parameter) throws Exception {

        Map<String, Boolean> hasProcessedPrefixMap = new HashMap<>();

        Class<?> targetType = binder.getTarget().getClass();
        ServletRequest servletRequest = prepareServletRequest(binder.getTarget(), request, parameter);
        WebDataBinder simpleBinder = binderFactory.createBinder(request, null, null);

        if (Collection.class.isAssignableFrom(targetType)) {//bind collection or array

            Type type = parameter.getGenericParameterType();
            Class<?> componentType = Object.class;

            @SuppressWarnings("unchecked")
            Collection<Object> target = (Collection<Object>) binder.getTarget();

            List<Object> targetList = Lists.newArrayList(target);

            if (type instanceof ParameterizedType) {
                componentType = (Class<?>) ((ParameterizedType) type).getActualTypeArguments()[0];
            }

            if (parameter.getParameterType().isArray()) {
                componentType = parameter.getParameterType().getComponentType();
            }

            for (Object key : servletRequest.getParameterMap().keySet()) {
                String prefixName = getPrefixName((String) key);

                //每个prefix 只处理一次
                if (hasProcessedPrefixMap.containsKey(prefixName)) {
                    continue;
                } else {
                    hasProcessedPrefixMap.put(prefixName, Boolean.TRUE);
                }

                if (isSimpleComponent(prefixName)) { //bind simple type
                    Map<String, Object> paramValues = WebUtils.getParametersStartingWith(servletRequest, prefixName);
                    Matcher matcher = INDEX_PATTERN.matcher(prefixName);
                    if(!matcher.matches()) { //处理如 array=1&array=2的情况
                        for (Object value : paramValues.values()) {
                            targetList.add(simpleBinder.convertIfNecessary(value, componentType));
                        }
                    } else {  //处理如 array[0]=1&array[1]=2的情况
                        int index = Integer.valueOf(matcher.group(1));

                        if (targetList.size() <= index) {
                            growCollectionIfNecessary(targetList, index);
                        }
                        targetList.set(index, simpleBinder.convertIfNecessary(paramValues.values(), componentType));
                    }
                } else { //处理如 votes[1].title=votes[1].title&votes[0].title=votes[0].title&votes[0].id=0&votes[1].id=1
                    Object component = null;
                    //先查找老的 即已经在集合中的数据（而不是新添加一个）
                    Matcher matcher = INDEX_PATTERN.matcher(prefixName);
                    if(!matcher.matches()) {
                        throw new IllegalArgumentException("bind collection error, need integer index, key:" + key);
                    }
                    int index = Integer.valueOf(matcher.group(1));
                    if (targetList.size() <= index) {
                        growCollectionIfNecessary(targetList, index);
                    }
                    Iterator<Object> iterator = targetList.iterator();
                    for (int i = 0; i < index; i++) {
                        iterator.next();
                    }
                    component = iterator.next();

                    if(component == null) {
                        component = BeanUtils.instantiate(componentType);
                    }

                    WebDataBinder componentBinder = binderFactory.createBinder(request, component, null);
                    component = componentBinder.getTarget();

                    if (component != null) {
                        ServletRequestParameterPropertyValues pvs = new ServletRequestParameterPropertyValues(servletRequest, prefixName, "");
                        componentBinder.bind(pvs);
                        validateIfApplicable(componentBinder, parameter);
                        if (componentBinder.getBindingResult().hasErrors()) {
                            if (isBindExceptionRequired(componentBinder, parameter)) {
                                throw new BindException(componentBinder.getBindingResult());
                            }
                        }
                        targetList.set(index, component);
                    }
                }
                target.clear();
                target.addAll(targetList);
            }
//        } else if (Multimap.class.isAssignableFrom(targetType)) {
//
//
//            Type type = parameter.getGenericParameterType();
//            Class<?> keyType = Object.class;
//            Class<?> valueType = Object.class;
//
//            if (type instanceof ParameterizedType) {
//                keyType = (Class<?>) ((ParameterizedType) type).getActualTypeArguments()[0];
//                valueType = (Class<?>) ((ParameterizedType) type).getActualTypeArguments()[1];
//            }
//
//
//            Multimap<?, ?> multimap = ((Multimap<?, ?>) binder.getTarget());
//            Map<?, ?> target = multimap.asMap();
//            if(target == null) {
//                target = new HashMap();
//                multimap.setInnerMap(target);
//            }
//
//            for (Object key : servletRequest.getParameterMap().keySet()) {
//                String prefixName = getPrefixName((String) key);
//
//                //每个prefix 只处理一次
//                if (hasProcessedPrefixMap.containsKey(prefixName)) {
//                    continue;
//                } else {
//                    hasProcessedPrefixMap.put(prefixName, Boolean.TRUE);
//                }
//
//                Object keyValue = simpleBinder.convertIfNecessary(getMapKey(prefixName), keyType);
//
//                if (isSimpleComponent(prefixName)) { //bind simple type
//                    Map<String, Object> paramValues = WebUtils.getParametersStartingWith(servletRequest, prefixName);
//
//                    for (Object value : paramValues.values()) {
//                        target.put(keyValue, simpleBinder.convertIfNecessary(value, valueType));
//                    }
//                } else {
//
//                    Object component = target.get(keyValue);
//                    if(component == null) {
//                        component = BeanUtils.instantiate(valueType);
//                    }
//
//                    WebDataBinder componentBinder = binderFactory.createBinder(request, component, null);
//                    component = componentBinder.getTarget();
//
//                    if (component != null) {
//                        ServletRequestParameterPropertyValues pvs = new ServletRequestParameterPropertyValues(servletRequest, prefixName, "");
//                        componentBinder.bind(pvs);
//
//                        validateComponent(componentBinder, parameter);
//
//                        target.put(keyValue, component);
//                    }
//                }
//            }
        } else {//bind model
            ServletRequestDataBinder servletBinder = (ServletRequestDataBinder) binder;
            servletBinder.bind(servletRequest);
        }
    }

    private void growCollectionIfNecessary(final Collection<?> collection, final int index) {
        if(index >= collection.size() && index < this.autoGrowCollectionLimit) {
            for (int i = collection.size(); i <= index; i++) {
                collection.add(null);
            }
        }
    }


    private Object getMapKey(String prefixName) {
        String key = prefixName;
        if (key.startsWith("['")) {
            key = key.replaceAll("\\[\'", "").replaceAll("\'\\]", "");
        }
        if (key.startsWith("[\"")) {
            key = key.replaceAll("\\[\"", "").replaceAll("\"\\]", "");
        }
        if (key.endsWith(".")) {
            key = key.substring(0, key.length() - 1);
        }
        return key;
    }

    private boolean isSimpleComponent(String prefixName) {
        return !prefixName.endsWith(".");
    }

    private String getPrefixName(String name) {

        int begin = 0;

        int end = name.indexOf("]") + 1;

        if (name.contains("].")) {
            end = end + 1;
        }

        return name.substring(begin, end);
    }

    private ServletRequest prepareServletRequest(Object target, NativeWebRequest request, MethodParameter parameter) {

        String modelPrefixName = parameter.getParameterAnnotation(FormModel.class).value();

        HttpServletRequest nativeRequest = (HttpServletRequest) request.getNativeRequest();
        MultipartRequest multipartRequest = WebUtils.getNativeRequest(nativeRequest, MultipartRequest.class);

        MockHttpServletRequest mockRequest = null;
        if (multipartRequest != null) {
            MockMultipartHttpServletRequest mockMultipartRequest = new MockMultipartHttpServletRequest();
            for(MultipartFile file : multipartRequest.getFileMap().values()) {
                mockMultipartRequest.addFile(new MultipartFileWrapper(getNewParameterName(file.getName(), modelPrefixName), file));
            }
            mockRequest = mockMultipartRequest;
        } else {
            mockRequest = new MockHttpServletRequest();
        }

        for (Map.Entry<String, String> entry : getUriTemplateVariables(request).entrySet()) {
            String parameterName = entry.getKey();
            String value = entry.getValue();
            if (isFormModelAttribute(parameterName, modelPrefixName)) {
                mockRequest.setParameter(getNewParameterName(parameterName, modelPrefixName), value);
            }
        }

        for (Object parameterEntry : nativeRequest.getParameterMap().entrySet()) {
            @SuppressWarnings("unchecked")
            Map.Entry<String, String[]> entry = (Map.Entry<String, String[]>) parameterEntry;
            String parameterName = entry.getKey();
            String[] value = entry.getValue();
            if (isFormModelAttribute(parameterName, modelPrefixName)) {
                mockRequest.setParameter(getNewParameterName(parameterName, modelPrefixName), value);
            }
        }

        return mockRequest;
    }

    private String getNewParameterName(String parameterName, String modelPrefixName) {
        int modelPrefixNameLength = modelPrefixName.length();

        if (parameterName.charAt(modelPrefixNameLength) == '.') {
            return parameterName.substring(modelPrefixNameLength + 1);
        }

        if (parameterName.charAt(modelPrefixNameLength) == '[') {
            return parameterName.substring(modelPrefixNameLength);
        }
        throw new IllegalArgumentException("illegal request parameter, can not binding to @FormBean(" + modelPrefixName + ")");
    }

    private boolean isFormModelAttribute(String parameterName, String modelPrefixName) {
        int modelPrefixNameLength = modelPrefixName.length();

        if (parameterName.length() == modelPrefixNameLength) {
            return false;
        }

        if (!parameterName.startsWith(modelPrefixName)) {
            return false;
        }


        char ch = parameterName.charAt(modelPrefixNameLength);

        return ch == '.' || ch == '[';

    }

    protected void validateComponent(WebDataBinder binder, MethodParameter parameter) throws BindException {

        boolean validateParameter = validateParameter(parameter);
        Annotation[] annotations = binder.getTarget().getClass().getAnnotations();
        for (Annotation annot : annotations) {
            if (annot.annotationType().getSimpleName().startsWith("Valid") && validateParameter) {
                Object hints = AnnotationUtils.getValue(annot);
                binder.validate(hints instanceof Object[] ? (Object[]) hints : new Object[]{hints});
            }
        }

        if (binder.getBindingResult().hasErrors()) {
            if (isBindExceptionRequired(binder, parameter)) {
                throw new BindException(binder.getBindingResult());
            }
        }
    }

    private boolean validateParameter(MethodParameter parameter) {
        Annotation[] annotations = parameter.getParameterAnnotations();
        for (Annotation antn : annotations) {
            if (antn.annotationType().getSimpleName().startsWith("Valid")) {
                return true;
            }
        }

        return false;
    }

    /**
     * Validate the model attribute if applicable.
     * <p>The default implementation checks for {@code @javax.validation.Valid}.
     *
     * @param binder    the DataBinder to be used
     * @param parameter the method parameter
     */
    protected void validateIfApplicable(WebDataBinder binder, MethodParameter parameter) {
        Annotation[] annotations = parameter.getParameterAnnotations();
        for (Annotation annot : annotations) {
            if (annot.annotationType().getSimpleName().startsWith("Valid")) {
                Object hints = AnnotationUtils.getValue(annot);
                binder.validate(hints instanceof Object[] ? (Object[]) hints : new Object[]{hints});
            }
        }
    }

    /**
     * Whether to raise a {@link org.springframework.validation.BindException} on bind or validation errors.
     * The default implementation returns {@code true} if the next method
     * argument is not of type {@link org.springframework.validation.Errors}.
     *
     * @param binder    the data binder used to perform data binding
     * @param parameter the method argument
     */
    protected boolean isBindExceptionRequired(WebDataBinder binder, MethodParameter parameter) {
        int i = parameter.getParameterIndex();
        Class<?>[] paramTypes = parameter.getMethod().getParameterTypes();
        boolean hasBindingResult = (paramTypes.length > (i + 1) && Errors.class.isAssignableFrom(paramTypes[i + 1]));

        return !hasBindingResult;
    }


    private static class MultipartFileWrapper implements MultipartFile {
        private String name;
        private MultipartFile delegate;

        private MultipartFileWrapper(String name, MultipartFile delegate) {
            this.name = name;
            this.delegate = delegate;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getOriginalFilename() {
            return delegate.getOriginalFilename();
        }

        @Override
        public String getContentType() {
            return delegate.getContentType();
        }

        @Override
        public boolean isEmpty() {
            return delegate.isEmpty();
        }

        @Override
        public long getSize() {
            return delegate.getSize();
        }

        @Override
        public byte[] getBytes() throws IOException {
            return delegate.getBytes();
        }

        @Override
        public InputStream getInputStream() throws IOException {
            return delegate.getInputStream();
        }

        @Override
        public void transferTo(File dest) throws IOException, IllegalStateException {
            delegate.transferTo(dest);
        }
    }
}
