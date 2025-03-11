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
import org.example.cinema.service.MovieService;

import java.io.IOException;

@WebServlet("/viewMovies")
public class ViewMoviesServlet extends HttpServlet {
    private MovieService movieService;
    private GenreService genreService;
    @Override
    public void init() throws ServletException {
        super.init();
        ApplicationContext ctx = (ApplicationContext) getServletContext().getAttribute("ctx");
        movieService = ctx.getObject(MovieService.class);
        genreService = ctx.getObject(GenreService.class);
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("movies", movieService.getAll());
        req.setAttribute("genres", genreService.getAll());
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/jsp/viewMoviesJSP.jsp");
        dispatcher.forward(req, resp);
    }
}

