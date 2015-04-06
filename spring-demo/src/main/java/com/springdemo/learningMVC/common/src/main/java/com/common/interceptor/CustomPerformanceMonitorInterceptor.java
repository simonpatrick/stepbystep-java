
package com.springdemo.learningMVC.common.src.main.java.com.common.interceptor;

import com.google.common.base.Stopwatch;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;

import java.util.concurrent.TimeUnit;

/**
 * Simple AOP Alliance {@code MethodInterceptor} for performance monitoring.
 * This com.common.interceptor has no effect on the intercepted method call.
 * <p>
 * Uses a {@code Stopwatch} for the actual performance measuring.
 */
public class CustomPerformanceMonitorInterceptor
        extends CustomAbstractMonitoringInterceptor {

    private static final long serialVersionUID = -8688901939829545767L;

    /**
     * Create a new CustomPerformanceMonitorInterceptor with a static logger.
     */
    public CustomPerformanceMonitorInterceptor() {
        super();
    }

    /**
     * Create a new CustomPerformanceMonitorInterceptor with a dynamic or static logger,
     * according to the given flag.
     *
     * @param useDynamicLogger whether to use a dynamic logger or a static logger
     * @see #setUseDynamicLogger
     */
    public CustomPerformanceMonitorInterceptor(boolean useDynamicLogger) {
        setUseDynamicLogger(useDynamicLogger);
    }

    @Override
    protected Object invokeUnderTrace(MethodInvocation invocation, Log logger)
            throws Throwable {
        String name = createInvocationTraceName(invocation);
        Stopwatch stopwatch = Stopwatch.createStarted();
        try {
            return invocation.proceed();
        } finally {
            stopwatch.stop();
            long nanos = stopwatch.elapsed(TimeUnit.NANOSECONDS);
            double millis = (double) nanos / 1000000;
            logger.trace(String.format("%s runtime: %s ms, %s ns.", name, millis, nanos));
        }
    }
}
