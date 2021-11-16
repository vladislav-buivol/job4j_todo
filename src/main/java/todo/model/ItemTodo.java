package todo.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "todos")
public class ItemTodo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String description;
    private Timestamp created;
    private boolean done;

    public ItemTodo(String description, boolean done) {
        this.description = description;
        this.done = done;
    }

    public ItemTodo() {
    }

    @PrePersist
    protected void onCreate() {
        created = new Timestamp(System.currentTimeMillis());
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
