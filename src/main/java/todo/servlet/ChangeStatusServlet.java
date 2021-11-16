package todo.servlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import todo.model.ItemTodo;
import todo.store.psql.ItemTodoStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ChangeStatusServlet extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(ChangeStatusServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String id = req.getParameter("id");
        try {
            ItemTodo item = ItemTodoStore.getInstance().findById(id);
            item.setDone(!item.isDone());
            ItemTodoStore.getInstance().replace(id, item);
        } catch (Exception e) {
            resp.setStatus(409);
            LOGGER.error(e);
            e.printStackTrace();
        }
        resp.sendRedirect(req.getContextPath());
    }

}
