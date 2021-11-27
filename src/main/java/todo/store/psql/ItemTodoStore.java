package todo.store.psql;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import todo.model.ItemTodo;
import todo.store.Store;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

public class ItemTodoStore implements Store<ItemTodo> {
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
                .setParameter("id", id)
                .executeUpdate() == 1
        );
    }

    @Override
    public Collection<ItemTodo> findAll() {
        return this
                .txt(session -> session.createQuery("from ItemTodo order by created desc ").list());
    }

    @Override
    public ItemTodo findById(String id) {
        return this.txt(session -> session.get(ItemTodo.class, Integer.parseInt(id)));
    }

    @Override
    public Collection<ItemTodo> executeSelect(String query, Map<String, Object> params) {
        return this.txt(session -> createCustomQuery(query, params, session).list());
    }

    @Override
    public boolean executeUpdate(String query, Map<String, Object> params) {
        return this.txt(session -> createCustomQuery(query, params, session).executeUpdate() == 1);
    }

    private Query createCustomQuery(String query, Map<String, Object> params, Session session) {
        Query q = session.createQuery(query);
        for (String key : params.keySet()) {
            q.setParameter(key, params.get(key));
        }
        return q;
    }

    private <T> T txt(final Function<Session, T> command) {
        final Session session = PsqlConnectionManager.instOf().getSf().openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}
