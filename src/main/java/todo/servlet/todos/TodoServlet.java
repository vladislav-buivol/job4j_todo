package todo.servlet.todos;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import todo.model.Category;
import todo.store.psql.CategoryStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class TodoServlet extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(TodoServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        LOGGER.info("Handling post request");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        LOGGER.info("Handling get request");
        List<Category> categories = (List<Category>) CategoryStore.instOf().findAll();
        req.getSession().setAttribute("allCatigories", categories);
        resp.sendRedirect(req.getContextPath() + "/index.jsp");
    }
}
