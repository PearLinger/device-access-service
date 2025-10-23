package ${boPackage};

<#if swagger>
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
</#if>
<#if entityLombokModel>
import lombok.Data;
<#if chainModel>
import lombok.experimental.Accessors;
    </#if>
</#if>
import java.util.Date;
import java.math.*;
import com.voxel.dance.common.pojo.SortablePageParam;
/**
* <p>
* ${table.comment!}
* </p>
*
* @author ${author}
* @since ${date}
*/
<#if entityLombokModel>
@Data
<#if chainModel>
@Accessors(chain = true)
</#if>
</#if>
<#if swagger>
@ApiModel(value = "${entity}BO对象", description = "${table.comment!}")
</#if>
public class ${entity}BO extends SortablePageParam{
<#if entitySerialVersionUID>

    private static final long serialVersionUID = 1L;
</#if>
<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>
    <#if field.keyFlag>
        <#assign keyPropertyName="${field.propertyName}"/>
    </#if>

<#if field.comment!?length gt 0>
<#if swagger>
    @ApiModelProperty("${field.comment}")
<#else>
    /**
    * ${field.comment}
    */
</#if>
</#if>
    private ${field.propertyType} ${field.propertyName};
</#list>
<#------------  END 字段循环遍历  ---------->


<#if entityColumnConstant>
    <#list table.fields as field>
        public static final String ${field.name?upper_case} = "${field.name}";

    </#list>
</#if>
}
