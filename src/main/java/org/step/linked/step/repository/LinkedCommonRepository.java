package org.step.linked.step.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface LinkedCommonRepository<T> extends JpaRepository<T, Long> {

    @Query(nativeQuery = true, value = "SELECT ID " +
            "FROM #{#entityName} \n" +
            "ORDER BY id DESC \n" +
            "LIMIT 1")
    Long findMaxId();
}
