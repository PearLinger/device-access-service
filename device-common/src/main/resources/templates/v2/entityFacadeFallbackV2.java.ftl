package ${facadeFallbackPackage};

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${dtoPackage}.${entity}DTO;
import ${voPackage}.${entity}VO;
import ${queryPackage}.${entity}Query;
import ${facadePackage}.${entity}Facade;
import com.rinoiot.base.exception.BaseException;
import com.rinoiot.base.model.CommonErrorCodes;
import com.rinoiot.base.model.QueryPageDTO;
import org.springframework.stereotype.Component;
import java.util.List;
<#list table.fields as field>
    <#if field.keyFlag>
        <#assign keyPropertyName="${field.propertyName}"/>
    </#if>
</#list>

/**
 * <p>
 * ${table.comment!} FacadeFallback
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
@Component
public class ${entity}FacadeFallback implements ${entity}Facade {

    @Override
    public Page<${entity}VO> queryPage(QueryPageDTO<${entity}Query> pageDTO){
        throw new BaseException(${errorCode});
    }

    @Override
    public List<${entity}VO> queryList(${entity}Query query){
        throw new BaseException(${errorCode});
    }

    @Override
    public ${entity}VO getBy${keyPropertyName?cap_first}(String ${keyPropertyName?uncap_first}){
        throw new BaseException(${errorCode});
    }

    @Override
    public Boolean add(${entity}DTO dto){
        throw new BaseException(${errorCode});
    }

    @Override
    public Boolean updateBy${keyPropertyName?cap_first}(${entity}DTO dto){
        throw new BaseException(${errorCode});
    }

    @Override
    public Boolean removeBy${keyPropertyName?cap_first}(String ${keyPropertyName?uncap_first}){
        throw new BaseException(${errorCode});
    }
}
</#if>
