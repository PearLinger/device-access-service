package ${basePackage}.converts;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ${entityPackage}.${entity};
import ${package.Controller}.vo.*;
import java.util.List;
import com.voxel.dance.common.pojo.PageResult;
import ${boPackage}.${entity}BO;

/**
* <p>
  * ${table.comment!} 服务实现类
  * </p>
*
* @author ${author}
* @since ${date}
*/

@Mapper
public interface ${entity}Convert {
     ${entity}Convert INSTANCE = Mappers.getMapper(${entity}Convert.class);

      @Mapping(source = "records", target = "list")
      PageResult<${entity}RespVO> pageEntityConvertToVO(Page<${entity}> pageInfo);

      ${entity}BO reqVOConvertToBO(${entity}ReqVO reqVO);

      ${entity}RespVO entityConvertToRespVO(${entity} entity);

      List<${entity}RespVO> listEntitysConvertToVO(List<${entity}> entitys);

      ${entity} reqVOConvertToEntity(${entity}ReqVO reqVO);

  }
