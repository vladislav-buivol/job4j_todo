package todo.servlet.auth;

import todo.model.User;
import todo.store.psql.UserStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class AuthServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        HashMap<String, Object> params = new HashMap<>();
        params.put("email", email);
        List<User> users =
                (List<User>) UserStore.instOf()
                        .executeSelect("from User where email=:email", params);
        if (users.isEmpty()) {
            req.setAttribute("error", "User with this email was not found");
        } else {
            User user = users.get(0);
            if (user.validatePwd(password)) {
                createSession(req, resp, users);
            } else {
                req.setAttribute("error", "Invalid password");
            }
            return;
        }
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    private void createSession(HttpServletRequest req, HttpServletResponse resp, List<User> users)
            throws IOException {
        HttpSession sc = req.getSession();
        sc.setAttribute("user", users.get(0));
        resp.sendRedirect(req.getContextPath());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }
}
