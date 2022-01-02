package todo.store.psql;

import todo.model.User;
import todo.store.Store;

import java.sql.SQLException;
import java.util.Collection;

public class UserStore extends PsqlStore<User> {

    private UserStore() {
    }

    private static final class Lazy {
        private static final Store<User> INST = new UserStore();
    }

    public static Store<User> instOf() {
        return Lazy.INST;
    }

    @Override
    public User add(User user) throws SQLException {
        return this.txt(session -> {
            Integer id = (Integer) session.save(user);
            user.setId(id);
            return user;
        });
    }

    @Override
    public boolean replace(String id, User user) {
        return this.txt(session ->
                session.createQuery(
                        "update User set name=:name, role=:role where id=:id")
                        .setParameter("name", user.getName())
                        .setParameter("role", user.getRole())
                        .setParameter("id", Integer.parseInt(id))
                        .executeUpdate() == 1
        );
    }

    @Override
    public boolean delete(String id) {
        return this.txt(session -> session.createQuery("delete from User where id =:id")
                .setParameter("id", Integer.parseInt(id))
                .executeUpdate() == 1
        );
    }

    @Override
    public Collection<User> findAll() {
        return this
                .txt(session -> session.createQuery("from User order by id").list());
    }

    @Override
    public User findById(String id) {
        return findById(id, User.class);
    }
}
