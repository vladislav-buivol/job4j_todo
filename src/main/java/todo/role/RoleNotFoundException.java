package todo.role;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(Object o) {
        super(String.format("Role %s was not found", o.toString()));
    }

    public RoleNotFoundException(String message) {
        super(message);
    }
}
