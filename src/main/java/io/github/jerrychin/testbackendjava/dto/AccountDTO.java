package io.github.jerrychin.testbackendjava.dto;

public class AccountDTO {

    /**
     * unique user name.
     */
    private String account;

    /**
     * user plain password.
     */
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
