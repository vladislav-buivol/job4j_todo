package todo.servlet.category;

import todo.model.Category;
import todo.store.psql.CategoryStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddCategoryServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            CategoryStore.instOf().add(
                    Category.of((String) req.getParameter("name"))
            );
            resp.setStatus(200);
        } catch (Exception e) {
            resp.setStatus(409);
        }
    }
}
