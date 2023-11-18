package com.jszx.pojo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author 刘林
 * @version 1.0
 * @caeateDate
 * @description
 */
@Data
public class AdminUserDto {
    @NotNull
    private String username;
    @NotNull
    private String password;
}
