package me.solution;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * boot for the app
 *
 * @author davincix
 * @since 2023/5/20 12:46
 */
@SpringBootApplication
@MapperScan("me.solution.mapper")
public class TestSolutionApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestSolutionApplication.class, args);
    }
}
