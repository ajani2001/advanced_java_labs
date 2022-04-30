package org.ajani2001.lab2.uploader;

import org.ajani2001.lab2.dao.NodeDao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class BatchNodesUploader implements NodesUploader {

    private final Connection connection;

    public BatchNodesUploader(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void upload(List<NodeDao> nodes) throws SQLException {
//        final var STEP = 100;
        var statement = connection.createStatement();
        for (NodeDao nodeDao : nodes) {
            addBatch(statement, nodeDao);
        }
        statement.executeBatch();
    }

    private void addBatch(Statement statement, NodeDao node) throws SQLException {
        statement.addBatch(
                "INSERT INTO nodes VALUES (%d, %f, %f, '%s', %d, %s, %d, %d, '%s');"
                        .formatted(
                                node.getId(),
                                node.getLat(),
                                node.getLon(),
                                node.getUserName().replace("'", "''"),
                                node.getUserId(),
                                node.getVisible(),
                                node.getVersion(),
                                node.getChangeset(),
                                node.getTimestamp()
                        )
        );
    }
}
