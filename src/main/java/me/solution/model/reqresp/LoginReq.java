package me.solution.model.reqresp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * login request model
 *
 * @author davincix
 * @since 2023/5/20 15:47
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginReq {

    private String name;

    private String passwd;
}
