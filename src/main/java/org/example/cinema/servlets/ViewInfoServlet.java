package org.example.cinema.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.cinema.entity.Movie;
import org.example.cinema.infrasturcture.core.annotations.Autowired;
import org.example.cinema.infrasturcture.core.impl.ApplicationContext;
import org.example.cinema.service.GenreService;
import org.example.cinema.service.MovieService;
import org.example.cinema.service.RentService;

import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet("/info")
public class ViewInfoServlet extends HttpServlet {
    private MovieService movieService;
    private GenreService genreService;
    private RentService rentService;
    @Override
    public void init() throws ServletException {
        super.init();
        ApplicationContext ctx = (ApplicationContext) getServletContext().getAttribute("ctx");
        movieService = ctx.getObject(MovieService.class);
        genreService = ctx.getObject(GenreService.class);
        rentService = ctx.getObject(RentService.class);
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        Movie one = movieService.getAll().stream().filter(movie -> id == movie.getId()).findFirst().orElse(null);
        req.setAttribute("movie", one);
        req.setAttribute("rents", rentService.getAll().stream().filter(rent -> id == rent.getMovieId()).collect(Collectors.toList()));
        req.setAttribute("genre", genreService.get(one.getGenreId()));
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/jsp/viewMovieInfoJSP.jsp");
        dispatcher.forward(req, resp);
    }
}

