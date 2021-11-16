package todo.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import todo.store.psql.ItemTodoStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class LoadTodosServlet extends HttpServlet {
    private static final Gson GSON = new GsonBuilder().create();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");
        OutputStream output = resp.getOutputStream();
        String todos = GSON.toJson(ItemTodoStore.getInstance().findAll());
        this.getClass().getClassLoader().getResource("/status/done.png");
        output.write(todos.getBytes(StandardCharsets.UTF_8));
        output.flush();
        output.close();
    }
}

