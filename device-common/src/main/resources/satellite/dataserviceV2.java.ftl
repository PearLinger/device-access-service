package ${ormPackage}.iService;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${entityPackage}.${entity};
import ${boPackage}.${entity}BO;
import ${superServiceClassPackage};
import java.util.List;
<#list table.fields as field>
    <#if field.keyFlag>
        <#assign keyPropertyName="${field.propertyName}"/>
    </#if>
</#list>

/**
 * <p>
 * ${table.comment!} 服务类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
public interface I${table.entityName}DataService extends ${superServiceClass}<${entity}> {

    Page<${entity}> queryPage(${entity}BO queryBO);

    List<${entity}> queryList(${entity}BO queryBO);

    ${entity} getBy${keyPropertyName?cap_first}(Long ${keyPropertyName?uncap_first});

    Boolean add(${entity} entity);

    Boolean updateEntity(${entity} entity);

    Boolean removeBy${keyPropertyName?cap_first}(Long ${keyPropertyName?uncap_first});
}
</#if>
