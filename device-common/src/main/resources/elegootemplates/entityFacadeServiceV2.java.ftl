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
@Validated
public class ${entity}ApiImpl implements ${entity}Api {

}
</#if>
