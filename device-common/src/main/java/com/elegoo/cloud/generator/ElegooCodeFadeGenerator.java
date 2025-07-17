package com.elegoo.cloud.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import java.util.HashMap;
import java.util.Map;

public class ElegooCodeFadeGenerator {

    private static String[] tableNames = new String[]{"rino_agora_license"};

    private String databaseName = "rino_device";
    private String moduleFacadeName = "provider.facade.product";//模块名
    private String url = "jdbc:mysql://prod-qc-db.ct0ckyow8o4q.us-east-2.rds.amazonaws.com:3306/"+databaseName+"?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai";
    private String userName = "readonly";
    private String password = "Ip9uccm9q6xuhPQcC3OB";
    private String author = "yangyi";//作者
    private String outPutProviderFacadeDir = System.getProperty("user.dir") + "/rinoiot-provider-facade/rinoiot-provider-facade-product";
    private String classFacadeDir = outPutProviderFacadeDir + "/src/main/java";//class目录
    private String rootPackage = "com.rinoiot";//根包目录
    private String serviceNameConstants = "ServiceNameConstants.PRODUCT_SERVICE";
    private String errorCode = "CommonErrorCodes.PRODUCT_SERVICE_UNAVAILABLE";


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
                            .outputDir(classFacadeDir); // 指定输出目录
                }).packageConfig(builder -> {
                    builder.parent(rootPackage) // 设置父包名
                            .moduleName(moduleFacadeName) // 设置父包模块名
                    ;
                }).injectionConfig(consumer -> {
                    //自定义参数
                    Map<String, Object> customMap = new HashMap<>();
                    String basePackage = rootPackage+"."+moduleFacadeName;
                    customMap.put("dtoPackage",basePackage+".dto");
                    customMap.put("voPackage",basePackage+".vo");
                    customMap.put("queryPackage",basePackage+".query");
                    customMap.put("module",moduleFacadeName);
                    customMap.put("facadePackage",basePackage);
                    customMap.put("facadeFallbackPackage",basePackage+".hystrix");
                    customMap.put("ServiceNameConstants",serviceNameConstants);
                    customMap.put("errorCode",errorCode);
                    consumer.customMap(customMap);

                    // 自定义模板
                    Map<String, String> customFile = new HashMap<>();
                    customFile.put("DTO.java", "/templates/entityDTO.java.ftl");
                    customFile.put("VO.java", "/templates/entityVO.java.ftl");
                    customFile.put("Query.java", "/templates/entityQuery.java.ftl");
                    customFile.put("Facade.java","/templates/v2/entityFacadeV2.java.ftl");
                    customFile.put("FacadeFallback.java","/templates/v2/entityFacadeFallbackV2.java.ftl");
                    consumer.customFile(customFile);
                }).strategyConfig(builder -> {
                    builder.addInclude(tableName) // 设置需要生成的表名
                            .addTablePrefix("prodigytec_") // 设置过滤表前缀
                            .entityBuilder()
                            .disableSerialVersionUID()
                            .enableLombok()
                            .build()
                    ;
                }).templateEngine(new EnhanceFreemarkerTemplateFacadeEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }

    public static void main(String[] args) {
        ElegooCodeFadeGenerator generator = new ElegooCodeFadeGenerator();
        for (String tableName : tableNames) {
            generator.execute(tableName);
        }
    }
}
