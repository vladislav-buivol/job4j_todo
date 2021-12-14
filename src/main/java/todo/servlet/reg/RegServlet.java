package todo.servlet.reg;

import todo.model.User;
import todo.role.RoleNotFoundException;
import todo.store.psql.RoleStore;
import todo.store.psql.UserStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

public class RegServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("reg.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        HashMap<String, Object> params = new HashMap<>();
        params.put("email", email);

        if (RoleStore.instOf().findById("1") == null) {
            throw new RoleNotFoundException("Role with id 1 was not found");
        }
        try {
            if (!UserStore.instOf()
                    .executeSelect("select id from User where email=:email", params).isEmpty()) {
                req.setAttribute("errMessage", "This email already in use");
                req.getRequestDispatcher("reg.jsp").forward(req, resp);
            } else {
                UserStore.instOf()
                        .add(User.of(name, email, password, RoleStore.instOf().findById("1")));

                req.getRequestDispatcher("login.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(409);
        }
    }
}
