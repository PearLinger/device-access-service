package ${facadePackage};


import ${dtoPackage}.${entity}DTO;
import ${voPackage}.${entity}VO;
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
@Service
<#--<todo #--@FeignClient(contextId = "${entity}Facade", value = ${ServiceNameConstants}, fallback = ${entity}FacadeFallback.class)&ndash;&gt;-->
@FeignClient(contextId = "${entity}Facade")
public interface ${entity}Facade {

    @PostMapping(value = "/inside/v1/${entity?replace("([a-z])([A-Z]+)","$1/$2","r")?lower_case}/queryPage")
    PageResult<${entity}VO> queryPage(@RequestBody ${entity}Query pageDTO);

    @PostMapping(value = "/inside/v1/${entity?replace("([a-z])([A-Z]+)","$1/$2","r")?lower_case}/queryList")
    List<${entity}VO> queryList(@RequestBody ${entity}Query query);

    @PostMapping(value = "/inside/v1/${entity?replace("([a-z])([A-Z]+)","$1/$2","r")?lower_case}/getBy${keyPropertyName?cap_first}")
    ${entity}VO getBy${keyPropertyName?cap_first}(@RequestParam("${keyPropertyName?uncap_first}") String ${keyPropertyName?uncap_first});

    @PostMapping(value = "/inside/v1/${entity?replace("([a-z])([A-Z]+)","$1/$2","r")?lower_case}/add")
    Boolean add(@RequestBody ${entity}DTO dto);

    @PostMapping(value = "/inside/v1/${entity?replace("([a-z])([A-Z]+)","$1/$2","r")?lower_case}/updateBy${keyPropertyName?cap_first}")
    Boolean updateBy${keyPropertyName?cap_first}(@RequestBody ${entity}DTO dto);

    @PostMapping(value = "/inside/v1/${entity?replace("([a-z])([A-Z]+)","$1/$2","r")?lower_case}/removeBy${keyPropertyName?cap_first}")
    Boolean removeBy${keyPropertyName?cap_first}(@RequestParam("${keyPropertyName?uncap_first}") String ${keyPropertyName?uncap_first});
}
</#if>
