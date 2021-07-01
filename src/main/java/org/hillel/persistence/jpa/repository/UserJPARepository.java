package org.hillel.persistence.jpa.repository;


import org.hillel.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface UserJPARepository extends CommonRepository<UserEntity,Long>, JpaSpecificationExecutor<UserEntity> {

    List<UserEntity> findByEmail(String email);
}
