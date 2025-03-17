package org.example.cinema.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.cinema.Courier;
import org.example.cinema.infrasturcture.core.impl.ApplicationContext;
import org.example.cinema.facade.MovieDTOFacade;

import java.io.IOException;

@WebServlet("/viewPlannedDiagnostic")
public class ViewPlannedDiagnosticServlet extends HttpServlet {
    private MovieDTOFacade movieService;
    private Courier courier;
    @Override
    public void init() throws ServletException {
        super.init();
        ApplicationContext ctx = (ApplicationContext) getServletContext().getAttribute("ctx");
        movieService = ctx.getObject(MovieDTOFacade.class);
        courier = ctx.getObject(Courier.class);
        courier.moviesToMaintenance(ctx);
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("movies", movieService.getAll());
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/jsp/viewPlannedDiagnosticJSP.jsp");
        dispatcher.forward(req, resp);
    }
}

