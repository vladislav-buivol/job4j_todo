package todo.store.psql;

import org.hibernate.Session;
import org.hibernate.query.Query;
import todo.model.ItemTodo;
import todo.store.Store;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class ItemTodoStore implements Store<ItemTodo> {
    private static ItemTodoStore instance;

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
        Session session = PsqlConnectionManager.instOf().getSf().openSession();
        session.beginTransaction();
        Integer id = (Integer) session.save(itemTodo);
        itemTodo.setId(id);
        session.getTransaction().commit();
        session.close();
        return itemTodo;
    }

    @Override
    public boolean replace(String id, ItemTodo itemTodo) {
        Session session = PsqlConnectionManager.instOf().getSf().openSession();
        session.beginTransaction();
        int result = session.createQuery(
                "update ItemTodo set description=:desc, done=:status where id=:id")
                .setParameter("desc", itemTodo.getDescription())
                .setParameter("status", itemTodo.isDone())
                .setParameter("id", Integer.parseInt(id))
                .executeUpdate();
        session.getTransaction().commit();
        return result == 1;
    }

    @Override
    public boolean delete(String id) {
        Session session = PsqlConnectionManager.instOf().getSf().openSession();
        session.beginTransaction();
        int resuls = session.createQuery("delete from ItemTodo where id =:id")
                .setParameter("id", id)
                .executeUpdate();
        session.getTransaction().commit();
        return resuls == 1;
    }

    @Override
    public Collection<ItemTodo> findAll() {
        Session session = PsqlConnectionManager.instOf().getSf().openSession();
        session.beginTransaction();
        List result = session.createQuery("from ItemTodo order by created desc ").list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public ItemTodo findById(String id) {
        Session session = PsqlConnectionManager.instOf().getSf().openSession();
        session.beginTransaction();
        ItemTodo itemTodo = session.get(ItemTodo.class, Integer.parseInt(id));
        session.getTransaction().commit();
        session.close();
        return itemTodo;
    }

    @Override
    public Collection<ItemTodo> executeSelect(String query, Map<String, Object> params) {
        Session session = PsqlConnectionManager.instOf().getSf().openSession();
        session.beginTransaction();
        Query q = createCustomQuery(query, params, session);
        List result = q.list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public boolean executeUpdate(String query, Map<String, Object> params) {
        Session session = PsqlConnectionManager.instOf().getSf().openSession();
        session.beginTransaction();
        Query q = createCustomQuery(query, params, session);
        int result = q.executeUpdate();
        session.getTransaction().commit();
        session.close();
        return result == 1;
    }

    private Query createCustomQuery(String query, Map<String, Object> params, Session session) {
        Query q = session.createQuery(query);
        for (String key : params.keySet()) {
            q.setParameter(key, params.get(key));
        }
        return q;
    }


}
