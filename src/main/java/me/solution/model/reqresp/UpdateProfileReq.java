package me.solution.model.reqresp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * update profile request
 *
 * @author davincix
 * @since 2023/5/20 15:47
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProfileReq {

    private String address;

    private String description;
}
