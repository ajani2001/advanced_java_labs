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
        dropRelationRelationsTable();
        dropRelationWaysTable();
        dropRelationNodesTable();
        dropWayNodesTable();
        dropRelationTagsTable();
        dropWayTagsTable();
        dropNodeTagsTable();
        dropTagsTable();
        dropRelationsTable();
        dropWaysTable();
        dropNodesTable();
        createNodesTable();
        createWaysTable();
        createRelationsTable();
        createTagsTable();
        createNodeTagsTable();
        createWayTagsTable();
        createRelationTagsTable();
        createWayNodesTable();
        createRelationNodesTable();
        createRelationWaysTable();
        createRelationRelationsTable();
    }

    private void dropNodesTable() throws SQLException {
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS nodes;");
        }
    }

    private void dropWaysTable() throws SQLException {
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS ways;");
        }
    }

    private void dropRelationsTable() throws SQLException {
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS relations;");
        }
    }

    private void dropTagsTable() throws SQLException {
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS tags;");
        }
    }

    private void dropNodeTagsTable() throws SQLException {
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS node_tags;");
        }
    }

    private void dropWayTagsTable() throws SQLException {
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS way_tags;");
        }
    }

    private void dropRelationTagsTable() throws SQLException {
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS relation_tags;");
        }
    }

    private void dropWayNodesTable() throws SQLException {
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS way_nodes;");
        }
    }

    private void dropRelationNodesTable() throws SQLException {
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS relation_nodes;");
        }
    }

    private void dropRelationWaysTable() throws SQLException {
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS relation_ways;");
        }
    }

    private void dropRelationRelationsTable() throws SQLException {
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS relation_relations;");
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

    private void createWaysTable() throws SQLException {
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate("""
            CREATE TABLE ways(
                id BIGINT PRIMARY KEY,
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

    private void createRelationsTable() throws SQLException {
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate("""
            CREATE TABLE relations(
                id BIGINT PRIMARY KEY,
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
                value TEXT
            );
            """);
        }
    }

    private void createNodeTagsTable() throws SQLException {
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate("""
            CREATE TABLE node_tags(
                id BIGSERIAL PRIMARY KEY,
                node_id BIGINT REFERENCES nodes(id),
                tag_id BIGINT REFERENCES tags(id)
            );
            """);
        }
    }

    private void createWayTagsTable() throws SQLException {
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate("""
            CREATE TABLE way_tags(
                id BIGSERIAL PRIMARY KEY,
                way_id BIGINT REFERENCES ways(id),
                tag_id BIGINT REFERENCES tags(id)
            );
            """);
        }
    }

    private void createRelationTagsTable() throws SQLException {
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate("""
            CREATE TABLE relation_tags(
                id BIGSERIAL PRIMARY KEY,
                relation_id BIGINT REFERENCES relations(id),
                tag_id BIGINT REFERENCES tags(id)
            );
            """);
        }
    }

    private void createWayNodesTable() throws SQLException {
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate("""
            CREATE TABLE way_nodes(
                id BIGSERIAL PRIMARY KEY,
                way_id BIGINT REFERENCES ways(id),
                node_id BIGINT REFERENCES nodes(id)
            );
            """);
        }
    }

    private void createRelationNodesTable() throws SQLException {
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate("""
            CREATE TABLE relation_nodes(
                id BIGSERIAL PRIMARY KEY,
                relation_id BIGINT REFERENCES relations(id),
                node_id BIGINT REFERENCES nodes(id)
            );
            """);
        }
    }

    private void createRelationWaysTable() throws SQLException {
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate("""
            CREATE TABLE relation_ways(
                id BIGSERIAL PRIMARY KEY,
                relation_id BIGINT REFERENCES relations(id),
                way_id BIGINT REFERENCES ways(id)
            );
            """);
        }
    }

    private void createRelationRelationsTable() throws SQLException {
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate("""
            CREATE TABLE relation_relations(
                id BIGSERIAL PRIMARY KEY,
                relation_id BIGINT REFERENCES relations(id),
                relation_child_id BIGINT REFERENCES relations(id)
            );
            """);
        }
    }
}
