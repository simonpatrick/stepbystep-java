package com.springdemo.learningMVC.common.src.main.java.com.common.web.config.support;

import com.google.common.base.Joiner;
import com.google.common.base.MoreObjects;
import com.google.common.net.InternetDomainName;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.ui.context.Theme;
import com.common.web.config.WebAppConfigure;

import javax.annotation.Nonnull;
import java.io.Serializable;

import static com.common.web.config.WebAppConstants.*;


/**
 * The default implementation of the {@code WebAppConfigure}.
 */
public class DefaultWebAppConfigure implements
        WebAppConfigure, InitializingBean, Serializable {

    protected static final String DEFAULT_SKINS_NAME = "skins";

    private static final DefaultWebAppConfigure CONFIG = new DefaultWebAppConfigure();

    private static final long serialVersionUID = 6242808943394462803L;

    public static DefaultWebAppConfigure getConfigure() {
        return CONFIG;
    }

    private String mainDomain = DEFAULT_DOMAIN_NAME;
    private String topPrivateDomain;
    private String cookieDomain;
    private String staticDomain = DEFAULT_DOMAIN_NAME;
    private String httpPort;
    private String httpsPort;
    private String baseHttpUrl;
    private String baseHttpsUrl;
    private String contextPath = "/";

    private String skinsName = DEFAULT_SKINS_NAME;
    private String stylesBaseUrl;
    private String scriptsBaseUrl;
    private String imagesBaseUrl;
    private String stylesPath = "/styles";
    private String scriptsPath = "/scripts";
    private String imagesPath = "/images";

    private String skinImagesUrl;
    private String skinStylesUrl;

    private boolean forcedSSLLogin = false;
    private Theme theme;

    private boolean devMode = false;

    private DefaultWebAppConfigure() {
        super();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        initDomain();
    }

    private void initDomain() {
        String mainDomain;
        if ((mainDomain = getMainDomain()) == null) {
            return;
        }

        String topPrivateDomain, cookieDomain;
        if (InternetDomainName.isValid(mainDomain)) {
            InternetDomainName mainDomainName = InternetDomainName.from(mainDomain);
            topPrivateDomain = mainDomainName.topPrivateDomain().toString();
            cookieDomain = "." + topPrivateDomain;
        } else { // IPv4 or IPv6
            topPrivateDomain = mainDomain;
            cookieDomain = mainDomain;
        }
        if (getTopPrivateDomain() == null) {
            setTopPrivateDomain(topPrivateDomain);
        }
        if (getCookieDomain() == null) {
            setCookieDomain(cookieDomain);
        }
    }

    @Override
    public String getMainDomain() {
        return mainDomain;
    }

    /**
     * 配置主域名（可选）。默认值：{@code 127.0.0.1}。
     *
     * @param mainDomain 主域名。
     */
    public void setMainDomain(String mainDomain) {
        this.mainDomain = mainDomain;
    }

    @Override
    public String getTopPrivateDomain() {
        return topPrivateDomain;
    }

    /**
     * 配置顶级私有域名（可选）。
     * <p/>
     * 如果没有配置此属性，则该属性值会自动根据配置的{@link #getMainDomain()} 进行计算。
     *
     * @param topPrivateDomain 顶级私有域名。
     */
    public void setTopPrivateDomain(String topPrivateDomain) {
        this.topPrivateDomain = topPrivateDomain;
    }

    @Override
    public String getCookieDomain() {
        return cookieDomain;
    }

    /**
     * 配置cookie使用的域范围（可选）。
     * <p />
     * 如果没有配置此属性，则该属性值会自动根据{@link #getTopPrivateDomain()} 进行计算。
     *
     * @param cookieDomain Cookie 域。
     */
    public void setCookieDomain(String cookieDomain) {
        this.cookieDomain = cookieDomain;
    }

    @Override
    public String getStaticDomain() {
        return staticDomain;
    }

    /**
     * 配置静态资源域名（可选）。默认与主域名相同。
     *
     * @param staticDomain 静态资源域名。
     */
    public void setStaticDomain(String staticDomain) {
        this.staticDomain = staticDomain;
    }

    @Override
    public String getHttpPort() {
        return httpPort;
    }

    /**
     * 配置 http 服务的端口号（可选）。默认值：{@code 8080}。
     *
     * @param httpPort http 服务的端口号。
     */
    public void setHttpPort(String httpPort) {
        this.httpPort = httpPort;
    }

    @Override
    public String getHttpsPort() {
        return httpsPort;
    }

    /**
     * 配置 https 服务的端口号（可选）。默认值：{@code 8443}。
     *
     * @param httpsPort https 服务的端口号。
     */
    public void setHttpsPort(String httpsPort) {
        this.httpsPort = httpsPort;
    }

    @Override
    public String getContextPath() {
        return contextPath;
    }

    /**
     * 配置应用的上下文路径（可选）。默认值：{@code "/"}。
     *
     * @param contextPath 应用的上下文路径。
     */
    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    @Override
    public String getBaseHttpUrl() {
        if (baseHttpUrl == null) {
            baseHttpUrl = getBaseUrl("http", getHttpPort(), DEFAULT_HTTP_PORT);
        }
        return baseHttpUrl;
    }

    /**
     * 配置应用的基本http的URL（可选）。<b>建议不要配置，自动计算</b>
     *
     * @param baseHttpUrl 基本http的URL。
     */
    public void setBaseHttpUrl(String baseHttpUrl) {
        this.baseHttpUrl = baseHttpUrl;
    }

    @Override
    public String getBaseHttpsUrl() {
        if (baseHttpsUrl == null) {
            baseHttpsUrl = getBaseUrl("https", getHttpsPort(), DEFAULT_HTTPS_PORT);
        }
        return baseHttpsUrl;
    }

    /**
     * 配置应用的基本https的URL（可选）。<b>建议不要配置，自动计算</b>
     *
     * @param baseHttpsUrl 基本https的URL。
     */
    public void setBaseHttpsUrl(String baseHttpsUrl) {
        this.baseHttpsUrl = baseHttpsUrl;
    }

    protected String getBaseUrl(
            String scheme, String port, @Nonnull String defPort) {
        String p = MoreObjects.firstNonNull(port, defPort);
        if (NORMAL_HTTP_PORT.equals(p)) {
            p = "";
        }
        return Joiner.on("").join(scheme, "://", getMainDomain(),
                p, getContextPath());
    }

    @Override
    public String getImagesBaseUrl() {
        if (imagesBaseUrl == null) {
            imagesBaseUrl = getStaticBaseUrl(imagesPath);
        }
        return imagesBaseUrl;
    }

    /**
     * 配置图片资源基本URL（可选）。
     * <p />
     * 不配置此属性时，根据{@link #getStaticDomain()} 自动计算。
     *
     * @param imagesBaseUrl 图片资源基本URL。
     */
    public void setImagesBaseUrl(String imagesBaseUrl) {
        this.imagesBaseUrl = imagesBaseUrl;
    }

    @Override
    public String getStylesBaseUrl() {
        if (stylesBaseUrl == null) {
            stylesBaseUrl = getStaticBaseUrl(stylesPath);
        }
        return stylesBaseUrl;
    }

    /**
     * 配置样式资源基本URL（可选）。
     * <p />
     * 不配置此属性时，根据{@link #getStaticDomain()} 自动计算。
     *
     * @param stylesBaseUrl 图片样式基本URL。
     */
    public void setStylesBaseUrl(String stylesBaseUrl) {
        this.stylesBaseUrl = stylesBaseUrl;
    }

    @Override
    public String getScriptsBaseUrl() {
        if (scriptsBaseUrl == null) {
            scriptsBaseUrl = getStaticBaseUrl(scriptsPath);
        }
        return scriptsBaseUrl;
    }

    /**
     * 配置脚本资源基本URL（可选）。
     * <p />
     * 不配置此属性时，根据{@link #getStaticDomain()} 自动计算。
     *
     * @param scriptsBaseUrl 图片脚本基本URL。
     */
    public void setScriptsBaseUrl(String scriptsBaseUrl) {
        this.scriptsBaseUrl = scriptsBaseUrl;
    }

    protected String getStaticBaseUrl(@Nonnull String staticPath) {
        String path = staticPath;
        if (getContextPath().equals("/")) {
            if (staticPath.startsWith("/")) {
                path = staticPath.substring(1);
            }
        }
        String p = (NORMAL_HTTP_PORT.equals(getHttpPort()) ? "" : ":" + getHttpPort());
        return Joiner.on("").join("http://",
                getStaticDomain(), p, getContextPath(), path);
    }

    @Override
    public String getSkinImagesUrl() {
        if (skinImagesUrl == null) {
            skinImagesUrl = Joiner.on("/").join(
                    getImagesBaseUrl(), skinsName, getTheme().getName());
        }
        return skinImagesUrl;
    }

    /**
     * 配置图片皮肤基本URL（可选）。
     *
     * @param skinImagesUrl 图片皮肤基本URL。
     */
    public void setSkinImagesUrl(String skinImagesUrl) {
        this.skinImagesUrl = skinImagesUrl;
    }

    @Override
    public String getSkinStylesUrl() {
        if (skinStylesUrl == null) {
            skinStylesUrl = Joiner.on("/").join(
                    getStylesBaseUrl(), skinsName, getTheme().getName());
        }
        return skinStylesUrl;
    }

    /**
     * 配置皮肤样式基本URL（可选）。
     *
     * @param skinStylesUrl 样式皮肤基本URL。
     */
    public void setSkinStylesUrl(String skinStylesUrl) {
        this.skinStylesUrl = skinStylesUrl;
    }

    @Override
    public boolean isForcedSSLLogin() {
        return forcedSSLLogin;
    }

    /**
     * 配置是否强制使用{@code SSL} 登录（可选），默认值：{@code false}。
     *
     * @param forcedSSLLogin 是否强制使用{@code SSL} 登录。
     */
    public void setForcedSSLLogin(boolean forcedSSLLogin) {
        this.forcedSSLLogin = forcedSSLLogin;
    }

    @Override
    public Theme getTheme() {
        return theme;
    }

    /**
     * 配置主题信息。
     *
     * @param theme 主题信息。
     */
    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    @Override
    public boolean isDevMode() {
        return devMode;
    }

    /**
     * 配置应用的开发模式（可选）。默认值：{@code true}。
     *
     * @param devMode 应用的开发模式。
     */
    public void setDevMode(boolean devMode) {
        this.devMode = devMode;
    }

    /**
     * 配置样式资源的上下文路径（可选），默认值：{@code /styles}
     *
     * @param stylesPath 样式资源的上下文路径。
     */
    public void setStylesPath(String stylesPath) {
        this.stylesPath = stylesPath;
    }

    /**
     * 配置JS资源的上下文路径（可选），默认值：{@code /scripts}。
     *
     * @param scriptsPath JS资源的上下文路径。
     */
    public void setScriptsPath(String scriptsPath) {
        this.scriptsPath = scriptsPath;
    }

    /**
     * 配置图片资源的上下文路径（可选），默认值：{@code /images}。
     *
     * @param imagesPath 图片资源的上下文路径。
     */
    public void setImagesPath(String imagesPath) {
        this.imagesPath = imagesPath;
    }

    private Object readResolve() {
        return CONFIG;
    }
}
