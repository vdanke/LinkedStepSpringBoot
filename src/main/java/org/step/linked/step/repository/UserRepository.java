package org.step.linked.step.repository;

import org.springframework.stereotype.Repository;
import org.step.linked.step.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends LinkedCommonRepository<User> {

    Optional<User> findByUsername(String username);
}
