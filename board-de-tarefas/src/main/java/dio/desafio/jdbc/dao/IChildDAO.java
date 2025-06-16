package dio.desafio.jdbc.dao;

import java.util.List;

public interface IChildDAO <T, U>{

    List<T> findByParentId(U parentId);

    boolean existsByNameInParent(String name, U parentId);

}
