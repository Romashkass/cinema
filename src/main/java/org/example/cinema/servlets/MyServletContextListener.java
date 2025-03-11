package org.example.cinema.servlets;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.example.cinema.infrasturcture.core.impl.ApplicationContext;

import java.util.HashMap;

@WebListener
public class MyServletContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ApplicationContext ctx = new ApplicationContext("org.example.cinema", new HashMap<>());
        sce.getServletContext().setAttribute("ctx", ctx);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
