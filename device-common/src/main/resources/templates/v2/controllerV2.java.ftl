package ${package.Controller};

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rinoiot.core.annotation.Log;
import com.rinoiot.business.iot.annotation.PreAuthorize;
import com.rinoiot.core.enums.LogOperTypeEnum;
import com.rinoiot.core.result.ResponseResult;
import com.rinoiot.base.model.QueryPageDTO;
import ${package.Service}.${entity}Service;
import ${facadePackage}.${entity}Facade;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RequestMapping;
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

    private final ${entity}Facade ${entity?uncap_first}Facade;

    /**
     * 查询分页数据
     */
    @ApiOperation(value = "查询分页数据(${module}:${entity?replace("([a-z])([A-Z]+)","$1_$2","r")?lower_case}:list)")
    @PostMapping(value = "/queryPage")
    @PreAuthorize(hasAnyPermi = {"${module}:${entity?replace("([a-z])([A-Z]+)","$1_$2","r")?lower_case}:list"})
    public ResponseResult<Page<${entity}VO>> queryPage(@RequestBody QueryPageDTO<${entity}Query> pageDTO) {
        Page<${entity}VO> page = ${entity?uncap_first}Facade.queryPage(pageDTO);
        return ResponseResult.success(page);
    }

    /**
     * 根据${entity?uncap_first}Id查询
     */
    @ApiOperation(value = "根据${entity?uncap_first}Id查询数据(${module}:${entity?replace("([a-z])([A-Z]+)","$1_$2","r")?lower_case}:get)")
    @PreAuthorize(hasAnyPermi = {"${module}:${entity?replace("([a-z])([A-Z]+)","$1_$2","r")?lower_case}:get"})
    @PostMapping(value = "/getBy${keyPropertyName?cap_first}")
    public ResponseResult<${entity}VO> getBy${keyPropertyName?cap_first}(@RequestParam String ${keyPropertyName?uncap_first}) {
        ${entity}VO result = ${entity?uncap_first}Facade.getBy${keyPropertyName?cap_first}(${keyPropertyName?uncap_first});
        return ResponseResult.success(result);
    }

    /**
     * 新增
     */
    @ApiOperation(value = "新增数据(${module}:${entity?replace("([a-z])([A-Z]+)","$1_$2","r")?lower_case}:add)")
    @PreAuthorize(hasAnyPermi = {"${module}:${entity?replace("([a-z])([A-Z]+)","$1_$2","r")?lower_case}:add"})
    @PostMapping(value = "/add")
    @Log(key="${module}_${entity?replace("([a-z])([A-Z]+)","$1_$2","r")?lower_case}_add",title = "新增${table.comment!}", operatorType = LogOperTypeEnum.SAVE)
    public ResponseResult<Boolean> add(@RequestBody ${entity}DTO dto) {
        return ResponseResult.success(${entity?uncap_first}Facade.add(dto));
    }

    /**
     * 修改
     */
    @ApiOperation(value = "更新数据(${module}:${entity?replace("([a-z])([A-Z]+)","$1_$2","r")?lower_case}:edit)")
    @PreAuthorize(hasAnyPermi = {"${module}:${entity?replace("([a-z])([A-Z]+)","$1_$2","r")?lower_case}:edit"})
    @PostMapping(value = "/updateBy${keyPropertyName?cap_first}")
    @Log(key="${module}_${entity?replace("([a-z])([A-Z]+)","$1_$2","r")?lower_case}_edit",title = "修改${table.comment!}", operatorType = LogOperTypeEnum.EDIT)
    public ResponseResult<Boolean> updateBy${keyPropertyName?cap_first}(@RequestBody ${entity}DTO dto) {
        return ResponseResult.success(${entity?uncap_first}Facade.updateBy${keyPropertyName?cap_first}(dto));
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除数据(${module}:${entity?replace("([a-z])([A-Z]+)","$1_$2","r")?lower_case}:remove)")
    @PreAuthorize(hasAnyPermi = {"${module}:${entity?replace("([a-z])([A-Z]+)","$1_$2","r")?lower_case}:remove"})
    @PostMapping(value = "/removeBy${keyPropertyName?cap_first}")
    @Log(key="${module}_${entity?replace("([a-z])([A-Z]+)","$1_$2","r")?lower_case}_remove",title = "删除${table.comment!}", operatorType = LogOperTypeEnum.DELETE)
    public ResponseResult<Boolean> removeBy${keyPropertyName?cap_first}(@RequestParam String ${keyPropertyName?uncap_first}) {
        return ResponseResult.success(${entity?uncap_first}Facade.removeBy${keyPropertyName?cap_first}(${keyPropertyName?uncap_first}));
    }


}
</#if>
