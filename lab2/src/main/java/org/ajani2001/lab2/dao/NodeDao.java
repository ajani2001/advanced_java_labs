package org.ajani2001.lab2.dao;

import org.ajani2001.lab2.xml.Node;

import javax.xml.datatype.DatatypeFactory;
import java.math.BigInteger;
import java.util.List;

public class NodeDao {
    private Long id;
    private Double lat;
    private Double lon;
    private String userName;
    private Long userId;
    private Boolean visible;
    private Long version;
    private Long changeset;
    private String timestamp;

    private List<TagDao> tags;

    public NodeDao() {}

    public NodeDao(Node xmlNode) {
        this.id = xmlNode.getId().longValue();
        this.lat = xmlNode.getLat();
        this.lon = xmlNode.getLon();
        this.userName = xmlNode.getUser();
        this.userId = xmlNode.getUid().longValue();
        this.visible = xmlNode.isVisible();
        this.version = xmlNode.getVersion().longValue();
        this.changeset = xmlNode.getChangeset().longValue();
        this.timestamp = xmlNode.getTimestamp().toString();
        this.tags = xmlNode.getTag().stream().map(TagDao::new).toList();
    }

    public Node toXmlNode() {
        var result = new Node();
        result.setId(BigInteger.valueOf(id));
        result.setLat(lat);
        result.setLon(lon);
        result.setUser(userName);
        result.setUid(BigInteger.valueOf(userId));
        result.setVisible(visible);
        result.setVersion(BigInteger.valueOf(version));
        result.setChangeset(BigInteger.valueOf(changeset));
        result.setTimestamp(DatatypeFactory.newDefaultInstance().newXMLGregorianCalendar(timestamp));
        result.getTag().addAll(tags.stream().map(TagDao::toXmlTag).toList());
        return result;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Long getChangeset() {
        return changeset;
    }

    public void setChangeset(Long changeset) {
        this.changeset = changeset;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public List<TagDao> getTags() {
        return tags;
    }

    public void setTags(List<TagDao> tags) {
        this.tags = tags;
    }
}
