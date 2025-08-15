package com.elegoo.cloud.generator;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import java.awt.image.Kernel;
import java.io.File;
import java.util.Map;
import java.util.Set;

public class EnhanceFreemarkerTemplateEngineV2 extends FreemarkerTemplateEngine {

    @Override
    protected void outputCustomFile(Map<String, String> customFile, TableInfo tableInfo, Map<String, Object> objectMap) {
        String entityName = tableInfo.getEntityName();
        Set<String> strings = customFile.keySet();
        for (String key : strings) {
            String value = customFile.get(key);
            String otherPath = this.getPathInfo(OutputFile.other);
            otherPath = StrUtil.removeSuffix(otherPath, "other");
            if (key.equals("DTO.java")) {
                otherPath = otherPath + "dto";
            } else if (key.equals("RespVO.java") || key.equals("ReqVO.java")) {
                otherPath = otherPath + "controller\\vo";
            } else if (key.equals("QueryBO.java")) {
                otherPath = otherPath + "controller\\bo";
            } else if(key.equals("FacadeService.java")){
                otherPath = otherPath + "feign";
            } else if (key.equals("Mapper.java")) {
                otherPath = otherPath + "dao";
            } else if(key.equals("Convert.java")){
                otherPath = otherPath + "convert";
            } else if(key.equals("Service.java") ||  key.equals("ServiceImpl.java")){
                otherPath = otherPath + "dataservice";
            } else if(key.equals("Entity.java")){
                otherPath = otherPath + "dal.pgsql";
            } else if(key.equals("Controller.java")){
                otherPath = otherPath + "controller";
            }
            String fileName = "";
            if (key.equals("QueryBO.java")) {
                fileName = String.format(otherPath + File.separator + "%s", entityName+"BO.java");
            }else if(key.equals("Entity.java")){
                fileName = String.format(otherPath + File.separator + entityName + ".java");
            }
            else if(key.equals("Service.java")) {
                fileName = String.format(otherPath + File.separator + "I" + entityName + "%s", key);
            } else {
                fileName = String.format(otherPath + File.separator + entityName + "%s", key);
            }
            this.outputFile(new File(fileName), objectMap, value,true);
        }
    }
}
