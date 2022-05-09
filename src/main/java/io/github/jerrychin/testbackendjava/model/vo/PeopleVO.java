package io.github.jerrychin.testbackendjava.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("People")
public class PeopleVO {

    @ApiModelProperty(required = true, notes = "account id", example = "1000")
    private Long id;

    @ApiModelProperty(required = true, example = "Jerry Chin")
    private String name;

    @ApiModelProperty(notes = "distance between this two user, if no pos is given then distance will be null.", example = "2000")
    private Long distanceInMeters;

    @ApiModelProperty(notes = "is followed by current user, if guest user found then null")
    private Boolean followed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getDistanceInMeters() {
        return distanceInMeters;
    }

    public void setDistanceInMeters(Long distanceInMeters) {
        this.distanceInMeters = distanceInMeters;
    }

    public Boolean getFollowed() {
        return followed;
    }

    public void setFollowed(Boolean followed) {
        this.followed = followed;
    }
}
