package org.example.cinema.service;

import org.example.cinema.entity.Rent;
import org.example.cinema.infrasturcture.core.annotations.Autowired;
import org.example.cinema.infrasturcture.core.annotations.InitMethod;
import org.example.cinema.infrasturcture.dto.EntityManager;

import java.util.List;

public class RentService {
    @Autowired
    private EntityManager entityManager;

    @InitMethod
    public void init(){

    }

    public Rent get(Long id){
        return entityManager.get(id, Rent.class).orElse(null);
    }

    public List<Rent> getAll(){
        return entityManager.getAll(Rent.class);
    }

    public Long save(Rent rent){
        return entityManager.save(rent);
    }
}
