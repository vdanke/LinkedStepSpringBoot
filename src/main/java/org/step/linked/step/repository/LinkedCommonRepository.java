package org.step.linked.step.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.step.linked.step.entity.projection.IdProjection;

@NoRepositoryBean
public interface LinkedCommonRepository<T> extends JpaRepository<T, Long> {

    IdProjection findTopByOrderByIdDesc();
}
