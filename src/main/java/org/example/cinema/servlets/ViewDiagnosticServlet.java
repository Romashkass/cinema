package org.example.cinema.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.cinema.CinemaWorkroom;
import org.example.cinema.dto.MovieDTO;
import org.example.cinema.infrasturcture.core.impl.ApplicationContext;
import org.example.cinema.facade.MovieDTOFacade;

import java.io.IOException;
import java.util.List;

@WebServlet("/viewDiagnostic")
public class ViewDiagnosticServlet extends HttpServlet {
    private MovieDTOFacade movieService;
    private CinemaWorkroom workroom;
    @Override
    public void init() throws ServletException {
        super.init();
        ApplicationContext ctx = (ApplicationContext) getServletContext().getAttribute("ctx");
        movieService = ctx.getObject(MovieDTOFacade.class);
        workroom = ctx.getObject(CinemaWorkroom.class);
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<MovieDTO> movies = movieService.getAll();
        for (MovieDTO movie: movies) {
            workroom.maintenance(movieService.movieDTOToMovie(movie));
        }
        req.setAttribute("movies", movies);
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/jsp/viewDiagnosticJSP.jsp");
        dispatcher.forward(req, resp);
    }
}

