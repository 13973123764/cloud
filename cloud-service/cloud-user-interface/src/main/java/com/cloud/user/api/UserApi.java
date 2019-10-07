package com.cloud.user.api;

import com.cloud.user.pojo.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author zf
 * @date 2019-10-05-19:25
 */
public interface UserApi {

    @GetMapping("user/query")
    public User queryUser(@RequestParam("username") String username, @RequestParam("password") String password);

}
