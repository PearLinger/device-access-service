package ${package.Service};

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${entityPackage}.${entity};
import ${superServiceClassPackage};
import ${package.Controller}.vo.*;
import java.util.List;
import com.voxel.dance.common.pojo.PageResult;
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
public interface I${table.serviceName} {

    PageResult<${entity}RespVO> queryPage(${entity}ReqVO vo);

    List<${entity}RespVO> queryList(${entity}ReqVO query);

    ${entity}RespVO getBy${keyPropertyName?cap_first}(Long ${keyPropertyName?uncap_first});

    Boolean add(${entity}ReqVO vo);

    Boolean updateBy${keyPropertyName?cap_first}(${entity}ReqVO vo);

    Boolean removeBy${keyPropertyName?cap_first}(Long ${keyPropertyName?uncap_first});
    }

