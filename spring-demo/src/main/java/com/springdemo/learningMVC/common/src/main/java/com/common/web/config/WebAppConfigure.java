
package com.springdemo.learningMVC.common.src.main.java.com.common.web.config;

import org.springframework.ui.context.Theme;
import com.common.web.config.support.DefaultWebAppConfigure;

/**
 * Web app 配置接口。
 *
 * @author fuchun
 * @version $Id: WebAppConfigure.java 290 2014-10-27 08:48:18Z fuchun $
 * @since 2.0
 */
public interface WebAppConfigure {

    /**
     * Returns default {@code WebAppConfigure} instance.
     */
    public static WebAppConfigure getConfigure() {
        return DefaultWebAppConfigure.getConfigure();
    }

    /**
     * Returns this App main domain. e.g. api.king4go.com
     */
    public String getMainDomain();

    /**
     * Returns this App top private domain. e.g. king4go.com
     */
    public String getTopPrivateDomain();

    /**
     * Returns this App cookie's domain. e.g. .king4go.com
     */
    public String getCookieDomain();

    /**
     * Returns this App static resource url prefix. e.g. img.king4go.com
     */
    public String getStaticDomain();

    /**
     * Returns this App url http port. Default: 80.
     */
    public String getHttpPort();

    /**
     * Returns this App url https (SSL) port. Default: 443.
     */
    public String getHttpsPort();

    /**
     * Returns this App url context path. Default: /
     */
    public String getContextPath();

    /**
     * Returns this App http url prefix.
     * This value is https:// + {@link #getMainDomain()} [+ ":{@link #getHttpPort()}"] +
     * {@link #getContextPath()}. No port if url http port is 80.
     * <p/>
     * NOTE: In dev mode, This value may be http://127.0.0.1:8080/{context_path}
     */
    public String getBaseHttpUrl();

    /**
     * Returns this App https url prefix.
     * This value is https:// + {@link #getMainDomain()} [+ ":{@link #getHttpsPort()}"] +
     * {@link #getContextPath()}. No port if url https port is 443.
     * <p/>
     * NOTE: In dev mode, This value may be https://127.0.0.1:8443/{context_path}
     */
    public String getBaseHttpsUrl();

    /**
     * Returns this App images url prefix. like http://static.domain.com/images
     */
    public String getImagesBaseUrl();

    /**
     * Returns this App styles url prefix. like http://static.domain.com/styles
     */
    public String getStylesBaseUrl();

    /**
     * Returns this App scripts url prefix. like http://static.domain.com/scripts
     */
    public String getScriptsBaseUrl();

    /**
     * Returns this App current skin image url prefix. like http://static.domain.com/images/skins/default
     */
    public String getSkinImagesUrl();

    /**
     * Returns this App current skin styles url prefix. like http://static.domain.com/styles/skins/default
     */
    public String getSkinStylesUrl();

    /**
     * Returns the whether be force login use SSL.
     */
    public boolean isForcedSSLLogin();

    /**
     * Returns this App UI theme.
     */
    public Theme getTheme();

    /**
     * Return {@code true} if this App is dev mode, otherwise {@code false}.
     * The application can output more useful debug information in dev mode.
     * Default value is {@code false}.
     */
    public boolean isDevMode();
}
