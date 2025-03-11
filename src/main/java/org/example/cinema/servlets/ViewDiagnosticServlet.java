package org.example.cinema.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.cinema.CinemaWorkroom;
import org.example.cinema.entity.Movie;
import org.example.cinema.infrasturcture.core.annotations.Autowired;
import org.example.cinema.infrasturcture.core.impl.ApplicationContext;
import org.example.cinema.service.GenreService;
import org.example.cinema.service.MovieService;
import org.example.cinema.service.RentService;

import java.io.IOException;
import java.util.List;

@WebServlet("/viewDiagnostic")
public class ViewDiagnosticServlet extends HttpServlet {
    private MovieService movieService;
    private GenreService genreService;
    private CinemaWorkroom workroom;
    @Override
    public void init() throws ServletException {
        super.init();
        ApplicationContext ctx = (ApplicationContext) getServletContext().getAttribute("ctx");
        movieService = ctx.getObject(MovieService.class);
        genreService = ctx.getObject(GenreService.class);
        workroom = ctx.getObject(CinemaWorkroom.class);
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Movie> movies = movieService.getAll();
        for (Movie movie: movies) {
            workroom.maintenance(movie);
        }
        req.setAttribute("movies", movies);
        req.setAttribute("genres", genreService.getAll());
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/jsp/viewDiagnosticJSP.jsp");
        dispatcher.forward(req, resp);
    }
}

