package com.wiredcraft.testbackend.util;

import java.text.MessageFormat;

public class CacheKeyUtil {

    /**
     * cache a day
     */
    public static final long ONE_DAY_MILLIS = 1000L * 60L * 60L * 24L;

    /**
     * cache three days
     */
    public static final long THREE_DAY_MILLIS = ONE_DAY_MILLIS * 3L;

    /**
     * cache a week
     */
    public static final long ONE_WEEK_MILLIS = ONE_DAY_MILLIS * 7L;

    /**
     * cache 30 days
     */
    public static final long ONE_MONTH_MILLIS = ONE_DAY_MILLIS * 30L;

    /**
     * user info key
     */
    public static final String getUserInfoKey(Long userId) {
        return MessageFormat.format(CacheKeyEnum.KEY_USER_INFO.getKey(), userId);
    }

    /**
     * get fan ids key
     */
    public static final String getUserFanIdsKey(Long userId) {
        return MessageFormat.format(CacheKeyEnum.KEY_USER_FANS_IDS.getKey(), userId);
    }

    /**
     * get follow ids key
     */
    public static final String getUserFollowIdsKey(Long userId) {
        return MessageFormat.format(CacheKeyEnum.KEY_USER_FOLLOW_IDS.getKey(), userId);
    }

}
