package org.ajani2001.lab2;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DbInitializer {
    private final Connection connection;

    public DbInitializer(Connection connection) {
        this.connection = connection;
    }

    public void initialize() throws SQLException {
        dropTagsTable();
        dropNodesTable();
        createNodesTable();
        createTagsTable();
    }

    private void dropNodesTable() throws SQLException {
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS nodes;");
        }
    }

    private void dropTagsTable() throws SQLException {
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS tags;");
        }
    }

    private void createNodesTable() throws SQLException {
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate("""
            CREATE TABLE nodes(
                id BIGINT PRIMARY KEY,
                lat DOUBLE PRECISION,
                lon DOUBLE PRECISION,
                user_name VARCHAR(50),
                user_id BIGINT,
                visible BOOLEAN,
                version BIGINT,
                changeset BIGINT,
                timestamp VARCHAR(20)
            );
            """);
        }
    }

    private void createTagsTable() throws SQLException {
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate("""
            CREATE TABLE tags(
                id BIGSERIAL PRIMARY KEY,
                key VARCHAR(50),
                value TEXT,
                node_id BIGINT REFERENCES nodes(id)
            );
            """);
        }
    }
}
