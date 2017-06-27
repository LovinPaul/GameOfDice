package com.gameofdice;

import com.gameofdice.data.MySQLData;
import com.gameofdice.data.Persistence;
import com.gameofdice.games.Game;
import com.gameofdice.users.User;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import static com.gameofdice.util.Util.InvalidSession;
import java.util.List;

public class GamePage extends HttpServlet {

    private final Persistence dataPersistence = MySQLData.GetINSTACE();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (InvalidSession(request)) {
            response.sendRedirect("login?invalid=session");
        } else {
            request.getSession().setAttribute("lastGame", getLastGame(request));
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/view/game.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (InvalidSession(request)) {
            response.sendRedirect("login?invalid=session");
        } else {
            if (request.getParameter("newGame") != null) {
                HttpSession session = request.getSession();
                User user = (User) session.getAttribute("user");
                Game game = new Game(user, System.currentTimeMillis());
                game.throwDice();
                dataPersistence.saveObj(game);
                session.setAttribute("currentGame", game);
                response.sendRedirect(request.getRequestURL().toString());
            }
        }
    }

    private Game getLastGame(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Game lastGame = null;
        Game currentGame = (Game) session.getAttribute("currentGame");
        if (currentGame == null) {
            List<Game> games = dataPersistence.getLastGames(user.getUserName(), 1);
            if (games != null && games.size() > 0) {
                lastGame = games.get(0);
            }
        } else {
            List<Game> games = dataPersistence.getLastGames(user.getUserName(), 2);
            if (games != null && games.size() > 1) {
                lastGame = games.get(1);
            }
        }
        return lastGame;
    }

}
