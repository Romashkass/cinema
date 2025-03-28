package org.example.cinema.infrasturcture.dto.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
@Getter
public enum SqlFieldType {
    INTEGER(Integer.class, "integer", "%s"),
    LONG(Long.class, "bigint", "%s"),
    DOUBLE(Double.class, "double precision", "%s"),
    STRING(String.class, "varchar(255)", "'%s'"),
    DATE(Date.class, "date", "'%s'");
    private final Class<?> type;
    private final String sqlType;
    private final String insertPattern;
}
