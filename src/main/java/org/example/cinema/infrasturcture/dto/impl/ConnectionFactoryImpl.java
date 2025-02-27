package org.example.cinema.infrasturcture.dto.impl;

import lombok.SneakyThrows;
import org.example.cinema.infrasturcture.core.annotations.InitMethod;
import org.example.cinema.infrasturcture.core.annotations.Property;
import org.example.cinema.infrasturcture.dto.ConnectionFactory;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactoryImpl implements ConnectionFactory {
    @Property("url")
    private String url;
    @Property("username")
    private String username;
    @Property("password")
    private String password;
    private Connection connection;

    public ConnectionFactoryImpl() {
    }

    @SneakyThrows
    @InitMethod
    public void initConnection() {
        connection = DriverManager.getConnection(url, username, password);
    }

    @Override
    public Connection getConnection() {
        return connection;
    }
}
