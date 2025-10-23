package com.elegoo.cloud.generator.satellite;

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
//  private static String[] tableNames = new String[]{"tp_operation_record","tp_operation_record_count"};
  private static String[] tableNames = new String[]{"tp_operation_record"};
  //路由名称
  private String controllerPathName = "operation-record";
  //数据库名称
  private String databaseName = "elegoo";
  //模块名称
  private String moduleName = "tango";
  //数据库url
  private String url = "jdbc:postgresql://192.168.3.25:15432/test1018?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&allowMultiQueries=true&useSSL=false&allowPublicKeyRetrieval=true";
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
  private String rootPackage = "com.voxel.dance";

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
          builder.xml("/satellite/mapperV2.xml.ftl");
          builder.entity("").disable();
        }).injectionConfig(consumer -> {
          //自定义参数
          Map<String, Object> customMap = new HashMap<>();
          String basePackage = rootPackage + "." + moduleName;
          customMap.put("dtoPackage", basePackage + ".dto");
          customMap.put("dtoApiPackage", basePackage + ".api.dto");
          customMap.put("voApiPackage", basePackage + ".api.vo");
          customMap.put("pathName", controllerPathName);
          customMap.put("voPackage", basePackage + ".vo");
          customMap.put("queryPackage", basePackage + ".bo");
          customMap.put("module", moduleName);
          customMap.put("convertPackage", basePackage + ".convert");
          customMap.put("mapperPackage", basePackage + ".dao");
          customMap.put("boPackage", basePackage + ".domain.bo");
          customMap.put("ormPackage", basePackage + ".orm");
          customMap.put("entityPackage", basePackage + ".domain.po");
          customMap.put("basePackage", basePackage);
          customMap.put("mapperAnnotation", true);
          consumer.customMap(customMap);

          // 自定义模板
          Map<String, String> customFile = new HashMap<>();
          customFile.put("Mapper.java", "/satellite/mapperV2.java.ftl");
          customFile.put("Mapper.xml", "/satellite/mapperV2.xml.ftl");
          customFile.put("Convert.java", "/satellite/convertV2.java.ftl");
          customFile.put("ReqVO.java", "/satellite/entityReqVO.java.ftl");
          customFile.put("RespVO.java", "/satellite/entityRespVO.java.ftl");
          customFile.put("QueryBO.java", "/satellite/entityBO.java.ftl");
          customFile.put("DataService.java", "/satellite/dataserviceV2.java.ftl");
          customFile.put("Service.java", "/satellite/serviceV2.java.ftl");
          customFile.put("DataServiceImpl.java", "/satellite/dataserviceImplV2.java.ftl");
          customFile.put("ServiceImpl.java", "/satellite/serviceImplV2.java.ftl");
          customFile.put("Entity.java", "/satellite/entityV2.java.ftl");
          customFile.put("Controller.java", "/satellite/controllerV2.java.ftl");
          consumer.customFile(customFile);
        }).strategyConfig(builder -> {
          builder.addInclude(tableName) // 设置需要生成的表名
              .addTablePrefix("tp_") // 设置过滤表前缀
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
          customMap.put("ServiceNameConstants", serviceNameConstants);
          customMap.put("errorCode", errorCode);
          customMap.put("mapperAnnotation", true);
          consumer.customMap(customMap);

          // 自定义模板
          Map<String, String> customFile = new HashMap<>();
          customFile.put("DTO.java", "/satellite/entityDTO.java.ftl");
          consumer.customFile(customFile);
        }).strategyConfig(builder -> {
          builder.addInclude(tableName) // 设置需要生成的表名
              .addTablePrefix("tp_") // 设置过滤表前缀
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
