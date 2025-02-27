package org.example.cinema.service;

import org.example.cinema.entity.Genre;
import org.example.cinema.infrasturcture.core.annotations.Autowired;
import org.example.cinema.infrasturcture.core.annotations.InitMethod;
import org.example.cinema.infrasturcture.dto.EntityManager;

import java.util.List;

public class GenreService {
    @Autowired
    private EntityManager entityManager;

    @InitMethod
    public void init(){

    }

    public Genre get(Long id){
        return entityManager.get(id, Genre.class).orElse(null);
    }

    public List<Genre> getAll(){
        return entityManager.getAll(Genre.class);
    }

    public Long save(Genre genre){
        return entityManager.save(genre);
    }

}
