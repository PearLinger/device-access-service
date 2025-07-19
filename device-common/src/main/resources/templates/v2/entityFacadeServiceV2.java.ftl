package ${facadeServicePackage};

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${package.Entity}.${entity};
import ${superServiceClassPackage};
import ${dtoPackageFacade}.${entity}DTO;
import ${voPackageFacade}.${entity}VO;
import ${queryPackageFacade}.${entity}Query;
import ${package.Service}.${entity}Service;
import ${facadePackage}.${entity}Facade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import com.elegoo.framework.common.pojo.PageResult;
import java.util.List;
<#list table.fields as field>
    <#if field.keyFlag>
        <#assign keyPropertyName="${field.propertyName}"/>
    </#if>
</#list>

/**
 * <p>
 * ${table.comment!} Facade实现
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
@RestController
@RequiredArgsConstructor
public class ${entity}FacadeService implements ${entity}Facade {

    private final ${entity}Service ${entity?uncap_first}Service;

    @Override
    public PageResult<${entity}VO> queryPage(${entity}Query pageDTO){
        return ${entity?uncap_first}Service.queryPage(pageDTO);
    }

    @Override
    public List<${entity}VO> queryList(${entity}Query query){
        return ${entity?uncap_first}Service.queryList(query);
    }

    @Override
    public ${entity}VO getBy${keyPropertyName?cap_first}(Long ${keyPropertyName?uncap_first}){
        return ${entity?uncap_first}Service.getBy${keyPropertyName?cap_first}(${keyPropertyName?uncap_first});
    }

    @Override
    public Boolean add(${entity}DTO dto){
        return ${entity?uncap_first}Service.add(dto);
    }

    @Override
    public Boolean updateBy${keyPropertyName?cap_first}(${entity}DTO dto){
        return ${entity?uncap_first}Service.updateBy${keyPropertyName?cap_first}(dto);
    }

    @Override
    public Boolean removeBy${keyPropertyName?cap_first}(Long ${keyPropertyName?uncap_first}){
        return ${entity?uncap_first}Service.removeBy${keyPropertyName?cap_first}(${keyPropertyName?uncap_first});
    }
}
</#if>
