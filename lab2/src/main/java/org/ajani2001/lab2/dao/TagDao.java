package org.ajani2001.lab2.dao;

import org.ajani2001.Tag;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TagDao {

    private final Connection connection;

    public TagDao(Connection connection) {
        this.connection = connection;
    }

    public void save(Tag tag, Long nodeId) throws SQLException {
        var statement = connection.createStatement();
        statement.executeUpdate(
                "INSERT INTO tags(key, value, node_id) VALUES ('%s', '%s', %d);"
                        .formatted(
                                tag.getK().replace("'", "''"),
                                tag.getV().replace("'", "''"),
                                nodeId
                        ),
                new String[]{"id"}
        );
    }

    public List<Tag> getAllByNodeId(Long nodeId) throws SQLException {
        var statement = connection.createStatement();
        var resultSet = statement.executeQuery("SELECT * FROM tags WHERE node_id = %d".formatted(nodeId));
        var result = new ArrayList<Tag>(resultSet.getFetchSize());
        while (resultSet.next()) {
            var tag = new Tag();
            tag.setK(resultSet.getString("key"));
            tag.setV(resultSet.getString("value"));
            result.add(tag);
        }
        return result;
    }
}
