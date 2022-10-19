package com.wiredcraft.user.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author jeremy.zhang
 * @date 2022-10-19
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestHeaderUtils {
    private static final InheritableThreadLocal<String> USER_UUID_REPOSITORY = new InheritableThreadLocal<>();

    public static void set(String uuid) {
        USER_UUID_REPOSITORY.set(uuid);
    }

    public static String get() {
        return USER_UUID_REPOSITORY.get();
    }

    public static void clear() {
        USER_UUID_REPOSITORY.remove();
    }
}
