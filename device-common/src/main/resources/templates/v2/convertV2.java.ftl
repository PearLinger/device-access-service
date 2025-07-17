package ${package.Service};

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ${package.Entity}.${entity};
import ${dtoPackage}.${entity}DTO;
import ${voPackage}.${entity}VO;
import java.util.List;


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
  }
