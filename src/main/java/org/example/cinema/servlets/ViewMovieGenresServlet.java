package org.example.cinema.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.cinema.infrasturcture.core.annotations.Autowired;
import org.example.cinema.infrasturcture.core.impl.ApplicationContext;
import org.example.cinema.service.GenreService;

import java.io.IOException;

@WebServlet("/viewTypes")
public class ViewMovieGenresServlet extends HttpServlet {
    private GenreService genreService;
    @Override
    public void init() throws ServletException {
        super.init();
        ApplicationContext ctx = (ApplicationContext) getServletContext().getAttribute("ctx");
        genreService = ctx.getObject(GenreService.class);
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("genres", genreService.getAll());
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/jsp/viewGenresJSP.jsp");
        dispatcher.forward(req, resp);
    }
}
