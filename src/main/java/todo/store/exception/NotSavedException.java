package todo.store.exception;

public class NotSavedException extends RuntimeException {
    public NotSavedException(Object o) {
        super(String.format("%s was not found", o.toString()));
    }

    public NotSavedException(String message) {
        super(message);
    }
}
