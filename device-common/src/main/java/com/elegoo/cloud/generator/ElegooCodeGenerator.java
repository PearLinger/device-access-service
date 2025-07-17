package com.elegoo.cloud.generator;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.fill.Column;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ElegooCodeGenerator {

    private static String[] tableNames = new String[]{"prodigytec_one_time_timed_task_manage"};

    private String databaseName = "rino_product";
    private String moduleName = "provider.product";//模块名
    private Boolean isFacade = true;//是否生成facade接口
    private Boolean isCache = false;//是否生成缓存

    private String url = "jdbc:mysql://10.0.10.166:3306/"+databaseName+"?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai";
    private String userName = "root";
    private String password = "E6Bnj_BW0V9EpqfU";

    private String author = "yangyi";//作者
    private String outPutDir = System.getProperty("user.dir") + "/rinoiot-provider/rinoiot-provider-product";
    private String classDir = outPutDir + "/src/main/java";//class目录
    private String mapperDir = outPutDir + "/src/main/resources/mapper";//mapper文件目录

    private String rootPackage = "com.rinoiot";//根包目录


    private void execute(String tableName) {
//        FastAutoGenerator.create(new DataSourceConfig.Builder("jdbc:mysql://127.0.0.1:3306/mybatis-plus","root","123456")
//                .dbQuery(new MySqlQuery())
//                .schema("mybatis-plus")
//                .typeConvert(new MySqlTypeConvert())
//                .keyWordsHandler(new MySqlKeyWordsHandler()));
        FastAutoGenerator.create(url, userName, password)
                .globalConfig(builder -> {
                    builder.author(author) // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件,感觉不好使,应该用其他方式,懒得看了
                            .disableOpenDir()//不打开文件夹
                            .dateType(DateType.ONLY_DATE)//全局日期使用util下Date
                            .outputDir(classDir); // 指定输出目录
                }).packageConfig(builder -> {
                    builder.parent(rootPackage) // 设置父包名
                            .moduleName(moduleName) // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, mapperDir)) // 设置mapperXml生成路径
                    ;
                }).templateConfig(builder -> {
                    if(isCache){
                        builder.service("/templates/serviceCache.java")
                                .serviceImpl("/templates/serviceCacheImpl.java");
                    }else{
                        builder.service("/templates/service.java")
                                .serviceImpl("/templates/serviceImpl.java");
                    }
                }).injectionConfig(consumer -> {
                    //自定义参数
                    Map<String, Object> customMap = new HashMap<>();
                    String basePackage = rootPackage+"."+moduleName;
                    customMap.put("dtoPackage",basePackage+".dto");
                    customMap.put("voPackage",basePackage+".vo");
                    customMap.put("queryPackage",basePackage+".query");
                    customMap.put("module",moduleName);
                    customMap.put("facadeServicePackage",basePackage+".facade.service");
                    consumer.customMap(customMap);

                    // 自定义模板
                    Map<String, String> customFile = new HashMap<>();
                    customFile.put("DTO.java", "/templates/entityDTO.java.ftl");
                    customFile.put("VO.java", "/templates/entityVO.java.ftl");
                    customFile.put("Query.java", "/templates/entityQuery.java.ftl");
                    if(isFacade){
                        customFile.put("Facade.java", "/templates/entityFacade.java.ftl");
                        customFile.put("FacadeFallback.java", "/templates/entityFacadeFallback.java.ftl");
                        customFile.put("FacadeService.java", "/templates/entityFacadeService.java.ftl");
                    }
                    consumer.customFile(customFile);
                }).strategyConfig(builder -> {
                    builder.addInclude(tableName) // 设置需要生成的表名
                            .addTablePrefix("rino_") // 设置过滤表前缀
                            .entityBuilder()
                            .disableSerialVersionUID()
                            .enableLombok()
                            .logicDeleteColumnName("deleted")
                            .addTableFills(new Column("create_by", FieldFill.INSERT))
                            .addTableFills(new Column("update_by", FieldFill.UPDATE))
                            .addTableFills(new Column("create_time", FieldFill.INSERT))
                            .addTableFills(new Column("update_time", FieldFill.UPDATE))
                            .idType(IdType.ASSIGN_UUID)
                            .build()
                            .serviceBuilder()
                            .formatServiceFileName("%sService")
                            .build()
                            .controllerBuilder()
                            .enableRestStyle()
                    ;
                }).templateEngine(new EnhanceFreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }

    public static void main(String[] args) {
        ElegooCodeGenerator generator = new ElegooCodeGenerator();
        for (String tableName : tableNames) {
            generator.execute(tableName);
        }
    }
}
