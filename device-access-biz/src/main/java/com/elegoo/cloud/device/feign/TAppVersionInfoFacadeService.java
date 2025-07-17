package com.elegoo.cloud.device.feign;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.elegoo.cloud.device.entity.TAppVersionInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.elegoo.cloud.device.api.dto.TAppVersionInfoDTO;
import com.elegoo.cloud.device.api.vo.TAppVersionInfoVO;
import com.elegoo.cloud.device.api.query.TAppVersionInfoQuery;
import com.elegoo.cloud.device.service.TAppVersionInfoService;
import com.elegoo.cloud.device.api.TAppVersionInfoFacade;
import com.rinoiot.base.model.QueryPageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * <p>
 *  Facade实现
 * </p>
 *
 * @author yangyi
 * @since 2025-07-17
 */
@RestController
@RequiredArgsConstructor
public class TAppVersionInfoFacadeService implements TAppVersionInfoFacade {

    private final TAppVersionInfoService tAppVersionInfoService;

    @Override
    public Page<TAppVersionInfoVO> queryPage(QueryPageDTO<TAppVersionInfoQuery> pageDTO){
        return tAppVersionInfoService.queryPage(pageDTO);
    }

    @Override
    public List<TAppVersionInfoVO> queryList(TAppVersionInfoQuery query){
        return tAppVersionInfoService.queryList(query);
    }

    @Override
    public TAppVersionInfoVO getById(String id){
        return tAppVersionInfoService.getById(id);
    }

    @Override
    public Boolean add(TAppVersionInfoDTO dto){
        return tAppVersionInfoService.add(dto);
    }

    @Override
    public Boolean updateById(TAppVersionInfoDTO dto){
        return tAppVersionInfoService.updateById(dto);
    }

    @Override
    public Boolean removeById(String id){
        return tAppVersionInfoService.removeById(id);
    }
}
