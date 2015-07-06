
package com.springdemo.learningMVC.common.src.main.java.com.common.web.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.DelegatingMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import com.common.web.config.WebAppConfigure;

import javax.annotation.Nonnull;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 自定义控制器的基本实现，子类可继承{@link #processViewPath(String, org.springframework.ui.ModelMap)}
 * 方法，自定义实现视图的路径处理（响应式设计）。
 *
 * @author fuchun
 * @version $Id: BaseControllerSupport.java 290 2014-10-27 08:48:18Z fuchun $
 * @since 2.0
 */
@Controller
public class BaseControllerSupport implements CleanAware {

    /**
     * Controller logger instance.
     */
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /** 默认的 404 视图。*/
    protected static final String DEFAULT_PAGE_NOT_FOUND_VIEW = "/404";
    /** 默认的 403 视图。*/
    protected static final String DEFAULT_NO_PERMISSION_VIEW = "/403";
    /** spring MVC 重定向视图字符串模板。*/
    protected static final String REDIRECT_PATTERN = "redirect:%s";

    /**
     * com.common.i18n message source.
     */
    protected ResourceBundleMessageSource messageSource;

    /**
     * Web app configure.
     */
    protected WebAppConfigure webAppConfigure;

    private String pageNotFoundView = DEFAULT_PAGE_NOT_FOUND_VIEW;
    private String noPermissionView = DEFAULT_NO_PERMISSION_VIEW;

    /**
     * 返回重定向跳转至指定的视图的命令。
     *
     * @param viewName 需要重定向的视图名称。
     */
    protected String redirect(String viewName) {
        return String.format(REDIRECT_PATTERN, viewName);
    }

    /**
     * 返回重定向的模型与视图对象。
     *
     * @param viewName 视图名称。
     * @return 重定向的模型与视图对象。
     */
    protected ModelAndView redirectView(String viewName) {
        return new ModelAndView(redirect(viewName));
    }

    /**
     * 跳转指定的视图。
     *
     * @param viewName 视图名称。
     * @param model 数据模型。
     * @return 要跳转的视图指令。
     */
    protected String directView(String viewName, ModelMap model) {
        if (model == null) {
            model = new ModelMap();
        }
        if (webAppConfigure != null) {
            model.put("appConfigure", webAppConfigure);
        }
        return processViewPath(viewName, model);
    }

    /**
     * 根据指定的视图名称和数据模型，返回 spring MVC {@link org.springframework.web.servlet.ModelAndView} 对象。
     *
     * @param viewName 视图名称。
     * @param model 数据模型。
     */
    protected ModelAndView view(String viewName, ModelMap model) {
        if (viewName == null) {
            throw new IllegalArgumentException("The given view name must not be null!");
        }
        if (model == null) {
            model = new ModelMap();
        }
        if (webAppConfigure != null) {
            model.put("appConfigure", webAppConfigure);
        }
        final String viewPath = processViewPath(viewName, model);
        return new ModelAndView(viewPath, model);
    }

    /**
     * 根据指定的数据模型，处理指定的视图名称。默认返回指定的视图名称，子类可重写该方法，
     * 根据请求计算不同的视图（响应式设计）等。
     *
     * @param viewName 视图名称。
     * @param model 数据模型。
     * @return 处理后的视图路径。
     */
    @Nonnull
    protected String processViewPath(@Nonnull String viewName, @Nonnull ModelMap model) {
        return viewName;
    }

    /**
     * 返回{@code 404} 页面跳转指令。
     */
    protected String pageNotFound() {
        return redirect(pageNotFoundView);
    }

    /**
     * 返回{@code 403} 页面跳转指令。
     */
    protected String noPermission() {
        return redirect(noPermissionView);
    }

    @Override
    public void cleanUp() {

    }

    @Autowired(required = false)
    public void setPageNotFoundView(String pageNotFoundView) {
        this.pageNotFoundView = pageNotFoundView;
    }

    @Autowired(required = false)
    public void setNoPermissionView(String noPermissionView) {
        this.noPermissionView = noPermissionView;
    }

    @Autowired(required = false)
    public void setWebAppConfigure(WebAppConfigure webAppConfigure) {
        this.webAppConfigure = webAppConfigure;
    }

    @Autowired(required = false)
    public void setMessageSource(MessageSource messageSource) {
        checkNotNull(messageSource, "messageSource");
        if (messageSource instanceof ResourceBundleMessageSource) {
            this.messageSource = (ResourceBundleMessageSource) messageSource;
        } else if (messageSource instanceof DelegatingMessageSource) {
            this.messageSource = (ResourceBundleMessageSource)
                    ((DelegatingMessageSource) messageSource).getParentMessageSource();
        }
        if (this.messageSource == null) {
            logger.warn("This controller haven't inject MessageSource: {}",
                    getClass().getSimpleName());
        }
    }
}
