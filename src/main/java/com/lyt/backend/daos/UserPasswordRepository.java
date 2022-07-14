package com.lyt.backend.daos;

import com.lyt.backend.models.UserPassword;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.Index;
import java.util.Optional;


public interface UserPasswordRepository extends CrudRepository<UserPassword, Integer> {
    Optional<UserPassword> findByName(String name);
    boolean existsByName(String name);
}
