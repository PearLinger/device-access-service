package com.elegoo.cloud.generator;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import java.io.File;
import java.util.Map;

public class EnhanceFreemarkerTemplateEngine extends FreemarkerTemplateEngine {

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
            } else if (key.equals("Query.java")) {
                otherPath = otherPath + "query";
            } else if(key.equals("Facade.java")){
                otherPath = otherPath + "facade";
            }else if(key.equals("FacadeFallback.java")){
                otherPath = otherPath + "facade/hystrix";
            }else if(key.equals("FacadeService.java")){
                otherPath = otherPath + "facade/service";
            }
            String fileName = String.format(otherPath + File.separator + entityName + "%s", key);
            this.outputFile(new File(fileName), objectMap, value);
        });
    }
}
