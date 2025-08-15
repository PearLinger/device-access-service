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

public class ElegooCodeGeneratorV2 {

  //表名称
  private static String[] tableNames = new String[]{"t_serial_no_info"};
  //路由名称
  private String controllerPathName = "product-type-info";
  //数据库名称
  private String databaseName = "elegoo";
  //模块名称
  private String moduleName = "device";
  //是否生成facade接口
  private Boolean isFacade = true;
  private Boolean isCache = false;//是否生成缓存
  //数据库url
  private String url = "jdbc:postgresql://192.168.3.25:15432/elegoo-cloud-device-management?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&allowMultiQueries=true&useSSL=false&allowPublicKeyRetrieval=true";
  private String userName = "user_mmijbx";
  private String password = "password_ifrepx";

  private String author = "yangyi";//作者
  //文件路径
  private String outPutDir = System.getProperty("user.dir") + "/device-access-biz";
  //class目录
  private String classDir = outPutDir + "/src/main/java";
  //mapper文件目录
  private String mapperDir = outPutDir + "/src/main/resources/mapper";
  //根包目录
  private String rootPackage = "com.elegoo.cloud";

  //facade目录
  private String outPutProviderFacadeDir = System.getProperty("user.dir") + "/device-access-api";
  private String classFacadeDir = outPutProviderFacadeDir + "/src/main/java";//class目录
  //facade模块名
  private String moduleFacadeName = "device.api";//模块名
  //feign value名称
  private String serviceNameConstants = "ServiceNameConstants.PRODUCT_SERVICE";
  //FacadeFallback errorCode
  private String errorCode = "CommonErrorCodes.PRODUCT_SERVICE_UNAVAILABLE";


  private void execute(String tableName) {
    FastAutoGenerator.create(url, userName, password)
        .globalConfig(builder -> {
          builder.author(author) // 设置作者
              .fileOverride() // 覆盖已生成文件
              .disableOpenDir()//不打开文件夹
              .dateType(DateType.ONLY_DATE)//全局日期使用util下Date
              .outputDir(classDir); // 指定输出目录
        }).packageConfig(builder -> {
          builder.parent(rootPackage) // 设置父包名
              .moduleName(moduleName) // 设置父包模块名
              .pathInfo(Collections.singletonMap(OutputFile.xml, mapperDir)) // 设置mapperXml生成路径
          ;
        }).templateConfig(builder -> {
          builder.controller("").disable();
          builder.mapper("");
          builder.xml("/elegootemplates/mapperV2.xml");
          builder.entity("").disable();
        }).injectionConfig(consumer -> {
          //自定义参数
          Map<String, Object> customMap = new HashMap<>();
          String basePackage = rootPackage + "." + moduleName;
          String basePackageFacade = rootPackage + "." + moduleFacadeName;
          customMap.put("dtoPackage", basePackage + ".dto");
          customMap.put("dtoApiPackage", basePackage + ".api.dto");
          customMap.put("voApiPackage", basePackage + ".api.vo");
          customMap.put("pathName", controllerPathName);
          customMap.put("voPackage", basePackage + ".vo");
          customMap.put("queryPackage", basePackage + ".query");
          customMap.put("module", moduleName);
          customMap.put("facadeServicePackage", basePackage + ".feign");
          customMap.put("dtoPackageFacade", basePackageFacade + ".dto");
          customMap.put("voPackageFacade", basePackageFacade + ".vo");
          customMap.put("convertPackage", basePackage + ".convert");
          customMap.put("queryPackageFacade", basePackageFacade + ".query");
          customMap.put("mapperPackage", basePackage + ".dao");
          customMap.put("mapperAnnotation", true);
          customMap.put("facadePackage", basePackageFacade);
          consumer.customMap(customMap);

          // 自定义模板
          Map<String, String> customFile = new HashMap<>();
          customFile.put("Mapper.java", "/elegootemplates/mapperV2.java.ftl");
          customFile.put("Convert.java", "/elegootemplates/convertV2.java.ftl");
          customFile.put("ReqVO.java", "/elegootemplates/entityReqVO.java.ftl");
          customFile.put("RespVO.java", "/elegootemplates/entityRespVO.java.ftl");
          customFile.put("QueryBO.java", "/elegootemplates/entityQueryBO.java.ftl");
          customFile.put("Service.java", "/elegootemplates/serviceV2.java.ftl");
          customFile.put("ServiceImpl.java", "/elegootemplates/serviceImplV2.java.ftl");
          customFile.put("Entity.java", "/elegootemplates/entityV2.java.ftl");
          customFile.put("Controller.java", "/elegootemplates/controllerV2.java.ftl");
          if (isFacade) {
            customFile.put("FacadeService.java", "/elegootemplates/entityFacadeServiceV2.java.ftl");
          }
          consumer.customFile(customFile);
        }).strategyConfig(builder -> {
          builder.addInclude(tableName) // 设置需要生成的表名
              .addTablePrefix("prodigytec_") // 设置过滤表前缀
              .entityBuilder()
              .fileOverride()
              .disableSerialVersionUID()
              .enableLombok()
              .enableActiveRecord()
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
              .mapperBuilder()
          ;
        }).templateEngine(new EnhanceFreemarkerTemplateEngineV2()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
        .execute();

    FastAutoGenerator.create(url, userName, password)
        .globalConfig(builder -> {
          builder.author(author) // 设置作者
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
          String basePackage = rootPackage + "." + moduleFacadeName;
          customMap.put("dtoPackage", basePackage + ".dto");
          customMap.put("dtoApiPackage", basePackage + ".api.dto");
          customMap.put("voApiPackage", basePackage + ".api.vo");
          customMap.put("queryPackage", basePackage + ".query");
          customMap.put("module", moduleFacadeName);
          customMap.put("facadePackage", basePackage);
          customMap.put("ServiceNameConstants", serviceNameConstants);
          customMap.put("errorCode", errorCode);
          customMap.put("mapperAnnotation", true);
          customMap.put("apiConstants", basePackage + ".constants");
          consumer.customMap(customMap);

          // 自定义模板
          Map<String, String> customFile = new HashMap<>();
          customFile.put("DTO.java", "/elegootemplates/entityDTO.java.ftl");
          customFile.put("Facade.java", "/elegootemplates/entityFacadeV2.java.ftl");
          customFile.put("ApiConstants.java", "/elegootemplates/apiConstantsV2.java.ftl");
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
    ElegooCodeGeneratorV2 generator = new ElegooCodeGeneratorV2();
    for (String tableName : tableNames) {
      generator.execute(tableName);
    }
  }
}
