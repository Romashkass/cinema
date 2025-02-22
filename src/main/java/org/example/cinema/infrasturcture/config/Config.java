package org.example.cinema.infrasturcture.config;

import org.example.cinema.infrasturcture.core.Scanner;

public interface Config {
    <T> Class<? extends T> getImplementation(Class<T> target);
    Scanner getScanner();
}
