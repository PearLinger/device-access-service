package com.elegoo.cloud.device.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.elegoo.cloud.device.entity.TAppVersionInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.elegoo.cloud.device.api.dto.TAppVersionInfoDTO;
import com.elegoo.cloud.device.api.vo.TAppVersionInfoVO;
import com.elegoo.cloud.device.api.query.TAppVersionInfoQuery;
import com.rinoiot.base.model.QueryPageDTO;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yangyi
 * @since 2025-07-17
 */
public interface TAppVersionInfoService extends IService<TAppVersionInfo> {

    Page<TAppVersionInfoVO> queryPage(QueryPageDTO<TAppVersionInfoQuery> pageDTO);

    List<TAppVersionInfoVO> queryList(TAppVersionInfoQuery query);

    TAppVersionInfoVO getById(String id);

    Boolean add(TAppVersionInfoDTO dto);

    Boolean updateById(TAppVersionInfoDTO dto);

    Boolean removeById(String id);
}
