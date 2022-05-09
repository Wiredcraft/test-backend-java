package io.github.jerrychin.testbackendjava.util;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class CoordinateTest {

    @Test
    public void findDistanceBetweenCoordinatesShouldCloseToExpectedDistance() {
        // coordinates from https://jingweidu.bmcx.com/
        // expected distance from https://map.baidu.com

        // 北京安贞桥
        Coordinate coordinate1 = new Coordinate(new BigDecimal("116.4075456"),
                new BigDecimal("39.968842"));

        // 廊坊北站
        Coordinate coordinate2 = new Coordinate(new BigDecimal("116.706837"),
                new BigDecimal("39.5114509"));

        // assert distance, allow 2 KM offset.
        assertThat(Coordinate.dBetween(coordinate1, coordinate2))
                .isCloseTo(56_800L, Offset.offset(2000L));
    }
}