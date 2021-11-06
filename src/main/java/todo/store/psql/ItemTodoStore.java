package todo.store.psql;

import todo.model.ItemTodo;
import todo.store.Store;

import java.util.Collection;

public class ItemTodoStore implements Store<ItemTodo> {
    @Override
    public ItemTodo add(ItemTodo itemTodo) {
        return null;
    }

    @Override
    public boolean replace(String id, ItemTodo itemTodo) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public Collection<ItemTodo> findAll() {
        return null;
    }

    @Override
    public ItemTodo findById() {
        return null;
    }
}
