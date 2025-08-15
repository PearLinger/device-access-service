package com.elegoo.cloud.generator;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import jakarta.validation.constraints.NotNull;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class EnhanceFreemarkerTemplateFacadeEngine extends FreemarkerTemplateEngine {
    private static Map<String,String> customMapNoFileOverride = new HashMap<>();
    static{
        customMapNoFileOverride.put("facadeServicePackage","facadeServicePackage");
        customMapNoFileOverride.put("dtoPackageFacade","dtoPackageFacade");
        customMapNoFileOverride.put("voPackageFacade","voPackageFacade");
        customMapNoFileOverride.put("queryPackageFacade","queryPackageFacade");
        customMapNoFileOverride.put("mapperPackage","mapperPackage");
        customMapNoFileOverride.put("facadePackage","facadePackage");
        customMapNoFileOverride.put("FacadeFallback","FacadeFallback");
        customMapNoFileOverride.put("FacadeService","FacadeService");
        customMapNoFileOverride.put("Mapper","Mapper");
    }

    @Override
    protected void outputMapper(@NotNull TableInfo tableInfo,@NotNull Map<String,Object> objectMap) {

    }

    @Override
    protected void outputService(@NotNull TableInfo tableInfo,@NotNull Map<String,Object> objectMap) {
    }

    @Override
    protected void outputController(@NotNull TableInfo tableInfo,@NotNull Map<String,Object> objectMap) {
    }

    @Override
    protected void outputEntity(@NotNull TableInfo tableInfo,@NotNull Map<String,Object> objectMap) {
    }

    @Override
    protected void outputCustomFile(Map<String, String> customFile, TableInfo tableInfo, Map<String, Object> objectMap) {
        String entityName = tableInfo.getEntityName();
        customFile.forEach((key, value) -> {
            String otherPath = this.getPathInfo(OutputFile.other);
            otherPath = StrUtil.removeSuffix(otherPath, "other");
            if (key.equals("DTO.java")) {
                otherPath = otherPath + "dto";
            } else if (key.equals("VO.java")) {
                otherPath = otherPath + "vo";
            } else if(key.equals("FacadeFallback.java")){
                otherPath = otherPath + "hystrix";
            } else if(key.equals("Convert.java")){
                otherPath = otherPath + "convert";
            } else if(key.equals("ApiConstants.java")){
                otherPath = otherPath + "constants";
            }
            String fileName = "";
            if (key.equals("ApiConstants.java")) {
                fileName = String.format(otherPath + File.separator + "%s", key);
            }else{
                fileName = String.format(otherPath + File.separator + entityName + "%s", key);
            }

            if (key.equals("DTO.java") || key.equals("VO.java") || key.equals("Query.java")) {
                this.outputFile(new File(fileName), objectMap, value,true);
            } else {
                this.outputFile(new File(fileName), objectMap, value);
            }
        });
    }
}
