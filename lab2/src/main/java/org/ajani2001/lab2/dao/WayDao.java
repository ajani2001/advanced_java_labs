package org.ajani2001.lab2.dao;

import org.ajani2001.lab2.xml.Nd;
import org.ajani2001.lab2.xml.Way;

import javax.xml.datatype.DatatypeFactory;
import java.math.BigInteger;
import java.util.List;

public class WayDao {

    private Long id;
    private String userName;
    private Long userId;
    private Boolean visible;
    private Long version;
    private Long changeset;
    private String timestamp;

    private List<Long> nodeIds;
    private List<TagDao> tags;

    public WayDao() {}
    
    public WayDao(Way xmlWay) {
        this.id = xmlWay.getId().longValue();
        this.userName = xmlWay.getUser();
        this.userId = xmlWay.getUid().longValue();
        this.visible = xmlWay.isVisible();
        this.version = xmlWay.getVersion().longValue();
        this.changeset = xmlWay.getChangeset().longValue();
        this.timestamp = xmlWay.getTimestamp().toString();
        this.nodeIds = xmlWay.getNd().stream().map(nd -> nd.getRef().longValue()).toList();
        this.tags = xmlWay.getTag().stream().map(TagDao::new).toList();
    }

    public Way toXmlWay() {
        var result = new Way();
        result.setId(BigInteger.valueOf(id));
        result.setUser(userName);
        result.setUid(BigInteger.valueOf(userId));
        result.setVisible(visible);
        result.setVersion(BigInteger.valueOf(version));
        result.setChangeset(BigInteger.valueOf(changeset));
        result.setTimestamp(DatatypeFactory.newDefaultInstance().newXMLGregorianCalendar(timestamp));
        result.getNd().addAll(nodeIds.stream().map(nodeId -> {
            var nodeRef = new Nd();
            nodeRef.setRef(BigInteger.valueOf(nodeId));
            return nodeRef;
        }).toList());
        result.getTag().addAll(tags.stream().map(TagDao::toXmlTag).toList());
        return result;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<Long> getNodeIds() {
        return nodeIds;
    }

    public void setNodes(List<Long> nodeIds) {
        this.nodeIds = nodeIds;
    }

    public List<TagDao> getTags() {
        return tags;
    }

    public void setTags(List<TagDao> tags) {
        this.tags = tags;
    }
}
