package org.ajani2001.lab2.uploader;

import org.ajani2001.lab2.dao.NodeDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class PreparedStatementBatchNodesUploader implements NodesUploader {

    private final Connection connection;

    public PreparedStatementBatchNodesUploader(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void upload(List<NodeDao> nodes) throws SQLException {
        var statement = connection.prepareStatement("INSERT INTO nodes VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);");
        for (NodeDao nodeDao : nodes) {
            addBatch(statement, nodeDao);
        }
        statement.executeBatch();
        connection.commit();
    }

    private void addBatch(PreparedStatement statement, NodeDao node) throws SQLException {
        statement.setObject(1, node.getId());
        statement.setObject(2, node.getLat());
        statement.setObject(3, node.getLon());
        statement.setObject(4, node.getUserName());
        statement.setObject(5, node.getUserId());
        statement.setObject(6, node.getVisible());
        statement.setObject(7, node.getVersion());
        statement.setObject(8, node.getChangeset());
        statement.setObject(9, node.getTimestamp());
        statement.addBatch();
    }
}
