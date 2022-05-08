package io.github.jerrychin.testbackendjava.repository;

import io.github.jerrychin.testbackendjava.entity.Account;
import io.github.jerrychin.testbackendjava.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, Long> {
    boolean existsAccountByAccount(String account);

    Optional<Account> findAccountByAccount(String account);

}
