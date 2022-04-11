package org.ajani2001.lab1;

import javax.xml.namespace.QName;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class UserEditingCollector implements XMLEventProcessor {
    private final Map<String, Integer> userEditingCount = new TreeMap<>();

    @Override
    public void process(XMLEvent e) {
        if(e instanceof StartElement && Objects.equals(((StartElement) e).getName().getLocalPart(), "node")) {
            var username = ((StartElement) e).getAttributeByName(new QName("user")).getValue();
            var oldCounter = userEditingCount.get(username);
            if(oldCounter == null) {
                oldCounter = 0;
            }
            userEditingCount.put(username, oldCounter + 1);
        }
    }

    public Map<String, Integer> getResults() {
        return userEditingCount;
    }
}
