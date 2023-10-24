package com.jszx.pojo.vo;

import com.jszx.pojo.User;
import lombok.Data;

import java.util.Map;

@Data
public class room {
    private Map<String, User> onlineUser;
    private Map<String,User> getKey;
}
