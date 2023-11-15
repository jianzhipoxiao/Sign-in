package com.jszx.utils;

import com.jszx.pojo.User;
import com.jszx.pojo.vo.SignUser;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Data
public class Room {
    private List<SignUser> onlineUser = new ArrayList<>();
    private List<User> getKey = new ArrayList<>();
}
