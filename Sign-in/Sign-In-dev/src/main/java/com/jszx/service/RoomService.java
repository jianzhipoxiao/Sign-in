package com.jszx.service;

import com.jszx.utils.Result;

public interface RoomService {
    //查询在工作室人员
    Result queryOnlineUser();

    //查询携带钥匙人员
    Result queryKey();
}
