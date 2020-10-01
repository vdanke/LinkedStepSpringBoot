package org.step.linked.step.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.step.linked.step.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(nativeQuery = true, value = "SELECT ID " +
            "FROM USERS \n" +
            "ORDER BY id DESC \n" +
            "LIMIT 1")
    Long findMaxId();
}
