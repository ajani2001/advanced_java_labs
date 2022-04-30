package org.ajani2001.lab2.dao;

import org.ajani2001.lab2.xml.Relation;

import java.util.List;

public class RelationDao {
    private Long id;
    private String userName;
    private Long userId;
    private Boolean visible;
    private Long version;
    private Long changeset;
    private String timestamp;

    private List<Long> nodeIds;
    private List<Long> wayIds;
    private List<Long> relationIds;
    private List<TagDao> tags;

    public RelationDao() {}
    
    public RelationDao(Relation xmlRelation) {
        this.id = xmlRelation.getId().longValue();
        this.userName = xmlRelation.getUser();
        this.userId = xmlRelation.getUid().longValue();
        this.visible = xmlRelation.isVisible();
        this.version = xmlRelation.getVersion().longValue();
        this.changeset = xmlRelation.getChangeset().longValue();
        this.timestamp = xmlRelation.getTimestamp().toString();

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

    public void setNodeIds(List<Long> nodeIds) {
        this.nodeIds = nodeIds;
    }

    public List<Long> getWayIds() {
        return wayIds;
    }

    public void setWayIds(List<Long> wayIds) {
        this.wayIds = wayIds;
    }

    public List<Long> getRelationIds() {
        return relationIds;
    }

    public void setRelationIds(List<Long> relationIds) {
        this.relationIds = relationIds;
    }

    public List<TagDao> getTags() {
        return tags;
    }

    public void setTags(List<TagDao> tags) {
        this.tags = tags;
    }
}
