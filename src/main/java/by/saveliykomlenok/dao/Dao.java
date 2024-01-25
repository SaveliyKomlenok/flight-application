package by.saveliykomlenok.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<N, T> {
    List<N> findAll();
    Optional<N> findById(T t);
    N save(N n);
    boolean update(N n);
    boolean delete(T t);
}
