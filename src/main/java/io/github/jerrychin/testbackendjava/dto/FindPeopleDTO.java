package io.github.jerrychin.testbackendjava.dto;

public class FindPeopleDTO {

    private Long currentAccountId;

    private Boolean nearby = true;

    public Boolean getNearby() {
        return nearby;
    }

    public void setNearby(Boolean nearby) {
        this.nearby = nearby;
    }

    public Long getCurrentAccountId() {
        return currentAccountId;
    }

    public void setCurrentAccountId(Long currentAccountId) {
        this.currentAccountId = currentAccountId;
    }
}
