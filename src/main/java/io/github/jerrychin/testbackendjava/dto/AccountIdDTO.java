package io.github.jerrychin.testbackendjava.dto;


import io.swagger.annotations.ApiModelProperty;

public class AccountIdDTO {

    @ApiModelProperty(required = true, example = "1000")
    private Long accountId;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
}
