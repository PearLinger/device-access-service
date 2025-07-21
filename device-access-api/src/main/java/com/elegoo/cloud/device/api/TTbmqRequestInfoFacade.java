package com.elegoo.cloud.device.api;


import com.elegoo.cloud.device.api.dto.TTbmqRequestInfoDTO;
import com.elegoo.cloud.device.api.vo.TTbmqRequestInfoVO;
import com.elegoo.cloud.device.api.query.TTbmqRequestInfoQuery;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import com.elegoo.framework.common.pojo.PageResult;

/**
 * <p>
 *  Facade
 * </p>
 *
 * @author yangyi
 * @since 2025-07-19
 */
@Service
@FeignClient(contextId = "TTbmqRequestInfoFacade")
public interface TTbmqRequestInfoFacade {

    @PostMapping(value = "/inside/v1/ttbmq/request/info/queryPage")
    PageResult<TTbmqRequestInfoVO> queryPage(@RequestBody TTbmqRequestInfoQuery pageDTO);

    @PostMapping(value = "/inside/v1/ttbmq/request/info/queryList")
    List<TTbmqRequestInfoVO> queryList(@RequestBody TTbmqRequestInfoQuery query);

    @PostMapping(value = "/inside/v1/ttbmq/request/info/getById")
    TTbmqRequestInfoVO getById(@RequestParam("id") Long id);

    @PostMapping(value = "/inside/v1/ttbmq/request/info/add")
    Boolean add(@RequestBody TTbmqRequestInfoDTO dto);

    @PostMapping(value = "/inside/v1/ttbmq/request/info/updateById")
    Boolean updateById(@RequestBody TTbmqRequestInfoDTO dto);

    @PostMapping(value = "/inside/v1/ttbmq/request/info/removeById")
    Boolean removeById(@RequestParam("id") Long id);
}
