package ${package.Controller};

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.voxel.dance.api.result.AjaxResult;
import ${package.Service}.I${table.serviceName};
import ${package.Controller}.vo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import com.voxel.dance.common.pojo.PageResult;
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
@RequestMapping("/api/tp<#if pathName?? && pathName != "">/${pathName}</#if>")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>

    private final I${entity}Service ${entity?uncap_first}Service;

    /**
     * 查询分页数据
     */
    @PostMapping(value = "/queryPage")
    public AjaxResult<PageResult<${entity}RespVO>> queryPage(@RequestBody ${entity}ReqVO vo) {
        PageResult<${entity}RespVO> page = ${entity?uncap_first}Service.queryPage(vo);
        return AjaxResult.success(page);
    }

    /**
     * 根据${entity?uncap_first}Id查询
     */
    @PostMapping(value = "/getBy${keyPropertyName?cap_first}")
    public AjaxResult<${entity}RespVO> getBy${keyPropertyName?cap_first}(@RequestParam Long ${keyPropertyName?uncap_first}) {
        ${entity}RespVO result = ${entity?uncap_first}Service.getBy${keyPropertyName?cap_first}(${keyPropertyName?uncap_first});
        return AjaxResult.success(result);
    }

    /**
     * 新增
     */
    @PostMapping(value = "/add")
    public AjaxResult<Boolean> add(@RequestBody ${entity}ReqVO vo) {
        return AjaxResult.success(${entity?uncap_first}Service.add(vo));
    }

    /**
     * 修改
     */
    @PostMapping(value = "/updateBy${keyPropertyName?cap_first}")
    public AjaxResult<Boolean> updateBy${keyPropertyName?cap_first}(@RequestBody ${entity}ReqVO vo) {
        return AjaxResult.success(${entity?uncap_first}Service.updateBy${keyPropertyName?cap_first}(vo));
    }

    /**
     * 删除
     */
    @DeleteMapping(value = "/removeBy${keyPropertyName?cap_first}")
    public AjaxResult<Boolean> removeBy${keyPropertyName?cap_first}(@RequestParam Long ${keyPropertyName?uncap_first}) {
        return AjaxResult.success(${entity?uncap_first}Service.removeBy${keyPropertyName?cap_first}(${keyPropertyName?uncap_first}));
    }


}
</#if>
