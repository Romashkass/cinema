package org.example.cinema.infrasturcture.core.impl;

import org.example.cinema.infrasturcture.core.Cache;

import java.util.HashMap;
import java.util.Map;

public class CacheImpl implements Cache {
    private Map<String, Object> cache;

    public CacheImpl() {
        cache = new HashMap<>();
    }

    @Override
    public boolean contains(Class<?> clazz) {
        return cache.containsKey(clazz.getName());
    }

    @Override
    public <T> T get(Class<T> clazz) {
        return (T) cache.get(clazz.getName());
    }

    @Override
    public <T> void put(Class<T> clazz, T value) {
        cache.put(clazz.getName(), value);
    }
}
