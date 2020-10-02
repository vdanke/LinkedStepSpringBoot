package org.step.linked.step.util;

import org.springframework.stereotype.Component;
import org.step.linked.step.entity.projection.IdProjection;
import org.step.linked.step.repository.LinkedCommonRepository;

@Component
public class DbHelper<T> {

    public Long generateId(LinkedCommonRepository<T> commonRepository) {
        final long startId = 1L;
        long newId;

        IdProjection maxId = commonRepository.findTopByOrderByIdDesc();

        if (maxId == null) {
            newId = startId;
        } else {
            newId = maxId.getId();
            ++newId;
        }
        return newId;
    }
}
