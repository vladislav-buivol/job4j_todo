package todo.store;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public interface Store<T> {
    T add(T t) throws SQLException;

    boolean replace(String id, T t);

    boolean delete(String id);

    Collection<T> findAll();

    T findById(String id);

    Collection<T> executeSelect(String query, Map<String, Object> params);

    boolean executeUpdate(String query, Map<String, Object> params);
}
