package org.example.cinema.infrasturcture.dto.impl;

import org.example.cinema.infrasturcture.core.Context;
import org.example.cinema.infrasturcture.core.annotations.Autowired;
import org.example.cinema.infrasturcture.dto.ConnectionFactory;
import org.example.cinema.infrasturcture.dto.EntityManager;
import org.example.cinema.infrasturcture.dto.PostgreDataBase;

import java.util.List;
import java.util.Optional;

public class EntityManagerImpl implements EntityManager {
    @Autowired
    private ConnectionFactory connection;
    @Autowired
    private PostgreDataBase dataBaseService;
    @Autowired
    private Context context;

    public EntityManagerImpl() {
    }

    @Override
    public <T> Optional<T> get(Long id, Class<T> clazz) {
        return dataBaseService.get(id, clazz);
    }

    @Override
    public Long save(Object object) {
        return dataBaseService.save(object);
    }

    @Override
    public <T> List<T> getAll(Class<T> clazz) {
        return dataBaseService.getAll(clazz);
    }
}
