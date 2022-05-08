package io.github.jerrychin.testbackendjava.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class AccountDTO {

    /**
     * unique user name.
     */
    @ApiModelProperty(notes = "unique user account.")
    private String account;

    /**
     * user plain password.
     */
    @ApiModelProperty(notes = "user plain password.")
    private String password;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
