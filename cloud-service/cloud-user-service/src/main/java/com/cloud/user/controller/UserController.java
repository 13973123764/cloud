package com.cloud.user.controller;

import com.cloud.user.pojo.User;
import com.cloud.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author zf
 * @date 2019-10-05-12:29
 */
@RestController
@RequestMapping("user")
@Api(description = "用户信息管理")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("check/{data}/{type}")
    public ResponseEntity<Boolean>  checkData(@PathVariable("data") String data, @PathVariable("type") Integer type){
        return ResponseEntity.ok(userService.checkData(data, type));
    }

    /**
     * 发送短信验证码
     * @param phone
     * @return
     */
    @ApiOperation(value = "发送短信验证码", httpMethod = "POST")
    @PostMapping("code")
    public ResponseEntity<Void> sendCode(@RequestParam("phone") String phone) throws InterruptedException {
        userService.sendCode(phone);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 注册用户
     * @param user  用户pojo
     * @param code
     * @return
     */
    @ApiOperation(value = "注册用户", httpMethod = "POST")
    @PostMapping("registry")
    public ResponseEntity<Void> registry(User user, @RequestParam("code") String code){
        userService.registry(user,code);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 查询用户
     * @param username  用户名
     * @param password  用户密码
     * @return
     */
    @ApiOperation(value = "查询用户", httpMethod = "GET")
    @GetMapping("query")
    public ResponseEntity<User> queryUser(@RequestParam("username") String username, @RequestParam("password") String password){
        return ResponseEntity.ok(userService.queryUser(username, password));
    }


}



















