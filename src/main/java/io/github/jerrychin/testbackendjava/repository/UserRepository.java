package io.github.jerrychin.testbackendjava.repository;

import io.github.jerrychin.testbackendjava.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {
}
