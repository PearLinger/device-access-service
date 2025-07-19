package com.elegoo.cloud.device.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.elegoo.cloud.device.entity.TAppVersionInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.elegoo.cloud.device.api.dto.TAppVersionInfoDTO;
import com.elegoo.cloud.device.api.vo.TAppVersionInfoVO;
import com.elegoo.cloud.device.api.query.TAppVersionInfoQuery;
import java.util.List;
import com.elegoo.framework.common.pojo.PageResult;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yangyi
 * @since 2025-07-19
 */
public interface TAppVersionInfoService extends IService<TAppVersionInfo> {

    PageResult<TAppVersionInfoVO> queryPage(TAppVersionInfoQuery pageDTO);

    List<TAppVersionInfoVO> queryList(TAppVersionInfoQuery query);

    TAppVersionInfoVO getById(Long id);

    Boolean add(TAppVersionInfoDTO dto);

    Boolean updateById(TAppVersionInfoDTO dto);

    Boolean removeById(Long id);
}
