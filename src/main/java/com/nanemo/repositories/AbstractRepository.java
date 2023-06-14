package com.nanemo.repositories;

import java.util.List;

public interface AbstractRepository<T> {

    List<T> getAll();
    T getById(Integer id);
    void create(T t);
    void update(T t, Integer id);
    void delete(Integer id);

}
