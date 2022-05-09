package io.github.jerrychin.testbackendjava.repository;

import io.github.jerrychin.testbackendjava.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, String> {
    Optional<User> findUserByAccountId(Long accountId);

    List<User> findUserByAccountIdIn(List<Long> accountIdList);
}
