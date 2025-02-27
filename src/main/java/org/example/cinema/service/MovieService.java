package org.example.cinema.service;

import org.example.cinema.entity.Movie;
import org.example.cinema.infrasturcture.core.annotations.Autowired;
import org.example.cinema.infrasturcture.core.annotations.InitMethod;
import org.example.cinema.infrasturcture.dto.EntityManager;

import java.util.List;

public class MovieService {
    @Autowired
    private EntityManager entityManager;

    @InitMethod
    public void init(){

    }

    public Movie get(Long id){
        return entityManager.get(id, Movie.class).orElse(null);
    }

    public List<Movie> getAll(){
        return entityManager.getAll(Movie.class);
    }

    public Long save(Movie movie){
        return entityManager.save(movie);
    }
}
