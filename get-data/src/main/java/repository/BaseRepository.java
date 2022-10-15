package repository;

import java.util.List;

public interface BaseRepository<T> {
    List<T> findAll();

    T findById(int id);

    void save(T t);

    void delete(int id);
}
