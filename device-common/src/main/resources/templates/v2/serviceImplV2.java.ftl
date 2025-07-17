package ${package.ServiceImpl};

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rinoiot.base.exception.BaseException;
import com.rinoiot.base.model.CommonErrorCodes;
import ${package.Entity}.${entity};
import ${mapperPackage}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${dtoPackageFacade}.${entity}DTO;
import ${voPackageFacade}.${entity}VO;
import ${queryPackageFacade}.${entity}Query;
import ${superServiceImplClassPackage};
import com.rinoiot.base.model.QueryPageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.rinoiot.mybatisplus.utils.PageUtil;
import com.rinoiot.core.utils.BeanUtils;
import cn.hutool.core.util.StrUtil;
import java.util.List;

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
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {

	@Override
    public PageResult<${entity}VO> queryPage(${entity}Query pageDTO) {
        Page<${entity}> page = new Page<${entity}>(pageDTO.getPageNo(),pageDTO.getPageSize());
        LambdaQueryWrapper<${entity}> lqw = getWrapper(pageDTO.getQueryCondition());

        PageResult<${entity}VO> pageResult = new PageResult<>();
        pageResult.setList(toRespVOS(page.getList(), false));
        pageResult.setTotal(page.getTotal());
        return pageResult;
    }

    @Override
    public List<${entity}VO> queryList(${entity}Query query) {
        LambdaQueryWrapper<${entity}> lqw = getWrapper(query);
        List<${entity}> list = this.list(lqw);
        return BeanUtils.copyListAndOperator(list,${entity}VO.class,(source, target) -> setOther(target));
    }

    private void setOther(${entity}VO target) {
    }

    @Override
    public ${entity}VO getBy${keyPropertyName?cap_first}(String ${keyPropertyName?uncap_first}){
        if (StrUtil.isEmpty(${keyPropertyName?uncap_first})){
            throw new BaseException(CommonErrorCodes.MISS_PARAMS);
        }
        ${entity}VO result = null;
        ${entity} record = super.getById(${keyPropertyName?uncap_first});
        if(record != null){
            result = new ${entity}VO();
            BeanUtils.copyProperties(record,result);
        }
        return result;
    }

    @Override
    public Boolean add(${entity}DTO dto){
        valParams(dto);
        ${entity} record = new ${entity}();
        BeanUtils.copyProperties(dto,record);
        return this.save(record);
    }

    @Override
    public Boolean updateBy${keyPropertyName?cap_first}(${entity}DTO dto){
        if(StrUtil.isBlank(dto.get${keyPropertyName?cap_first}())){
            throw new BaseException(CommonErrorCodes.MISS_PARAMS);
        }
        valParams(dto);
        ${entity} record = new ${entity}();
        BeanUtils.copyProperties(dto,record);
        return this.updateById(record);
    }

    private void valParams(${entity}DTO dto){
    }

    @Override
    public Boolean removeBy${keyPropertyName?cap_first}(String ${keyPropertyName?uncap_first}){
        if (StrUtil.isEmpty(${keyPropertyName?uncap_first})){
            throw new BaseException(CommonErrorCodes.MISS_PARAMS);
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
