
package com.springdemo.learningMVC.common.src.main.java.com.common.web.config;

/**
 * The com.common.web app constants utility class.
 *
 */
public abstract class WebAppConstants {
    private WebAppConstants() {}

    /** Defaults http url port. */
    public static final String DEFAULT_HTTP_PORT = "8080";

    /** Defaults https url port. */
    public static final String DEFAULT_HTTPS_PORT = "8443";

    public static final String NORMAL_HTTP_PORT = "80";

    public static final String NORMAL_HTTPS_PORT = "443";

    /** Defaults domain name for com.common.web app.*/
    public static final String DEFAULT_DOMAIN_NAME = "127.0.0.1";

    /** Defaults context path. */
    public static final String DEFAULT_CONTEXT_PATH = "/";

    public static final String MAIN_DOMAIN_KEY = "app.mainDomain";
    public static final String TOP_PRIVATE_DOMAIN_KEY = "app.topPrivateDomain";
    public static final String COOKIE_DOMAIN_KEY = "app.cookieDomain";
    public static final String STATIC_DOMAIN_KEY = "app.staticDomain";
    public static final String HTTP_PORT_KEY = "app.http.port";
    public static final String HTTP_URL_KEY = "app.http.url";
    public static final String HTTPS_PORT_KEY = "app.https.port";
    public static final String HTTPS_URL_KEY = "app.https.url";
    public static final String CONTEXT_PATH_KEY = "app.contextPath";
    public static final String IMAGE_URL_KEY = "app.imageBaseUrl";
    public static final String STYLES_URL_KEY = "app.stylesBaseUrl";
    public static final String SCRIPTS_URL_KEY = "app.scriptsBaseUrl";
    public static final String SKIN_IMAGES_URL_KEY = "app.skinImagesUrl";
    public static final String SKIN_STYLES_URL_KEY = "app.skinStylesUrl";
    public static final String THEME_KEY = "app.theme";
    public static final String THEME_NAME_KEY = "app.themeName";
    public static final String SKINS_NAME_KEY = "app.skinsName";
    public static final String FORCING_SSL_LOGIN = "app.forcedSSLLogin";
    public static final String DEV_MODE_KEY = "app.devMode";
}
