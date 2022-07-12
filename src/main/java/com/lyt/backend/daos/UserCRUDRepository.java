package com.lyt.backend.daos;

import com.lyt.backend.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserCRUDRepository extends CrudRepository<User, Integer> {
}
