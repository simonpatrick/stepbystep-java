package com.springdemo.learningMVC.common.src.main.java.com.common.interceptor;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.interceptor.AbstractTraceInterceptor;

import java.lang.reflect.*;

/**
 * Base class for monitoring interceptors, such as performance monitors.
 * Provides {@code prefix} and {@code suffix} properties
 * that help to classify/group performance monitoring results.
 * <p>
 * Subclasses should call the {@code createInvocationTraceName(MethodInvocation)}
 * method to create a name for the given trace that includes information about the
 * method invocation under trace along with the prefix and suffix added as appropriate.

 */
public abstract class CustomAbstractMonitoringInterceptor
        extends AbstractTraceInterceptor {

    private static final long serialVersionUID = -3737170848600286121L;

    /**
     * Global switch for monitor.
     */
    private boolean enabled = true;

    private String prefix = "";

    private String suffix = "";

    private boolean showParameters = false;

    private boolean logTargetClassInvocation = false;

    protected boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Set the text that will get appended to the trace data.
     * <p>Default is none.
     */
    public void setPrefix(String prefix) {
        this.prefix = (prefix != null ? prefix : "");
    }

    /**
     * Return the text that will get appended to the trace data.
     */
    protected String getPrefix() {
        return this.prefix;
    }

    /**
     * Set the text that will get prepended to the trace data.
     * <p>Default is none.
     */
    public void setSuffix(String suffix) {
        this.suffix = (suffix != null ? suffix : "");
    }

    /**
     * Return the text that will get prepended to the trace data.
     */
    protected String getSuffix() {
        return this.suffix;
    }

    /**
     * Set whether to log the invocation on the target class method showParameters.
     * <p>
     * <p />Default is "false", logging the invocation based on the target class method name.
     */
    public void setShowParameters(boolean showParameters) {
        this.showParameters = showParameters;
    }

    protected boolean isShowParameters() {
        return this.showParameters;
    }

    /**
     * Set whether to log the invocation on the target class, if applicable
     * (i.e. if the method is actually delegated to the target class).
     * <p />Default is "false", logging the invocation based on the proxy
     * interface/class name.
     */
    public void setLogTargetClassInvocation(boolean logTargetClassInvocation) {
        this.logTargetClassInvocation = logTargetClassInvocation;
    }

    /**
     * Create a {@code String} name for the given {@code MethodInvocation}
     * that can be used for trace/logging purposes. This name is made up of the
     * configured prefix, followed by the fully-qualified name of the method being
     * invoked, followed by the configured suffix.
     *
     * @see #setPrefix
     * @see #setSuffix
     */
    protected String createInvocationTraceName(MethodInvocation invocation) {
        StringBuilder sb = new StringBuilder(getPrefix());
        Method method = invocation.getMethod();
        Class<?> clazz = method.getDeclaringClass();
        if (this.logTargetClassInvocation && clazz.isInstance(invocation.getThis())) {
            clazz = invocation.getThis().getClass();
        }
        sb.append(clazz.getSimpleName());
        sb.append('.').append(method.getName());
        if (isShowParameters()) {
            sb.append("(");
            Parameter[] parameters = method.getParameters();
            if (parameters.length != 0) {
                for (Parameter p : parameters) {
                    if (!(p.getParameterizedType() instanceof ParameterizedType)) {
                        sb.append(p.getType().getSimpleName()).append(",");
                        continue;
                    }
                    ParameterizedType pType = (ParameterizedType) p.getParameterizedType();
                    if (pType.getActualTypeArguments() != null &&
                            pType.getActualTypeArguments().length > 0) {
                        sb.append(p.getType().getSimpleName()).append("<");
                        Type[] types = pType.getActualTypeArguments();
                        for (Type t : types) {
                            if (t instanceof Class) {
                                sb.append(((Class<?>) t).getSimpleName()).append(",");
                            } else {
                                if (t instanceof TypeVariable) {
                                    sb.append(((TypeVariable<?>) t).getName()).append(",");
                                }
                            }
                        }
                        sb.deleteCharAt(sb.length() - 1);
                        sb.append(">,");
                    } else {
                        sb.append(p.getType().getSimpleName()).append(",");
                    }
                }
                sb.deleteCharAt(sb.length() - 1);
            }
            sb.append(")");
        }
        sb.append(getSuffix());
        return sb.toString();
    }
}
