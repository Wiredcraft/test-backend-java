package io.github.jerrychin.testbackendjava.repository;

import io.github.jerrychin.testbackendjava.model.entity.PeopleRelation;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PeopleRelationRepository extends CrudRepository<PeopleRelation, Long> {

    List<PeopleRelation> findPeopleRelationByCurrentAccountId(Long currentAccountId);

    List<PeopleRelation> findPeopleRelationByFollowingAccountId(Long followingAccountId);

    @Modifying
    @Query("delete from PeopleRelation where followingAccountId = :followingAccountId " +
            "and currentAccountId = :currentAccountId")
    void delete(@Param("currentAccountId") Long currentAccountId,
                @Param("followingAccountId") Long followingAccountId);
}
