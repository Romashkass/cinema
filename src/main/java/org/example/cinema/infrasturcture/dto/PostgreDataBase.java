package org.example.cinema.infrasturcture.dto;

import org.example.cinema.infrasturcture.core.Context;
import org.example.cinema.infrasturcture.core.annotations.Autowired;
import org.example.cinema.infrasturcture.core.annotations.InitMethod;
import org.example.cinema.infrasturcture.dto.annotations.Column;
import org.example.cinema.infrasturcture.dto.annotations.ID;
import org.example.cinema.infrasturcture.dto.annotations.Table;
import org.example.cinema.infrasturcture.dto.enums.SqlFieldType;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.stream.Collectors;

public class PostgreDataBase {
    @Autowired
    private ConnectionFactory connectionFactory;
    private Map<String, String> classToSql;
    private Map<String, String> insertPatternByClass;
    @Autowired
    private Context context;
    private static final String SEQ_NAME = "id_seq";
    private static final String CHECK_SEQ_PATTERN = "SELECT EXISTS ( " +
            "SELECT FROM information_schema.sequences " +
            "WHERE sequence_schema = 'public' " +
            "AND sequence_name = '%s' " +
            ");";
    private static final String CREATE_ID_SEQ_PATTERN = "CREATE SEQUENCE %s INCREMENT 1 START 1;";
    private static final String CHECK_TABLE_SQL_PATTERN = "SELECT EXISTS ( " +
                    "SELECT FROM information_schema.tables " +
                    "WHERE table_schema = 'public' " +
                    "AND table_name   = '%s' " +
                    ");";
    private static final String CREATE_TABLE_SQL_PATTERN = "CREATE TABLE %s ( " +
                    "%s bigint PRIMARY KEY DEFAULT nextval('%s'), " +
                    "%s );";
    private static final String INSERT_SQL_PATTERN = "INSERT INTO %s (%s) VALUES (%s) RETURNING %s ;";
    private Map<String, String> insertByClassPattern;

    public PostgreDataBase() {
    }

    @InitMethod
    public void init() {
        classToSql = Arrays.stream(SqlFieldType.values()).collect(Collectors.toMap((t) -> t.getType().getName(), SqlFieldType::getSqlType));
        insertPatternByClass = Arrays.stream(SqlFieldType.values()).collect(Collectors.toMap((t) -> t.getType().getName(), SqlFieldType::getInsertPattern));
        insertByClassPattern = new HashMap<>();

        if (!checkSeq()) {
            createSeq();
        }

        Set<Class<?>> entities = context.getConfig().getScanner().getReflections().getTypesAnnotatedWith(Table.class);
        validateEntities(entities);

        for (Class<?> entity: entities) {
            boolean exists = checkIfTableExists(entity);
            if (!exists) {
                createTable(entity);
            }
        }

        entities.forEach(this::fillInsertByClassPattern);
    }

