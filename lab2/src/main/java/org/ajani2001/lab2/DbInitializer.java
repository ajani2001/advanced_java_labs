package org.ajani2001.lab2;

import java.sql.Connection;
import java.sql.Statement;

public class DbInitializer {
    private final Connection connection;

    public DbInitializer(Connection connection) {
        this.connection = connection;
    }

    public void initialize() {
        dropTagsTable();
        dropNodesTable();
        createNodesTable();
        createTagsTable();
    }

    private void dropNodesTable() {
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS nodes");
        }
    }

    private void dropTagsTable() {
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS tags");
        }
    }

    private void createNodesTable() {
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate("""
            CREATE TABLE nodes(
                id BIGINT,
                lat DOUBLE PRECISION,
                lon DOUBLE PRECISION,
                user_name VARCHAR(50),
                user_id BIGINT,
                visible BOOLEAN,
                version BIGINT,
                changeset BIGINT,
                timestamp TIMESTAMP
            )
            """);
        }
    }

    private void createTagsTable() {
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate("""
            CREATE TABLE tags(
                id BIGINT,
                key VARCHAR(50),
                value VARCHAR(50),
                node_id BIGINT REFERENCES nodes(id)
            )
            """);
        }
    }
}
