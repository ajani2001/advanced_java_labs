package org.ajani2001.lab2.dao;

import org.ajani2001.Node;
import org.ajani2001.Tag;

import javax.xml.datatype.DatatypeFactory;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;

public class NodeDao {

    private final Connection connection;
    private final TagDao tagDao;

    public NodeDao(Connection connection) {
        this.connection = connection;
        tagDao = new TagDao(connection);
    }

    public void saveNode(Node node) throws SQLException {
        var statement = connection.createStatement();
        var s =
                "INSERT INTO nodes VALUES (%d, %f, %f, '%s', %d, %s, %d, %d, '%s');"
                        .formatted(
                                node.getId(),
                                node.getLat(),
                                node.getLon(),
                                node.getUser().replace("'", "''"),
                                node.getUid(),
                                node.isVisible(),
                                node.getVersion(),
                                node.getChangeset(),
                                node.getTimestamp().toString()
                        );
        statement.executeUpdate(s);

        for (Tag tag : node.getTag()) {
            tagDao.save(tag, node.getId().longValue());
        }
    }

    public Node getNode(Long id) throws SQLException {
        var statement = connection.createStatement();
        var resultSet = statement.executeQuery("SELECT * FROM nodes WHERE nodes.id = %d".formatted(id));
        var node = new Node();
        node.setId(resultSet.getObject("id") == null ? null : BigInteger.valueOf(resultSet.getLong("id")));
        node.setLat(resultSet.getObject("lat") == null ? null : resultSet.getDouble("lat"));
        node.setLon(resultSet.getObject("lon") == null ? null : resultSet.getDouble("lon"));
        node.setUser(resultSet.getString("user_name"));
        node.setUid(resultSet.getObject("user_id") == null ? null : BigInteger.valueOf(resultSet.getLong("user_id")));
        node.setVisible(resultSet.getObject("visible") == null ? null : resultSet.getBoolean("visible"));
        node.setVersion(resultSet.getObject("version") == null ? null : BigInteger.valueOf(resultSet.getLong("version")));
        node.setChangeset(resultSet.getObject("changeset") == null ? null : BigInteger.valueOf(resultSet.getLong("changeset")));
        node.setTimestamp(resultSet.getObject("timestamp") == null ? null : DatatypeFactory.newDefaultInstance().newXMLGregorianCalendar(resultSet.getString("timestamp")));
        var tags = node.getTag();
        tags.addAll(tagDao.getAllByNodeId(id));
        return node;
    }

    public void updateNode(Node node) throws SQLException {
        var statement = connection.createStatement();
        statement.executeUpdate("""
                UPDATE nodes SET
                    lat = %f,
                    lon = %f,
                    user_name = '%s',
                    user_id = %d,
                    visible = %s,
                    version = %d,
                    changeset = %d,
                    timestamp = '%s'
                WHERE id = %d;
                """
                .formatted(
                        node.getLat(),
                        node.getLon(),
                        node.getUser().replace("'", "''"),
                        node.getUid(),
                        node.isVisible(),
                        node.getVersion(),
                        node.getChangeset(),
                        node.getTimestamp().toString(),
                        node.getId()
                )
        );
    }

    public void deleteNode(Long id) throws SQLException {
        var statement = connection.createStatement();
        statement.executeUpdate("DELETE FROM nodes WHERE id = %d".formatted(id));
    }
}
