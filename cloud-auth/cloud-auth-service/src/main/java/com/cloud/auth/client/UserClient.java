package com.cloud.auth.client;

import com.cloud.user.api.UserApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author zf
 * @date 2019-10-05-19:32
 */
@FeignClient("user-service")
public interface UserClient extends UserApi {



}
