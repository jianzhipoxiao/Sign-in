package com.jszx.pojo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author 刘林
 * @version 1.0
 */
@Data
public class TestUser {

    @JsonProperty("userName")
    private String username;
    @JsonProperty("passWord")
    private String password;
}
