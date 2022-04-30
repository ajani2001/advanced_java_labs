package org.ajani2001.lab2.node_processors;

import org.ajani2001.lab2.xml.Node;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class NodeKeyCollector implements NodeProcessor {
    private static final Logger logger = LogManager.getLogger();
    private final Map<String, Integer> markedNodeCount = new TreeMap<>();

    @Override
    public void process(Node node) {
        node.getTag().forEach(tag -> {
            var tagName = tag.getK();
            var oldCounter = markedNodeCount.get(tagName);
            markedNodeCount.put(tagName, oldCounter == null ? 1 : oldCounter + 1);
        });
    }

    public String getResults() {
        return markedNodeCount.entrySet().stream()
                .map(entry -> String.format("%s: %d", entry.getKey(), entry.getValue()))
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
