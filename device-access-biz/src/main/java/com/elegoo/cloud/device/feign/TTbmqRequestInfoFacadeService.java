package com.elegoo.cloud.device.feign;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.elegoo.cloud.device.entity.TTbmqRequestInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.elegoo.cloud.device.api.dto.TTbmqRequestInfoDTO;
import com.elegoo.cloud.device.api.vo.TTbmqRequestInfoVO;
import com.elegoo.cloud.device.api.query.TTbmqRequestInfoQuery;
import com.elegoo.cloud.device.service.TTbmqRequestInfoService;
import com.elegoo.cloud.device.api.TTbmqRequestInfoFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import com.elegoo.framework.common.pojo.PageResult;
import java.util.List;

/**
 * <p>
 *  Facade实现
 * </p>
 *
 * @author yangyi
 * @since 2025-07-19
 */
@RestController
@RequiredArgsConstructor
public class TTbmqRequestInfoFacadeService implements TTbmqRequestInfoFacade {

    private final TTbmqRequestInfoService tTbmqRequestInfoService;

    @Override
    public PageResult<TTbmqRequestInfoVO> queryPage(TTbmqRequestInfoQuery pageDTO){
        return tTbmqRequestInfoService.queryPage(pageDTO);
    }

    @Override
    public List<TTbmqRequestInfoVO> queryList(TTbmqRequestInfoQuery query){
        return tTbmqRequestInfoService.queryList(query);
    }

    @Override
    public TTbmqRequestInfoVO getById(Long id){
        return tTbmqRequestInfoService.getById(id);
    }

    @Override
    public Boolean add(TTbmqRequestInfoDTO dto){
        return tTbmqRequestInfoService.add(dto);
    }

    @Override
    public Boolean updateById(TTbmqRequestInfoDTO dto){
        return tTbmqRequestInfoService.updateById(dto);
    }

    @Override
    public Boolean removeById(Long id){
        return tTbmqRequestInfoService.removeById(id);
    }
}
