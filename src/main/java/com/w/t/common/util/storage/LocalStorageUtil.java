package com.w.t.common.util.storage;

import com.w.t.common.core.constant.LocalStorageConstant;

import java.util.HashMap;
import java.util.Map;

public class LocalStorageUtil {

    private static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal() {
        protected Map<String, Object> initialValue() {
            return new HashMap();
        }
    };

    private static void set(String key, Object value) {
        Map map = (Map) threadLocal.get();
        map.put(key, value);
    }

    private static <T> T get(String key) {
        Map map = (Map) threadLocal.get();
        return (T) map.get(key);
    }

    public static void clean(){
        threadLocal.remove();
    }

    public static void setCurrentUserId(Long userId) {
        set(LocalStorageConstant.CURRENT_USER, userId);
    }

    public static Long getCurrentUserId() {
        return get(LocalStorageConstant.CURRENT_USER);
    }


}