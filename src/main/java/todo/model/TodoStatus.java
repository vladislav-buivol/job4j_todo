package todo.model;

import java.util.HashMap;
import java.util.Map;

public enum TodoStatus {
    DONE("done", true),
    IN_PROGRESS("in_progress", false);

    private final String val;
    private final boolean status;

    TodoStatus(String val, boolean status) {
        this.val = val;
        this.status = status;
    }

    public String getVal() {
        return val;
    }

    private static Map<String, TodoStatus> STATUSES = null;

    public static boolean getStatus(String name) {
        if (STATUSES == null) {
            STATUSES = new HashMap<>();
            for (TodoStatus status : TodoStatus.values()) {
                STATUSES.put(status.getVal().toLowerCase(), status);
            }
        }
        return STATUSES.get(name).status;
    }
}

