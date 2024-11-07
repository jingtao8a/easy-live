package org.jingtao8a.constants;

public class Constants {
    //theme 1
    public static final Integer ONE = 1;
    //theme 0
    public static final Integer ZERO = 0;

    public static final Integer LENGTH_10 = 10;

    public static final Integer LENGTH_5 = 5;

    public static final String REGEX_PASSWORD = "^(?=.*\\d)(?=.*[a-zA-Z])[\\da-zA-Z~!@#$%^&*_]{8,18}$";

    public static final Integer REDIS_KEY_EXPIRES_ONE_MINUTE = 1000 * 60;

    public static final Integer REDIS_KEY_EXPIRES_TEN_MINUTE = 10 * REDIS_KEY_EXPIRES_ONE_MINUTE;

    public static final Integer REDIS_KEY_EXPIRES_ONE_HOUR = 60 * REDIS_KEY_EXPIRES_ONE_MINUTE;

    public static final Integer REDIS_KEY_EXPIRES_ONE_DAY = 24 * REDIS_KEY_EXPIRES_ONE_HOUR;

    public static final String REDIS_KEY_PREFIX = "easylive:";

    public static final String REDIS_KEY_CHECK_CODE = REDIS_KEY_PREFIX + "checkCode:";

    public static final String REDIS_KEY_TOKEN_WEB = REDIS_KEY_PREFIX + "token:web:";

    public static final String TOKEN_WEB = REDIS_KEY_PREFIX + "token:web:";
}
