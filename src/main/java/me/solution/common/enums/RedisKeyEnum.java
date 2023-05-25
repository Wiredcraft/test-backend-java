package me.solution.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.concurrent.TimeUnit;

/**
 * enum for default result
 *
 * @author davincix
 * @since 2023/5/22 11:25
 */
@Getter
@AllArgsConstructor
public enum RedisKeyEnum {

    /**
     * user login token
     */
    LOGIN_TOKEN("user:login:%s", 48, TimeUnit.HOURS);

    /**
     * key pattern
     */
    private final String keyPattern;

    /**
     * time to live
     */
    private final Integer ttl;

    /**
     * time unit
     */
    private final TimeUnit timeUnit;

    public String formatKey(Object... args) {
        return String.format(this.keyPattern, args);
    }
}
