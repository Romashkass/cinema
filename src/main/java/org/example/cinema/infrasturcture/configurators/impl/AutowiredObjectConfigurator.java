package org.example.cinema.infrasturcture.configurators.impl;

import lombok.SneakyThrows;
import org.example.cinema.infrasturcture.configurators.ObjectConfigurator;
import org.example.cinema.infrasturcture.core.Context;
import org.example.cinema.infrasturcture.core.annotations.Autowired;

import java.lang.reflect.Field;

public class AutowiredObjectConfigurator implements ObjectConfigurator {
    @Override
    @SneakyThrows
    public void configure(Object object, Context context) {
        for (Field field: object.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Autowired.class)) {
                field.setAccessible(true);
                field.set(object, context.getObject(field.getType()));
            }
        }
    }
}
