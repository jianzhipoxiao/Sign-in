package com.jszx.utils;

import com.jszx.pojo.User;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Data
public class Room {
    private List<User> onlineUser = new ArrayList<>();
    private List<User> getKey = new ArrayList<>();
}
