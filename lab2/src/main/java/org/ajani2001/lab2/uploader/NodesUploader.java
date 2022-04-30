package org.ajani2001.lab2.uploader;

import org.ajani2001.lab2.dao.NodeDao;

import java.sql.SQLException;
import java.util.List;

public interface NodesUploader {
    public void upload(List<NodeDao> nodes) throws SQLException;
}
