package org.ajani2001.lab2.uploader;

import org.ajani2001.lab2.dao.NodeDao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class StatementNodesUploader implements NodesUploader {

    private final Connection connection;

    public StatementNodesUploader(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void upload(List<NodeDao> nodes) throws SQLException {
        for (NodeDao nodeDao : nodes) {
            uploadNode(nodeDao);
        }
    }

    private void uploadNode(NodeDao node) throws SQLException {
        var statement = connection.createStatement();
        statement.executeUpdate(
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
