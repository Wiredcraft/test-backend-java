package com.zhangyongxin.demo.common;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther zhangyongxin
 * @date 2022/3/17 下午3:59
 */
public final class DemoThreadCache {

    private DemoThreadCache() {

    }

    private static final ThreadLocal<String> USER_TL = new ThreadLocal<>();
    private static final List<ThreadLocal> THREAD_LOCAL_LIST = new ArrayList<>();

    static {
        THREAD_LOCAL_LIST.add(USER_TL);
    }


    public static String getUserName() {
        return USER_TL.get();
    }

    public static void setUserName(String user) {
        USER_TL.set(user);
    }

    public static void clearAll() {
        for (ThreadLocal threadLocal : THREAD_LOCAL_LIST) {
            threadLocal.remove();
        }
    }
}
