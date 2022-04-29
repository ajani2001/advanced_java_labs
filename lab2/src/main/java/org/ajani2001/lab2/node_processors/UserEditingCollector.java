package org.ajani2001.lab2.node_processors;

import org.ajani2001.Node;

import java.math.BigInteger;
import java.util.*;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

public class UserEditingCollector implements NodeProcessor {
    private final Map<String, Set<BigInteger>> userEditingCount = new TreeMap<>();

    @Override
    public void process(Node node) {
        var username = node.getUser();
        var changeset = node.getChangeset();
        var changesetSet = userEditingCount.computeIfAbsent(username, k -> new TreeSet<>());
        changesetSet.add(changeset);
    }

    public String getResults() {
        return userEditingCount.entrySet().stream()
                .map(entry -> Map.entry(entry.getKey(), entry.getValue().size()))
                .sorted(Comparator.comparingInt((ToIntFunction<Map.Entry<String, Integer>>) Map.Entry::getValue).reversed().thenComparing(Map.Entry::getKey))
                .map(entry -> String.format("%s: %d", entry.getKey(), entry.getValue()))
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
