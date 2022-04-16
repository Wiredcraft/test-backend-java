package com.test.backend.java;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.ConstVal;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

/**
 * code generator
 */
public class MybatisPlusCodeGenerator {

    @Test
    public void test() {
        String url = "jdbc:mysql://192.168.66.136:3306/system_user";
        String username = "root";
        String password = "Password321";
        String author = "Rock Jiang";

        String projectPath = System.getProperty("user.dir");
        String outputDir = projectPath + "" + "/src/main/java";
        System.out.println("outputDir: " + outputDir);

        String parent = "com.test.backend.java";
        String controller = "controller";
        String service = "service";
        String serviceImpl = "service.impl";
        String entity = "entity";
        String mapper = "mapper";
        String xml = "mapper.xml";
        String mapperXml = projectPath + "" + "/src/main/resources/mapper";

        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author(author) // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .outputDir(outputDir); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent(parent) // 设置父包名
//                            .moduleName("system") // 设置父包模块名
                            .controller(controller)
                            .service(service)
                            .serviceImpl(serviceImpl)
                            .entity(entity)
                            .mapper(mapper)
                            .xml(xml)
                            .pathInfo(Collections.singletonMap(OutputFile.xml, mapperXml)); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("friend") // 设置需要生成的表名
                            .addTablePrefix("system_") // 设置过滤表前缀
                            .addTableSuffix("_table") // 设置过滤表后缀
                            .controllerBuilder()
                            .serviceBuilder().convertServiceFileName(entityName -> entityName + ConstVal.SERVICE) // 去掉默认生成的以I为开头的名称
                            .entityBuilder().enableLombok() // 【实体】是否为lombok模型（默认 false）
                            .mapperBuilder();
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
