package org.step.linked.step.util;

import org.springframework.stereotype.Component;
import org.step.linked.step.repository.LinkedCommonRepository;

@Component
public class DbHelper<T> {

    public Long generateId(LinkedCommonRepository<T> commonRepository) {
        final long startId = 1L;

        Long maxId = commonRepository.findMaxId();

        if (maxId == null) {
            maxId = startId;
        } else {
            ++maxId;
        }
        return maxId;
    }
}
