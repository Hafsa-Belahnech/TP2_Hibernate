package com.example.service;

import java.util.List;
import java.util.Optional;

public interface CrudService<T, ID> {
    T save(T entity);
    Optional<T> findById(ID id);
    List<T> findAll();
    void update(T entity);
    void delete(T entity);
    void deleteById(ID id);
}
