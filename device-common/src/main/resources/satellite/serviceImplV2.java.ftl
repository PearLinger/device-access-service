package ${package.ServiceImpl};

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${entityPackage}.${entity};
import ${mapperPackage}.${table.mapperName};
import ${boPackage}.${entity}BO;
import ${ormPackage}.iService.I${table.entityName}DataService;
import ${package.Service}.I${table.serviceName};
import ${superServiceImplClassPackage};
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import cn.hutool.core.util.StrUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import ${basePackage}.converts.${entity}Convert;
import com.voxel.dance.common.pojo.PageResult;
import javax.annotation.Resource;
import ${package.Controller}.vo.*;
import com.voxel.dance.tango.framework.exception.ServiceException;
import com.voxel.dance.tango.enums.HttpStatusCodeEnum;
<#list table.fields as field>
    <#if field.keyFlag>
        <#assign keyPropertyName="${field.propertyName}"/>
    </#if>
</#list>

/**
* <p>
    * ${table.comment!} 服务实现类
    * </p>
*
* @author ${author}
* @since ${date}
*/
@Slf4j
@Service
@RequiredArgsConstructor
public class ${table.serviceImplName} implements I${table.serviceName} {
@Resource
private ${entity}Convert convert;
@Resource
private I${table.entityName}DataService ${entity?uncap_first}DataService;
    @Override
    public PageResult<${entity}RespVO> queryPage(${entity}ReqVO vo) {
    ${entity}BO bo = convert.reqVOConvertToBO(vo);
    Page<${entity}> page = ${entity?uncap_first}DataService.queryPage(bo);
    return convert.pageEntityConvertToVO(page);
    }

    @Override
    public List<${entity}RespVO> queryList(${entity}ReqVO vo) {
    ${entity}BO bo = convert.reqVOConvertToBO(vo);
    List<${entity}> list = ${entity?uncap_first}DataService.queryList(bo);
    return convert.listEntitysConvertToVO(list);
    }


    @Override
    public ${entity}RespVO getBy${keyPropertyName?cap_first}(Long ${keyPropertyName?uncap_first}){
    if (${keyPropertyName?uncap_first} == null){
    throw new ServiceException(HttpStatusCodeEnum.PARAM_VALIDATION_FAILED);
    }
    ${entity} record = ${entity?uncap_first}DataService.getById(${keyPropertyName?uncap_first});
    return convert.entityConvertToRespVO(record);
    }

    @Override
    public Boolean add(${entity}ReqVO vo){
    valParams(vo);
    ${entity} record = convert.reqVOConvertToEntity(vo);
    return ${entity?uncap_first}DataService.add(record);
    }

    @Override
    public Boolean updateBy${keyPropertyName?cap_first}(${entity}ReqVO vo){
    if(ObjectUtil.isEmpty(vo.get${keyPropertyName?cap_first}())){
    throw new ServiceException(HttpStatusCodeEnum.PARAM_VALIDATION_FAILED);
    }
    valParams(vo);
    ${entity} record = convert.reqVOConvertToEntity(vo);
    return ${entity?uncap_first}DataService.updateEntity(record);
    }

        private void valParams(${entity}ReqVO vo){
        }

    @Override
    public Boolean removeBy${keyPropertyName?cap_first}(Long ${keyPropertyName?uncap_first}){
    if (${keyPropertyName?uncap_first} == null){
    throw new ServiceException(HttpStatusCodeEnum.PARAM_VALIDATION_FAILED);
    }
    return ${entity?uncap_first}DataService.removeById(${keyPropertyName?uncap_first});
    }


   }
