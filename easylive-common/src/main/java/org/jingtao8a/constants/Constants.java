package org.jingtao8a.constants;

public class Constants {
    public static final String FILE_TEMP = "temp/";

    public static final String FILE_VIDEO = "video/";

    public static final String FILE_COVER = "cover/";

    public static final String FILE_FOLDER = "file/";
    //theme 1
    public static final Integer ONE = 1;
    //theme 0
    public static final Integer ZERO = 0;

    public static final Integer LENGTH_30 = 30;

    public static final Integer LENGTH_20 = 20;

    public static final Integer LENGTH_15 = 15;

    public static final Integer LENGTH_10 = 10;

    public static final Integer LENGTH_5 = 5;

    public static final String REGEX_PASSWORD = "^(?=.*\\d)(?=.*[a-zA-Z])[\\da-zA-Z~!@#$%^&*_]{8,18}$";

    public static final Integer REDIS_KEY_EXPIRES_ONE_SECOND = 1000;

    public static final Integer REDIS_KEY_EXPIRES_ONE_MINUTE = 1000 * 60;

    public static final Integer REDIS_KEY_EXPIRES_TEN_MINUTE = 10 * REDIS_KEY_EXPIRES_ONE_MINUTE;

    public static final Integer REDIS_KEY_EXPIRES_ONE_HOUR = 60 * REDIS_KEY_EXPIRES_ONE_MINUTE;

    public static final Integer REDIS_KEY_EXPIRES_ONE_DAY = 24 * REDIS_KEY_EXPIRES_ONE_HOUR;

    public static final String REDIS_KEY_PREFIX = "easylive:";

    public static final String REDIS_KEY_CHECK_CODE = REDIS_KEY_PREFIX + "checkCode:";

    public static final String REDIS_KEY_TOKEN_WEB = REDIS_KEY_PREFIX + "token:web:";

    public static final String REDIS_KEY_TOKEN_ADMIN = REDIS_KEY_PREFIX + "token:admin:";

    public static final String REDIS_KEY_CATEGORY_LIST = REDIS_KEY_PREFIX + "category:list:";

    public static final String REDIS_KEY_UPLOADING_FILE = REDIS_KEY_PREFIX + "uploading:";

    public static final String REDIS_KEY_SYS_SETTING = REDIS_KEY_PREFIX + "sysSetting:";

    public static final String REDIS_KEY_FILE_DEL = REDIS_KEY_PREFIX + "file:list:del";

    public static final String REDIS_KEY_QUEUE_TRANSFER = REDIS_KEY_PREFIX + "queue:transfer";

    public static final String REDIS_KEY_VIDEO_PLAY_COUNT_ONLINE_PREFIX = REDIS_KEY_PREFIX + "video:play:online:";

    public static final String REDIS_KEY_VIDEO_PLAY_COUNT_ONLINE = REDIS_KEY_VIDEO_PLAY_COUNT_ONLINE_PREFIX + "count:%s";

    public static final String REDIS_KEY_VIDEO_PLAY_COUNT_USER_PREFIX = "user:";

    public static final String REDIS_KEY_VIDEO_PLAY_COUNT_USER = REDIS_KEY_VIDEO_PLAY_COUNT_ONLINE_PREFIX + REDIS_KEY_VIDEO_PLAY_COUNT_USER_PREFIX + "%s:%s";

    public static final String TOKEN_WEB = "token";

    public static final String TOKEN_ADMIN = "adminToken";

    public static final String IMAGE_THUMBNAIL_SUFFIX = "_thumbnail.jpg";

    public static final Integer MB_SIZE = 1024 * 1024;

    public static final String TEMP_VIDEO_NAME = "/temp.mp4";

    public static final String VIDEO_CODE_HEVC = "hevc";

    public static final String VIDEO_CODE_TEMP_FILE_SUFFIX = "_temp";

    public static final String TS_NAME = "index.ts";

    public static final String M3U8_NAME = "index.m3u8";
}
