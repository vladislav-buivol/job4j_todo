package todo.store.psql;

import todo.model.ItemTodo;
import todo.store.Store;

import java.sql.SQLException;
import java.util.Collection;

public class ItemTodoStore extends PsqlStore<ItemTodo> {
    private ItemTodoStore() {
    }

    private static final class Lazy {
        private static final Store<ItemTodo> INST = new ItemTodoStore();
    }

    public static Store<ItemTodo> instOf() {
        return Lazy.INST;
    }

    @Override
    public ItemTodo add(ItemTodo itemTodo) throws SQLException {
        return this.txt(session -> {
            Integer id = (Integer) session.save(itemTodo);
            itemTodo.setId(id);
            return itemTodo;
        });
    }

    @Override
    public boolean replace(String id, ItemTodo itemTodo) {
        return this.txt(session ->
                session.createQuery(
                        "update ItemTodo set description=:desc, done=:status where id=:id")
                        .setParameter("desc", itemTodo.getDescription())
                        .setParameter("status", itemTodo.isDone())
                        .setParameter("id", Integer.parseInt(id))
                        .executeUpdate() == 1
        );

    }

    @Override
    public boolean delete(String id) {
        return this.txt(session -> session.createQuery("delete from ItemTodo where id =:id")
                .setParameter("id", Integer.parseInt(id))
                .executeUpdate() == 1
        );
    }

    @Override
    public Collection<ItemTodo> findAll() {
        return this
                .txt(session -> session
                        .createQuery(
                                "select distinct it from ItemTodo it left join fetch it.categories"
                                        + " order by it.created desc")
                        .list());
    }

    @Override
    public ItemTodo findById(String id) {
        return (ItemTodo) this.txt(session -> session
                .createQuery(
                        "select distinct it from ItemTodo it left join fetch it.categories "
                                + "where it.id =:id"
                ).setParameter("id", Integer.parseInt(id))
                .list()).get(0);
    }
}
