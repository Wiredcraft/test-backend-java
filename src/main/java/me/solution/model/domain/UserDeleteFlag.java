package me.solution.model.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * user delete flag enum
 *
 * @author davincix
 * @since 2023/5/22 02:37
 */
@Getter
@AllArgsConstructor
public enum UserDeleteFlag {
    /**
     * instance
     */
    NORMAL(0, "未删除"),
    DELETED(1, "已删除");

    private final int value;
    private final String desc;
}
