package com.lyt.backend.daos;

import com.lyt.backend.models.UserPersonalInfo;
import org.springframework.data.repository.CrudRepository;

public interface UserInfoRepository extends CrudRepository<UserPersonalInfo, Integer> {
}
