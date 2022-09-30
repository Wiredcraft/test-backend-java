package com.coffee.user.domain.po;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
@Getter
@Setter
public abstract class BasePO implements Serializable {

    protected String id;

    protected Date createdAt;

    protected Date updatedAt;

    protected Boolean isDeleted;

}
