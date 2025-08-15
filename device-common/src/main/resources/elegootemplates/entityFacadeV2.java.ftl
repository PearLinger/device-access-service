package ${facadePackage};


import ${dtoPackage}.${entity}DTO;
<#--import ${facadeFallbackPackage}.${entity}FacadeFallback;-->
import ${queryPackage}.${entity}Query;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import com.elegoo.framework.common.pojo.PageResult;
<#list table.fields as field>
    <#if field.keyFlag>
        <#assign keyPropertyName="${field.propertyName}"/>
    </#if>
</#list>

/**
 * <p>
 * ${table.comment!} Facade
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
@FeignClient(name = ApiConstants.NAME)
public interface ${entity}Api {
    String PREFIX = ApiConstants.RPC_PREFIX + "/device";


}
</#if>
