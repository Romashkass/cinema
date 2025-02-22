package org.example.cinema.infrasturcture.core;

import org.example.cinema.infrasturcture.config.Config;

public interface Context {
    <T> T getObject(Class<T> type);
    Config getConfig();
}
