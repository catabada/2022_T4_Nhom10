package dao;

import java.util.List;

public interface BaseDAO<T> {
    List<T> findAll();

    T findById(int id);

    void save(T t);

    void delete(int id);
}
