package todo.store.psql;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import todo.store.Store;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

public abstract class PsqlStore<T> implements Store<T> {

    public <T> T txt(final Function<Session, T> command) {
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

    @Override
    public Collection<T> executeSelect(String query, Map<String, Object> params) {
        return this.txt(session -> createCustomQuery(query, params, session).list());
    }

    @Override
    public boolean executeUpdate(String query, Map<String, Object> params) {
        return this.txt(session -> createCustomQuery(query, params, session).executeUpdate() == 1);
    }

    public T findById(String id, Class<T> tClass) {
        return this.txt(session -> session.get(tClass, Integer.parseInt(id)));
    }

    public Query createCustomQuery(String query, Map<String, Object> params, Session session) {
        Query q = session.createQuery(query);
        for (String key : params.keySet()) {
            q.setParameter(key, params.get(key));
        }
        return q;
    }

}
