package org.ajani2001.lab2.dao;

import org.ajani2001.lab2.xml.Tag;

public class TagDao {
    private Long id;
    private String key;
    private String value;

    public TagDao() {}

    public TagDao(Tag xmlTag) {
        this.key = xmlTag.getK();
        this.value = xmlTag.getV();
    }

    public Tag toXmlTag() {
        var result = new Tag();
        result.setK(key);
        result.setV(value);
        return result;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
