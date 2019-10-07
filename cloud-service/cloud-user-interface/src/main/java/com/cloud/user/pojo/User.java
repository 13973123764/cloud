package com.cloud.user.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author zf
 * @date 2019-10-05-12:13
 */
@Data
@Table(name = "tb_user")
public class User {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    private String username;
    @JsonIgnore
    private String password;
    private String phone;
    private Date created;
    @JsonIgnore
    private String salt;













}
