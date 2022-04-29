package org.ajani2001.lab2.node_processors;

import org.ajani2001.Node;

import java.util.ArrayList;
import java.util.List;

public class NodeCollector implements NodeProcessor {
    private final List<Node> collectedNodes = new ArrayList<>();

    @Override
    public void process(Node node) {
        collectedNodes.add(node);
    }

    public List<Node> getCollectedNodes() {
        return collectedNodes;
    }
}
