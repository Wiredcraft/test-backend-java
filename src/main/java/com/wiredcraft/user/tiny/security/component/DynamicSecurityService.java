package com.wiredcraft.user.tiny.security.component;

import org.springframework.security.access.ConfigAttribute;

import java.util.Map;

/**
 *
 * @author yuao
 * @since 2023-01-14
 */
public interface DynamicSecurityService {
    /**
     * 加载资源ANT通配符和资源对应MAP
     */
    Map<String, ConfigAttribute> loadDataSource();
}
