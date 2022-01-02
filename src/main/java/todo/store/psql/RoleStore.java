package todo.store.psql;

import todo.role.Role;
import todo.store.Store;

import java.sql.SQLException;
import java.util.Collection;

public class RoleStore extends PsqlStore<Role> {

    private RoleStore() {
    }

    private static final class Lazy {
        private static final Store<Role> INST = new RoleStore();
    }

    public static Store<Role> instOf() {
        return Lazy.INST;
    }

    @Override
    public Role add(Role role) throws SQLException {
        return this.txt(session -> {
            Integer id = (Integer) session.save(role);
            role.setId(id);
            return role;
        });
    }

    @Override
    public boolean replace(String id, Role role) {
        return this.txt(session ->
                session.createQuery(
                        "update Role set name=:name where id=:id")
                        .setParameter("name", role.getName())
                        .setParameter("id", Integer.parseInt(id))
                        .executeUpdate() == 1
        );
    }

    @Override
    public boolean delete(String id) {
        return this.txt(session -> session.createQuery("delete from Role where id =:id")
                .setParameter("id", Integer.parseInt(id))
                .executeUpdate() == 1
        );
    }

    @Override
    public Collection<Role> findAll() {
        return this
                .txt(session -> session.createQuery("from Role order by id").list());
    }

    @Override
    public Role findById(String id) {
        return findById(id, Role.class);
    }
}
