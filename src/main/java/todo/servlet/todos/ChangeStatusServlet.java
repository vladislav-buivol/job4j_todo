package todo.servlet.todos;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import todo.store.psql.ItemTodoStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

public class ChangeStatusServlet extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(ChangeStatusServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String id = req.getParameter("id");
        try {
            HashMap<String, Object> params = new HashMap<>();
            params.put("id", Integer.parseInt(id));
            boolean result = ItemTodoStore.instOf()
                    .executeUpdate(
                            "update ItemTodo set " +
                                    " done = (select (done != true) from ItemTodo where id=:id)" +
                                    " where id=:id",
                            params);
            if (!result) {
                LOGGER.warn("Object was not updated");
                resp.setStatus(409);
            }
        } catch (Exception e) {
            resp.setStatus(409);
            LOGGER.error(e);
            e.printStackTrace();
        }
        resp.sendRedirect(req.getContextPath());
    }

}
