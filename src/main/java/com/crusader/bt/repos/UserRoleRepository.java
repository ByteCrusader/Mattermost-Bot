package com.crusader.bt.repos;

import com.crusader.bt.entity.UserRoleEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends ReactiveCrudRepository<UserRoleEntity, String> {

}
