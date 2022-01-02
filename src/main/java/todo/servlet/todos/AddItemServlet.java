package todo.servlet.todos;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import todo.model.ItemTodo;
import todo.model.TodoStatus;
import todo.model.User;
import todo.store.psql.CategoryStore;
import todo.store.psql.ItemTodoStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class AddItemServlet extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(AddItemServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        LOGGER.info("Adding item");
        String desc = req.getParameter("desc");
        boolean isDone = TodoStatus.getStatus(req.getParameter("status"));
        User author = (User) req.getSession().getAttribute("user");
        String[] cIds = req.getParameterValues("cIds[]");
        ItemTodo itemTodo = new ItemTodo(desc, isDone, author);
        for (String cId : cIds) {
            itemTodo.addCategory(CategoryStore.instOf().findById(cId));
        }
        try {
            ItemTodoStore.instOf().add(itemTodo);
            resp.setStatus(200);
        } catch (SQLException sqlException) {
            resp.setStatus(409);
            LOGGER.error(sqlException);
            sqlException.printStackTrace();
        }
        resp.sendRedirect(req.getContextPath());
    }
}