    private boolean checkSeq() {
        boolean result = false;
        Connection connection = connectionFactory.getConnection();
        try (Statement statement = connection.createStatement()) {
            String sql = String.format(CHECK_SEQ_PATTERN, SEQ_NAME);
            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.next();
            result = resultSet.getBoolean("exists");
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void createSeq() {
        Connection connection = connectionFactory.getConnection();
        try (Statement statement = connection.createStatement()) {
            String sql = String.format(CREATE_ID_SEQ_PATTERN, SEQ_NAME);
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void validateEntities(Set<Class<?>> entities) {
        for (Class<?> clazz: entities) {
            boolean id = false;
            for (Field field: clazz.getDeclaredFields()) {
                if (!field.isAnnotationPresent(ID.class)) {
                    continue;
                }
                id = true;
            }
            if (!id) {
                throw new RuntimeException("No @ID in " + clazz.getName());
            }

            Set<String> names = new HashSet<>();
            for (Field field: clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(Column.class)) {
                    if (field.getType().isPrimitive()) {
                        throw new RuntimeException("Field " + field.getName() + " is primitive");
                    }
                    if (!names.add(field.getAnnotation(Column.class).name())) {
                        throw new RuntimeException("Some fields in class " + clazz.getName() + " has not unique column names");
                    }
                }
            }
        }
    }

    private boolean checkIfTableExists(Class<?> entity) {
        boolean result = false;
        Connection connection = connectionFactory.getConnection();
        try (Statement statement = connection.createStatement()) {
                String tableName = entity.getAnnotation(Table.class).name();
                String sql = String.format(CHECK_TABLE_SQL_PATTERN, tableName);
                ResultSet resultSet = statement.executeQuery(sql);
                resultSet.next();
                result = resultSet.getBoolean("exists");
                resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void createTable(Class<?> entity) {
        String tableName = entity.getAnnotation(Table.class).name();
        String idField = "";
        StringBuilder fields = new StringBuilder();
        for (Field field: entity.getDeclaredFields()) {
            if (field.isAnnotationPresent(ID.class)) {
                idField = field.getName();
            }
            if (field.isAnnotationPresent(Column.class)) {
                Column column = field.getAnnotation(Column.class);

                fields.append(column.name()).append(" ");
                fields.append(classToSql.get(field.getType().getName())).append(" ");
                fields.append(column.nullable() ? "" : "NOT NULL ");
                fields.append(column.unique() ? "UNIQUE," : ",");
            }
        }
        fields.deleteCharAt(fields.length() - 1);

        Connection connection = connectionFactory.getConnection();
        try (Statement statement = connection.createStatement()) {
            String sql = String.format(CREATE_TABLE_SQL_PATTERN, tableName, idField, SEQ_NAME, fields);
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void fillInsertByClassPattern(Class<?> entity) {
        String tableName = entity.getAnnotation(Table.class).name();
        StringBuilder insertFields = new StringBuilder();
        StringBuilder values = new StringBuilder();
        String idFieldName = "";

        for (Field field: entity.getDeclaredFields()) {
            if (field.isAnnotationPresent(ID.class)) {
                idFieldName = field.getName();
            }
            if (field.isAnnotationPresent(Column.class)) {
                Column column = field.getAnnotation(Column.class);
                insertFields.append(column.name()).append(",");
                values.append(insertPatternByClass.get(field.getType().getName())).append(",");
            }
        }
        insertFields.deleteCharAt(insertFields.length() - 1);
        values.deleteCharAt(values.length() - 1);

        String sql = String.format(INSERT_SQL_PATTERN, tableName, insertFields, values, idFieldName);
        insertByClassPattern.put(entity.getName(), sql);
    }

    public Long save(Object obj) {
        Object[] values = getValues(obj);
        String idFieldName = "";

        for (Field field: obj.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(ID.class)) {
                idFieldName = field.getName();
                break;
            }
        }

        Long result = -1L;
        Connection connection = connectionFactory.getConnection();
        try (Statement statement = connection.createStatement()) {
            String sql = String.format(insertByClassPattern.get(obj.getClass().getName()), values);
            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.next();
            result = resultSet.getLong(idFieldName);
            Field field = obj.getClass().getDeclaredField(idFieldName);
            field.setAccessible(true);
            field.set(obj, result);
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    private Object[] getValues(Object obj) {
        List<Object> list = new ArrayList<>();
        for (Field field: obj.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Column.class)) {
                field.setAccessible(true);
                try {
                    list.add(field.get(obj));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return list.toArray();
    }

    public <T> Optional<T> get(Long id, Class<T> clazz) {
        if (clazz.isAnnotationPresent(Table.class)) {
            throw new RuntimeException("No @Table annotation on class " + clazz.getName());
        }
        Table table = clazz.getAnnotation(Table.class);
        String idFieldName = "";
        for (Field field: clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(ID.class)) {
                idFieldName = field.getName();
            }
        }

        String sql = "SELECT * FROM " + table.name() + " WHERE " + idFieldName + " = " + id;
        Optional<T> result = Optional.empty();

        Connection connection = connectionFactory.getConnection();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.next();
            result = Optional.of(makeObject(resultSet, clazz));
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private <T> T makeObject(ResultSet resultSet, Class<T> clazz) {
        try {
            T obj = clazz.getConstructor().newInstance();
            for (Field field: clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(ID.class)) {
                    field.setAccessible(true);
                    field.set(obj, field.getType().cast(resultSet.getObject(field.getName())));
                }
                if (!field.isAnnotationPresent(Column.class)) {
                    continue;
                }
                field.setAccessible(true);
                field.set(obj, field.getType().cast(resultSet.getObject(field.getAnnotation(Column.class).name())));
            }

            return obj;
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> List<T> getAll(Class<T> clazz) {
        if (!clazz.isAnnotationPresent(Table.class)) {
            throw new RuntimeException("No @Table annotation on class " + clazz.getName());
        }
        Table table = clazz.getAnnotation(Table.class);
        String sql = "SELECT * FROM " + table.name();
        List<T> result = new ArrayList<>();

        Connection connection = connectionFactory.getConnection();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                result.add(makeObject(resultSet, clazz));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
