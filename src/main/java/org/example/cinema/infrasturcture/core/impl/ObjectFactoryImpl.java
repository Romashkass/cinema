package org.example.cinema.infrasturcture.core.impl;

import lombok.SneakyThrows;
import org.example.cinema.infrasturcture.configurators.ObjectConfigurator;
import org.example.cinema.infrasturcture.configurators.ProxyConfigurator;
import org.example.cinema.infrasturcture.core.Context;
import org.example.cinema.infrasturcture.core.ObjectFactory;
import org.example.cinema.infrasturcture.core.Scanner;
import org.example.cinema.infrasturcture.core.annotations.InitMethod;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ObjectFactoryImpl implements ObjectFactory {
    private final Context context;
    private final List<ObjectConfigurator> objectConfigurators = new ArrayList<>();
    private final List<ProxyConfigurator> proxyConfigurators = new ArrayList<>();

    @SneakyThrows
    public ObjectFactoryImpl(Context context) {
        this.context = context;
        Scanner scanner = context.getConfig().getScanner();
        Set<Class<? extends ObjectConfigurator>> objectConfiguratorsSet = scanner.getSubTypesOf(ObjectConfigurator.class);
        for (Class<? extends ObjectConfigurator> item: objectConfiguratorsSet) {
            objectConfigurators.add(item.getConstructor().newInstance());
        }
        Set<Class<? extends ProxyConfigurator>> proxyConfiguratorsSet = scanner.getSubTypesOf(ProxyConfigurator.class);
        for (Class<? extends ProxyConfigurator> item: proxyConfiguratorsSet) {
            proxyConfigurators.add(item.getConstructor().newInstance());
        }
    }

    private <T> T create(Class<T> implementation) throws Exception {
        return implementation.getConstructor().newInstance();
    }

    private <T> void configure(T object) {
        for (ObjectConfigurator configurator: objectConfigurators) {
            configurator.configure(object, context);
        }
    }

    private <T> void initialize(Class<T> implementation, T object) throws Exception {
        for (Method method : implementation.getMethods()) {
            if (method.isAnnotationPresent(InitMethod.class)) {
                method.invoke(object);
            }
        }
    }

    @Override
    @SneakyThrows
    public <T> T createObject(Class<T> implementation) {
        T obj = create(implementation);
        obj = makeProxy(implementation, obj);
        configure(obj);
        initialize(implementation, obj);
        return obj;
    }

    private <T> T makeProxy(Class<T> implClass, T object) {
        for (ProxyConfigurator proxyConfigurator : proxyConfigurators) {
            object = (T) proxyConfigurator.makeProxy(object, implClass, context);
        }
        return object;
    }
}
