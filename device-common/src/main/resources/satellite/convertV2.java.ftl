package ${package.Service};

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ${package.Entity}.${entity};
import ${dtoApiPackage}.${entity}DTO;
import ${voApiPackage}.${entity}VO;
import java.util.List;
import com.voxel.dance.common.pojo.PageResult;

/**
* <p>
  * ${table.comment!} 服务实现类
  * </p>
*
* @author ${author}
* @since ${date}
*/

@Mapper(componentModel = "spring")
public interface ${entity}Convert {
      @Mapping(source = "records", target = "list")
      PageResult<${entity}VO> pageEntityConvertToVO(Page<${entity}> pageInfo);

      ${entity} dtoConvertToEntity(${entity}DTO dto);

      ${entity}VO entityConvertToVO(${entity} entity);

      List<${entity}VO> entitysConvertToVO(List<${entity}> entitys);
  }
