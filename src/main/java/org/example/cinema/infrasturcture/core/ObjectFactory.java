package org.example.cinema.infrasturcture.core;

public interface ObjectFactory {
    <T> T createObject(Class<T> implementation);
}
