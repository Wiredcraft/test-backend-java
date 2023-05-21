package me.solution.model.reqresp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * signup request model
 *
 * @author davincix
 * @since 2023/5/20 15:47
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpReq {

    /**
     * username
     */
    private String name;

    /**
     * password for login
     */
    private String passwd;

    /**
     * date of birth
     */
    private Date dob;

    private String address;

    private String description;
}
