package org.ajani2001.lab1;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import java.util.ArrayList;
import java.util.List;

public class XMLDocumentProcessor {
    private final List<XMLEventProcessor> collectors = new ArrayList<>();

    public void addCollector(XMLEventProcessor collector) {
        collectors.add(collector);
    }

    public void process(XMLEventReader reader) throws XMLStreamException {
        while (reader.hasNext()) {
            var event = reader.nextEvent();
            for (var collector: collectors) {
                collector.process(event);
            }
        }
    }
}
