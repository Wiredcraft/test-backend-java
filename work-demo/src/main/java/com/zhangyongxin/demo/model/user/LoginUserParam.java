package com.zhangyongxin.demo.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhangyongxin
 * @date 2022/3/18 14:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserParam {
    private String username;
    private String password;
}
