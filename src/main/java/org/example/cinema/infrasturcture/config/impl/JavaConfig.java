package org.example.cinema.infrasturcture.config.impl;

import lombok.AllArgsConstructor;
import org.example.cinema.infrasturcture.config.Config;
import org.example.cinema.infrasturcture.core.Scanner;

import java.util.Map;
import java.util.Set;

@AllArgsConstructor
public class JavaConfig implements Config {
    private final Scanner scanner;
    private final Map<Class<?>, Class<?>> interfaceToImplementation;

    @Override
    public <T> Class<? extends T> getImplementation(Class<T> target) {
        Set<Class<? extends T>> implementations = scanner.getSubTypesOf(target);
        if (implementations.size() > 1) {
            Class<?> expectedImplementation = interfaceToImplementation.get(target);
            if (expectedImplementation == null) {
                throw new RuntimeException("target interface has more then one implementations");
            }
            for (Class<? extends T> item: implementations) {
                if (expectedImplementation.equals(item)) {
                    return item;
                }
            }
        }
        return implementations.stream().findFirst().orElseThrow(() -> new RuntimeException("target interface has 0 implementations"));
    }

    @Override
    public Scanner getScanner() {
        return scanner;
    }
}
