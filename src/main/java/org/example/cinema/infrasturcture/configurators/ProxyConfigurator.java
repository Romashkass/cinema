package org.example.cinema.infrasturcture.configurators;

import org.example.cinema.infrasturcture.core.Context;

public interface ProxyConfigurator {
    <T> T makeProxy(T object, Class<T> implementation, Context context);
}
