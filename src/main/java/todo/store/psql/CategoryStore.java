package todo.store.psql;

import todo.model.Category;
import todo.store.Store;

import java.sql.SQLException;
import java.util.List;

public class CategoryStore extends PsqlStore<Category> {

    private CategoryStore() {
    }

    private static final class Lazy {
        private static final Store<Category> INST = new CategoryStore();
    }

    public static Store<Category> instOf() {
        return Lazy.INST;
    }

    @Override
    public Category add(Category category) throws SQLException {
        return this.txt(session -> {
            Integer id = (Integer) session.save(category);
            category.setId(id);
            return category;
        });
    }

    @Override
    public boolean replace(String id, Category category) {
        return this.txt(session ->
                session.createQuery(
                        "update Category set name=:name where id=:id")
                        .setParameter("name", category.getName())
                        .setParameter("id", Integer.parseInt(id))
                        .executeUpdate() == 1
        );
    }

    @Override
    public boolean delete(String id) {
        return this.txt(session -> session.createQuery("delete from Category where id =:id")
                .setParameter("id", Integer.parseInt(id))
                .executeUpdate() == 1
        );
    }

    @Override
    public List<Category> findAll() {
        return this
                .txt(session -> session.createQuery("from Category order by id").list());
    }

    @Override
    public Category findById(String id) {
        return super.findById(id, Category.class);
    }
}
