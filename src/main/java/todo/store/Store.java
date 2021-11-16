package todo.store;

import java.sql.SQLException;
import java.util.Collection;

public interface Store<T> {
    T add(T t) throws SQLException;

    boolean replace(String id, T t);

    boolean delete(String id);

    Collection<T> findAll();

    T findById(String id);
}
