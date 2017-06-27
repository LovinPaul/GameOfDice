package com.gameofdice.users.sessions;

import com.gameofdice.data.MySQLData;
import com.gameofdice.data.Persistence;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class SessionListener implements HttpSessionListener, ServletContextListener {

    private static boolean running = true;
    private static final List<HttpSession> APP_SESSIONS = Collections.synchronizedList(new ArrayList<HttpSession>());
    private static final int TO_MILLIS = 60000; //convert minutes to milis (60000 milliseconds = 1 minute)
    private static int SESSION_TIMEOUT;
    private final Persistence dataPersistence = MySQLData.GetINSTACE();
    private static final Thread SIT = new Thread() {
        long currentTime;

        @Override
        public void run() {
            while (running) {
                synchronized (APP_SESSIONS) {
                    currentTime = System.currentTimeMillis();
                    Iterator<HttpSession> it = APP_SESSIONS.iterator();
                    while (it.hasNext()) {
                        HttpSession session = it.next();
                        if (currentTime - session.getLastAccessedTime() > SESSION_TIMEOUT) {
                            session.invalidate();
                            it.remove();
                        }
                    }
                }
            }
        }
    };

    public static void AddSession(HttpSession session) {
        APP_SESSIONS.add(session);
    }

    //HttpSessionListener
    @Override
    public void sessionCreated(HttpSessionEvent hse) {}

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        UserSession userSession = (UserSession) se.getSession().getAttribute("userSession");
        userSession.endSession();
        dataPersistence.updateObject(userSession);
    }

    //ServletContextListener
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        SESSION_TIMEOUT = TO_MILLIS * Integer.parseInt(sce.getServletContext().getInitParameter("sessionTimeout"));
        SIT.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        running = false;
    }

}
