package org.step.linked.step.service;

import java.util.List;

public interface CrudService<T, ID> {

    T save(T t);

    List<T> findAll();

    T findById(ID id);

    T find(T t);

    void delete(T t);

    void deleteById(ID id);

    T update(ID id, T t);
}
