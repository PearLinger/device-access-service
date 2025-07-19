package ${package.Controller};

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.elegoo.framework.common.pojo.CommonResult;
import ${package.Service}.${entity}Service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import com.elegoo.framework.common.pojo.PageResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>
import ${dtoPackageFacade}.${entity}DTO;
import ${voPackageFacade}.${entity}VO;
import ${queryPackageFacade}.${entity}Query;

<#list table.fields as field>
    <#if field.keyFlag>
        <#assign keyPropertyName="${field.propertyName}"/>
    </#if>
</#list>

/**
* <p>
* ${table.comment!} 前端控制器
* </p>
*
* @author ${author}
* @since ${date}
*/
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequiredArgsConstructor
@RequestMapping("/v1<#if pathName?? && pathName != "">/${pathName}</#if>/<#if controllerMappingHyphenStyle>${controllerMappingHyphen}<#else>${entity?replace("([a-z])([A-Z]+)",
"$1/$2","r")?lower_case}</#if>")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>

    private final ${entity}Service ${entity?uncap_first}Service;

    /**
     * 查询分页数据
     */
    @PostMapping(value = "/queryPage")
    public CommonResult<PageResult<${entity}VO>> queryPage(@RequestBody ${entity}Query pageDTO) {
        PageResult<${entity}VO> page = ${entity?uncap_first}Service.queryPage(pageDTO);
        return CommonResult.success(page);
    }

    /**
     * 根据${entity?uncap_first}Id查询
     */
    @PostMapping(value = "/getBy${keyPropertyName?cap_first}")
    public CommonResult<${entity}VO> getBy${keyPropertyName?cap_first}(@RequestParam Long ${keyPropertyName?uncap_first}) {
        ${entity}VO result = ${entity?uncap_first}Service.getBy${keyPropertyName?cap_first}(${keyPropertyName?uncap_first});
        return CommonResult.success(result);
    }

    /**
     * 新增
     */
    @PostMapping(value = "/add")
    public CommonResult<Boolean> add(@RequestBody ${entity}DTO dto) {
        return CommonResult.success(${entity?uncap_first}Service.add(dto));
    }

    /**
     * 修改
     */
    @PostMapping(value = "/updateBy${keyPropertyName?cap_first}")
    public CommonResult<Boolean> updateBy${keyPropertyName?cap_first}(@RequestBody ${entity}DTO dto) {
        return CommonResult.success(${entity?uncap_first}Service.updateBy${keyPropertyName?cap_first}(dto));
    }

    /**
     * 删除
     */
    @DeleteMapping(value = "/removeBy${keyPropertyName?cap_first}")
    public CommonResult<Boolean> removeBy${keyPropertyName?cap_first}(@RequestParam Long ${keyPropertyName?uncap_first}) {
        return CommonResult.success(${entity?uncap_first}Service.removeBy${keyPropertyName?cap_first}(${keyPropertyName?uncap_first}));
    }


}
</#if>
