package io.github.jerrychin.testbackendjava.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "people_relation")
public class PeopleRelation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt;

    private Long followingAccountId;

    private Long currentAccountId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getFollowingAccountId() {
        return followingAccountId;
    }

    public void setFollowingAccountId(Long followingAccountId) {
        this.followingAccountId = followingAccountId;
    }

    public Long getCurrentAccountId() {
        return currentAccountId;
    }

    public void setCurrentAccountId(Long currentAccountId) {
        this.currentAccountId = currentAccountId;
    }
}
