
package com.springdemo.learningMVC.common.src.main.java.com.common.base;

public enum OSType implements EnumValue<Number> {

    OTHER(0, "Other", false),

    WINDOWS(1, "Windows", false),

    LINUX(2, "Linux", false),

    MAC_OS_X(3, "Mac OS X", false),

    ANDROID(4, "Android", true),

    IOS(5, "iOS", true),

    WINDOWS_PHONE(6, "Windows Phone", true),

    FIREFOX_OS(7, "Firefox OS", true);

    final short code;
    final String name;
    final boolean isMobile;

    private OSType(int code, String name, boolean isMobile) {
        this.code = (short) code;
        this.name = name;
        this.isMobile = isMobile;
    }

    /**
     * Returns code of this os type.
     */
    public short getCode() {
        return code;
    }

    /**
     * Returns name of this os type.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns {@code true} if this os type is a mobile device.
     */
    public boolean isMobile() {
        return isMobile;
    }

    @Override
    public Number getValue() {
        return (int) code;
    }

    /**
     * Returns a {@code OSType} with the specified code, or {@link #OTHER} if
     * the code not found.
     *
     * @param code OS code.
     * @param <T> OS code generate type.
     * @return a {@code OSType} with the specified code, or {@link #OTHER} if
     * the code not found.
     */
    public static <T extends Number> OSType of(T code) {
        return of(code, OTHER);
    }

    /**
     * Returns a {@code OSType} with the specified code, or {@code def} value if
     * the code not found.
     *
     * @param code OS code.
     * @param def  default OS value.
     * @param <T>  OS code generate type.
     * @return a {@code OSType} with the specified code, or {@code def} value if
     *         the code not found.
     */
    public static <T extends Number> OSType of(T code, OSType def) {
        if (code == null || code.intValue() < 0) {
            return def;
        }
        for (OSType osType : values()) {
            if (osType.getCode() == code.shortValue()) {
                return osType;
            }
        }
        return def;
    }

    /**
     * Returns a {@code OSType} with specified name, or {@link #OTHER}
     * if the name not found.
     *
     * @param name OS name.
     * @return a {@code OSType} with specified name, or {@link #OTHER}
     * if the name not found.
     */
    public static OSType of(String name) {
        return of(name, OTHER);
    }

    /**
     * Returns a {@code OSType} with specified name, or {@code def} value
     * if the name not found.
     *
     * @param name OS name.
     * @param def default OS.
     * @return a {@code OSType} with specified name, or {@code def} value
     * if the name not found.
     */
    public static OSType of(String name, OSType def) {
        if (name == null || (name = name.trim()).length() == 0) {
            return def;
        }
        for (OSType osType : values()) {
            if (osType.getName().equalsIgnoreCase(name) ||
                    osType.name().equalsIgnoreCase(name)) {
                return osType;
            }
        }
        return def;
    }
}