package com.elegoo.cloud.device.api;


import com.elegoo.cloud.device.api.dto.TAppVersionInfoDTO;
import com.elegoo.cloud.device.api.vo.TAppVersionInfoVO;
import com.elegoo.cloud.device.api.query.TAppVersionInfoQuery;
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
@FeignClient(contextId = "TAppVersionInfoFacade")
public interface TAppVersionInfoFacade {

    @PostMapping(value = "/inside/v1/tapp/version/info/queryPage")
    PageResult<TAppVersionInfoVO> queryPage(@RequestBody TAppVersionInfoQuery pageDTO);

    @PostMapping(value = "/inside/v1/tapp/version/info/queryList")
    List<TAppVersionInfoVO> queryList(@RequestBody TAppVersionInfoQuery query);

    @PostMapping(value = "/inside/v1/tapp/version/info/getById")
    TAppVersionInfoVO getById(@RequestParam("id") Long id);

    @PostMapping(value = "/inside/v1/tapp/version/info/add")
    Boolean add(@RequestBody TAppVersionInfoDTO dto);

    @PostMapping(value = "/inside/v1/tapp/version/info/updateById")
    Boolean updateById(@RequestBody TAppVersionInfoDTO dto);

    @PostMapping(value = "/inside/v1/tapp/version/info/removeById")
    Boolean removeById(@RequestParam("id") Long id);
}
