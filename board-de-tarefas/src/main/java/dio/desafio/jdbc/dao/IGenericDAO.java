package dio.desafio.jdbc.dao;

import java.util.List;
import java.util.Optional;

public interface IGenericDAO<T, U>{

    T save(T entity);

    Optional<T> findById(U id);

    boolean existsById(U id);

    boolean existsByName(String name);

    boolean existsByNameExcludingId(String name, U id);

    List<T> findAll();

    T update(T entity);

    void delete(U id);

}
