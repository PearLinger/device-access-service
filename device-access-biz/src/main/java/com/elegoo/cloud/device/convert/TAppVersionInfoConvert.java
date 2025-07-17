package com.elegoo.cloud.device.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.elegoo.cloud.device.entity.TAppVersionInfo;
import com.elegoo.cloud.device.dto.TAppVersionInfoDTO;
import com.elegoo.cloud.device.vo.TAppVersionInfoVO;
import java.util.List;


/**
* <p>
  *  服务实现类
  * </p>
*
* @author yangyi
* @since 2025-07-17
*/

  @Mapper(componentModel = "spring")
  public interface TAppVersionInfoConvert {
  @Mapping(source = "records", target = "list")
  PageResult<TAppVersionInfoVO> pageEntityConvertToVO(Page<TAppVersionInfo> pageInfo);
  }
