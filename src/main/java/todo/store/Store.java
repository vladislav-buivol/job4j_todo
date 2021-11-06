package todo.store;


import java.util.Collection;

public interface Store<T> {
    T add(T t);

    boolean replace(String id, T t);

    boolean delete(String id);

    Collection<T> findAll();

    T findById();

}
