package com.elegoo.cloud.device.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.elegoo.cloud.device.entity.TAppVersionInfo;
import com.elegoo.cloud.device.api.dto.TAppVersionInfoDTO;
import com.elegoo.cloud.device.api.vo.TAppVersionInfoVO;
import java.util.List;
import com.elegoo.framework.common.pojo.PageResult;

/**
* <p>
  *  服务实现类
  * </p>
*
* @author yangyi
* @since 2025-07-19
*/

@Mapper(componentModel = "spring")
public interface TAppVersionInfoConvert {
      @Mapping(source = "records", target = "list")
      PageResult<TAppVersionInfoVO> pageEntityConvertToVO(Page<TAppVersionInfo> pageInfo);

      TAppVersionInfo dtoConvertToEntity(TAppVersionInfoDTO dto);

      TAppVersionInfoVO entityConvertToVO(TAppVersionInfo entity);

      List<TAppVersionInfoVO> entitysConvertToVO(List<TAppVersionInfo> entitys);
  }
