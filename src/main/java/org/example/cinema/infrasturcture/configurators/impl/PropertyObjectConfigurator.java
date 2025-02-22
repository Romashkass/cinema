package org.example.cinema.infrasturcture.configurators.impl;

import lombok.SneakyThrows;
import org.example.cinema.infrasturcture.configurators.ObjectConfigurator;
import org.example.cinema.infrasturcture.core.Context;
import org.example.cinema.infrasturcture.core.annotations.Property;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class PropertyObjectConfigurator implements ObjectConfigurator {
    private final Map<String, String> properties;

    @SneakyThrows
    public PropertyObjectConfigurator() {
        URL path = this.getClass().getClassLoader().getResource("application.properties");
        if (path == null) {
            throw new FileNotFoundException(String.format("File '%s' not found", "application.properties"));
        }
        Stream<String> lines = new BufferedReader(new InputStreamReader(path.openStream()) ).lines();
        properties = lines.map(line -> line.split("=")).collect(toMap(arr -> arr[0], arr -> arr[1]));
    }

    @Override
    @SneakyThrows
    public void configure(Object object, Context context) {
        for (Field field: object.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Property.class)) {
                String prop = properties.get(field.getAnnotation(Property.class).value());
                if (prop == null) {
                    prop = properties.get(field.getName());
                }
                field.setAccessible(true);
                field.set(object, prop);
            }
        }
    }
}
