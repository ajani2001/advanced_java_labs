package org.ajani2001.lab1;

import javax.xml.namespace.QName;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.util.Map;
import java.util.TreeMap;

public class NodeKeyCollector implements XMLEventProcessor {
    private final Map<String, Integer> markedNodeCount = new TreeMap<>();
    private int currentDepth = 0;

    @Override
    public void process(XMLEvent e) {
        if(e instanceof StartElement) {
            switch (((StartElement) e).getName().getLocalPart()) {
                case "node" -> {
                    if(currentDepth == 0) currentDepth = 1;
                }
                case "tag" -> {
                    if(currentDepth == 1) {
                        var tagName = ((StartElement) e).getAttributeByName(new QName("k")).getValue();
                        var oldCounter = markedNodeCount.get(tagName);
                        if(oldCounter == null) {
                            oldCounter = 0;
                        }
                        markedNodeCount.put(tagName, oldCounter + 1);
                    }
                    if(currentDepth > 0) ++currentDepth;
                }
                default -> {
                    if(currentDepth > 0) ++currentDepth;
                }
            }
        } else if(e instanceof EndElement) {
            if(currentDepth > 0) --currentDepth;
        }
    }

    public Map<String, Integer> getResults() {
        return markedNodeCount;
    }
}
