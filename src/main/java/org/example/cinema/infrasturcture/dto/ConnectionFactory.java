package org.example.cinema.infrasturcture.dto;

import java.sql.Connection;

public interface ConnectionFactory {
    Connection getConnection();
}
