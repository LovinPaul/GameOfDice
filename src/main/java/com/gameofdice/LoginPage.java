package com.gameofdice;

import com.gameofdice.data.MySQLData;
import com.gameofdice.data.Persistence;
import com.gameofdice.users.User;
import com.gameofdice.users.sessions.SessionListener;
import com.gameofdice.users.sessions.UserSession;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import static com.gameofdice.util.Util.InvalidSession;

public class LoginPage extends HttpServlet {

    private final Persistence dataPersistence = MySQLData.GetINSTACE();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (InvalidSession(request)) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/view/login.jsp");
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect("game");
        }
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getParameter("login") != null) {
            User loginUser = dataPersistence.getUser(request.getParameter("user"));
            if (loginUser != null && loginUser.isPasswordValid(request.getParameter("password"))) {
                createNewSession(request, loginUser);
                response.sendRedirect("game");
            } else {
                response.sendRedirect("login?invalid=login");
            }
        }

        if (request.getParameter("logout") != null) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
        }
        
        if (request.getParameter("register") != null) {
            User user = dataPersistence.getUser(request.getParameter("user"));
            if (user != null) {
                response.sendRedirect("login?invalid=register");
            } else {
                User newUser = new User(request.getParameter("user"), request.getParameter("password"));
                dataPersistence.saveObj(newUser);
                createNewSession(request, newUser);
                response.sendRedirect("game");
            }
        }
    }

    private void createNewSession(HttpServletRequest request, User user) {
        UserSession userSession = new UserSession(user);
        dataPersistence.saveObj(userSession);
        HttpSession session = request.getSession();
        SessionListener.AddSession(session);
        session.setAttribute("user", user);
        session.setAttribute("userSession", userSession);
    }
}
