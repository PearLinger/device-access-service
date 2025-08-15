package ${package.ServiceImpl};

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${package.Entity}.${entity};
import ${mapperPackage}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${dtoPackageFacade}.${entity}DTO;
import ${voPackageFacade}.${entity}VO;
import ${queryPackageFacade}.${entity}Query;
import ${superServiceImplClassPackage};
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import cn.hutool.core.util.StrUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.elegoo.framework.common.pojo.PageResult;
import com.elegoo.cloud.device.service.${entity}Convert;
import com.elegoo.framework.common.exception.ServiceException;
import com.elegoo.framework.common.exception.enums.GlobalErrorCodeConstants;
<#list table.fields as field>
<#if field.keyFlag>
    <#assign keyPropertyName="${field.propertyName}"/>
</#if>
</#list>

/**
 * <p>
 * ${table.comment!} 服务实现类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Slf4j
@Service
@RequiredArgsConstructor
<#if kotlin>
open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

}
<#else>
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements I${table.serviceName} {
  @Autowired
  private ${entity}Convert convert;
	@Override
    public PageResult<${entity}VO> queryPage(${entity}Query pageDTO) {
        Page<${entity}> page = new Page<${entity}>(pageDTO.getPageNo(),pageDTO.getPageSize());
        LambdaQueryWrapper<${entity}> lqw = getWrapper(pageDTO);
        page = this.page(page,lqw);
        return convert.pageEntityConvertToVO(page);
    }

    @Override
    public List<${entity}VO> queryList(${entity}Query query) {
        LambdaQueryWrapper<${entity}> lqw = getWrapper(query);
        List<${entity}> list = this.list(lqw);
        return convert.entitysConvertToVO(list);
    }


    @Override
    public ${entity}VO getBy${keyPropertyName?cap_first}(Long ${keyPropertyName?uncap_first}){
        if (${keyPropertyName?uncap_first} == null){
          throw new ServiceException(GlobalErrorCodeConstants.BAD_REQUEST);
        }
        ${entity} record = super.getById(${keyPropertyName?uncap_first});
        return convert.entityConvertToVO(record);
    }

    @Override
    public Boolean add(${entity}DTO dto){
        valParams(dto);
        ${entity} record = convert.dtoConvertToEntity(dto);
        return this.save(record);
    }

    @Override
    public Boolean updateBy${keyPropertyName?cap_first}(${entity}DTO dto){
        if(ObjectUtil.isEmpty(dto.get${keyPropertyName?cap_first}())){
           throw new ServiceException(GlobalErrorCodeConstants.BAD_REQUEST);
        }
        valParams(dto);
        ${entity} record = convert.dtoConvertToEntity(dto);
        return this.updateById(record);
    }

    private void valParams(${entity}DTO dto){
    }

    @Override
    public Boolean removeBy${keyPropertyName?cap_first}(Long ${keyPropertyName?uncap_first}){
        if (${keyPropertyName?uncap_first} == null){
        throw new ServiceException(GlobalErrorCodeConstants.BAD_REQUEST);
        }
        return super.removeById(${keyPropertyName?uncap_first});
    }

    private LambdaQueryWrapper<${entity}> getWrapper(${entity}Query query) {
        LambdaQueryWrapper<${entity}> lqw = new LambdaQueryWrapper<>();
        if (query != null) {
            <#list table.fields as field>
            <#if field.propertyType == 'String'>
            if(StrUtil.isNotEmpty(query.get${field.propertyName?cap_first}())){
                lqw.eq(${entity}::get${field.propertyName?cap_first}, query.get${field.propertyName?cap_first}());
            }
            </#if>
            <#if field.propertyType != 'String'>
            if(query.get${field.propertyName?cap_first}() != null){
                lqw.eq(${entity}::get${field.propertyName?cap_first}, query.get${field.propertyName?cap_first}());
            }
            </#if>
            </#list>
        }
        lqw.orderByDesc(${entity}::get${keyPropertyName?cap_first});
        return lqw;
    }
}
</#if>
