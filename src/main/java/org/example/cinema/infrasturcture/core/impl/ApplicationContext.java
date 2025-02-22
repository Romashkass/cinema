package org.example.cinema.infrasturcture.core.impl;

import org.example.cinema.infrasturcture.config.Config;
import org.example.cinema.infrasturcture.config.impl.JavaConfig;
import org.example.cinema.infrasturcture.core.Cache;
import org.example.cinema.infrasturcture.core.Context;
import org.example.cinema.infrasturcture.core.ObjectFactory;

import java.util.Map;

public class ApplicationContext implements Context {
    private final Config config;
    private final Cache cache;
    private final ObjectFactory factory;

    public ApplicationContext(String packageToScan, Map<Class<?>, Class<?>> interfaceToImplementation) {
        this.config = new JavaConfig(new ScannerImpl(packageToScan), interfaceToImplementation);
        this.cache = new CacheImpl();
        cache.put(Context.class, this);
        this.factory = new ObjectFactoryImpl(this);
    }

    @Override
    public <T> T getObject(Class<T> type) {
        T result = cache.get(type);
        if (result != null) {
            return result;
        }

        if (type.isInterface()) {
            Class<? extends T> implementation = config.getImplementation(type);
            result = factory.createObject(implementation);
        } else {
            result = factory.createObject(type);
        }

        cache.put(type, result);
        return result;
    }

    @Override
    public Config getConfig() {
        return config;
    }
}
