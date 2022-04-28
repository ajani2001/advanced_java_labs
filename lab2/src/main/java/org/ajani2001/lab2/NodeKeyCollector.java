package org.ajani2001.lab2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.namespace.QName;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class NodeKeyCollector implements XMLEventProcessor {
    private static final Logger logger = LogManager.getLogger();
    private final Map<String, Integer> markedNodeCount = new TreeMap<>();
    private int currentDepth = 0;

    @Override
    public void process(XMLEvent e) {
        if(e instanceof StartElement) {
            switch (((StartElement) e).getName().getLocalPart()) {
                case "node" -> {
                    if(currentDepth == 0) {
                        currentDepth = 1;
                    } else {
                        logger.warn("Nested nodes are not supported, results may not be correct");
                    }
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

    public String getResults() {
        return markedNodeCount.entrySet().stream()
                .map(entry -> String.format("%s: %d", entry.getKey(), entry.getValue()))
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
