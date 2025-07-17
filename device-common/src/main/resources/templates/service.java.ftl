package ${package.Service};

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${package.Entity}.${entity};
import ${superServiceClassPackage};
import ${dtoPackage}.${entity}DTO;
import ${voPackage}.${entity}VO;
import ${queryPackage}.${entity}Query;
import com.rinoiot.base.model.QueryPageDTO;
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
public interface ${table.serviceName} extends ${superServiceClass}<${entity}> {

    Page<${entity}VO> queryPage(QueryPageDTO<${entity}Query> pageDTO);

    List<${entity}VO> queryList(${entity}Query query);

    ${entity}VO getBy${keyPropertyName?cap_first}(String ${keyPropertyName?uncap_first});

    Boolean add(${entity}DTO dto);

    Boolean updateBy${keyPropertyName?cap_first}(${entity}DTO dto);

    Boolean removeBy${keyPropertyName?cap_first}(String ${keyPropertyName?uncap_first});
}
</#if>
