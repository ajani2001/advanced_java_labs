package org.ajani2001.lab2;

import javax.xml.namespace.QName;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.util.*;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

public class UserEditingCollector implements XMLEventProcessor {
    private final Map<String, Set<String>> userEditingCount = new TreeMap<>();

    @Override
    public void process(XMLEvent e) {
        if(e instanceof StartElement && Objects.equals(((StartElement) e).getName().getLocalPart(), "node")) {
            var username = ((StartElement) e).getAttributeByName(new QName("user")).getValue();
            var changeset = ((StartElement) e).getAttributeByName(QName.valueOf("changeset")).getValue();
            var changesetSet = userEditingCount.computeIfAbsent(username, k -> new TreeSet<>());
            changesetSet.add(changeset);
        }
    }

    public String getResults() {
        return userEditingCount.entrySet().stream()
                .map(entry -> Map.entry(entry.getKey(), entry.getValue().size()))
                .sorted(Comparator.comparingInt((ToIntFunction<Map.Entry<String, Integer>>) Map.Entry::getValue).reversed().thenComparing(Map.Entry::getKey))
                .map(entry -> String.format("%s: %d", entry.getKey(), entry.getValue()))
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
