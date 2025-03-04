package org.example.cinema.infrasturcture.threads.configurators;

import lombok.SneakyThrows;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.example.cinema.infrasturcture.configurators.ProxyConfigurator;
import org.example.cinema.infrasturcture.core.Context;
import org.example.cinema.infrasturcture.threads.annotations.Schedule;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.concurrent.*;

public class ScheduleConfigurator implements ProxyConfigurator {
    @Override
    public <T> T makeProxy(T object, Class<T> implementation, Context context) {
        boolean methodsPresent = false;
        for (Method method: object.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(Schedule.class)) {
                if (method.getReturnType() != void.class || !Modifier.isPublic(method.getModifiers())) {
                    throw new RuntimeException("Method " + method.getName() + " in class " + object.getClass().getName() + " has not void return type or not public");
                }
                methodsPresent = true;
            }
        }
        if (methodsPresent) {
            return (T) Enhancer.create(implementation, (MethodInterceptor)this::invoke);
        }
        return object;
    }

    @SneakyThrows
    private Object invoke(Object object, Method method, Object[] args, MethodProxy methodProxy) {
        Schedule schedulesync = method.getAnnotation(Schedule.class);
        if (schedulesync != null) {
            Thread thread = new Thread(() -> this.invoker(object, methodProxy, args, schedulesync.timeout(), schedulesync.delta()));
            thread.setDaemon(true);
            thread.start();
            return null;
        }
        return methodProxy.invokeSuper(object, args);
    }

    private void invoker(Object obj, MethodProxy method, Object[] args, int milliseconds, int delta) {
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    Thread invokeThread = new Thread(() -> {
                        ExecutorService executorService =
                                Executors.newSingleThreadExecutor(new ThreadFactory() {
                                    @Override
                                    public Thread newThread(Runnable r) {
                                        Thread fThread = Executors.defaultThreadFactory().newThread(r);
                                        fThread.setDaemon(true);
                                        return fThread;
                                    }
                                });
                        try {
                            executorService.submit(() -> {
                                try {
                                    return method.invokeSuper(obj, args);
                                } catch (Throwable throwable) {
                                }
                                return null;
                            }).get(milliseconds, TimeUnit.MILLISECONDS);
                        } catch (TimeoutException exception) {
                            executorService.shutdownNow();
                        } catch (Exception exception) {
                            executorService.shutdownNow();
                        }
                        executorService.shutdown();
                    });
                    invokeThread.setDaemon(true);
                    invokeThread.start();
                    Thread.currentThread().sleep(delta);
                } catch (InterruptedException e) {
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }
}
