package ${ormPackage}.iService.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${entityPackage}.${entity};
import ${boPackage}.${entity}BO;
import ${mapperPackage}.${table.mapperName};
import  ${ormPackage}.iService.I${table.entityName}DataService;
import ${superServiceImplClassPackage};
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import cn.hutool.core.util.StrUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.elegoo.framework.common.exception.ServiceException;
import com.elegoo.framework.common.exception.enums.GlobalErrorCodeConstants;
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
<#if kotlin>
open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

}
<#else>
public class ${table.entityName}DataService extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements I${table.entityName}DataService {

	  @Override
    public Page<${entity}> queryPage(${entity}BO bo) {
        Page<${entity}> page = new Page<${entity}>(bo.getPageNo(),bo.getPageSize());
        LambdaQueryWrapper<${entity}> lqw = getWrapper(bo);
        page = this.page(page,lqw);
        return page;
    }

    @Override
    public List<${entity}> queryList(${entity}BO query) {
        LambdaQueryWrapper<${entity}> lqw = getWrapper(query);
        List<${entity}> list = this.list(lqw);
        return list;
    }


    @Override
    public ${entity} getBy${keyPropertyName?cap_first}(Long ${keyPropertyName?uncap_first}){
        ${entity} record = super.getById(${keyPropertyName?uncap_first});
        return record;
    }

    @Override
    public Boolean add(${entity} entity){
        return this.save(entity);
    }

    @Override
    public Boolean updateEntity(${entity} entity){
        return super.updateById(entity);
    }


    @Override
    public Boolean removeBy${keyPropertyName?cap_first}(Long ${keyPropertyName?uncap_first}){
        return super.removeById(${keyPropertyName?uncap_first});
    }

    private LambdaQueryWrapper<${entity}> getWrapper(${entity}BO query) {
        LambdaQueryWrapper<${entity}> lqw = new LambdaQueryWrapper<>();
        if (query != null) {
            <#list table.fields as field>
            <#if field.propertyType == 'String'>
            if(StrUtil.isNotEmpty(query.get${field.propertyName?cap_first}())){
                lqw.eq(${entity}::get${field.propertyName?cap_first}, query.get${field.propertyName?cap_first}());
            }
            </#if>
            <#if field.propertyType != 'String'>
            if(query.get${field.propertyName?cap_first}() != null){
                lqw.eq(${entity}::get${field.propertyName?cap_first}, query.get${field.propertyName?cap_first}());
            }
            </#if>
            </#list>
        }
        lqw.orderByDesc(${entity}::get${keyPropertyName?cap_first});
        return lqw;
    }
}
</#if>
