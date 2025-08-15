package ${package.Service};

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${package.Entity}.${entity};
import ${superServiceClassPackage};
import ${dtoPackageFacade}.${entity}DTO;
import ${voPackageFacade}.${entity}VO;
import ${queryPackageFacade}.${entity}Query;
import java.util.List;
import com.elegoo.framework.common.pojo.PageResult;
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
public interface I${table.serviceName} extends ${superServiceClass}<${entity}> {

    PageResult<${entity}VO> queryPage(${entity}Query pageDTO);

    List<${entity}VO> queryList(${entity}Query query);

    ${entity}VO getBy${keyPropertyName?cap_first}(Long ${keyPropertyName?uncap_first});

    Boolean add(${entity}DTO dto);

    Boolean updateBy${keyPropertyName?cap_first}(${entity}DTO dto);

    Boolean removeBy${keyPropertyName?cap_first}(Long ${keyPropertyName?uncap_first});
}
</#if>
