package org.step.linked.step.service;

import org.step.linked.step.dto.request.UpdateRequest;
import org.step.linked.step.entity.User;

import java.util.List;

public interface CrudService<T, ID> {

    T save(T t);

    List<T> findAll();

    T findById(ID id);

    T find(T t);

    void delete(User user);

    void delete(ID id);

    T update(ID id, UpdateRequest request);
}
