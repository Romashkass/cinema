package org.example.cinema.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.cinema.infrasturcture.core.impl.ApplicationContext;
import org.example.cinema.facade.MovieDTOFacade;
import org.example.cinema.facade.RentDTOFacade;

import java.io.IOException;

@WebServlet("/viewReport")
public class ViewReportServlet extends HttpServlet {
    private MovieDTOFacade movieService;
    private RentDTOFacade rentService;
    @Override
    public void init() throws ServletException {
        super.init();
        ApplicationContext ctx = (ApplicationContext) getServletContext().getAttribute("ctx");
        movieService = ctx.getObject(MovieDTOFacade.class);
        rentService = ctx.getObject(RentDTOFacade.class);
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("movies", movieService.getAll());
        req.setAttribute("rents", rentService.getAll());
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/jsp/viewReportJSP.jsp");
        dispatcher.forward(req, resp);
    }
}

