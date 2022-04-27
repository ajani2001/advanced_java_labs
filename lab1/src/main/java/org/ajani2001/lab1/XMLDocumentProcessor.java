package org.ajani2001.lab1;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import java.util.ArrayList;
import java.util.List;

public class XMLDocumentProcessor {
    private static final Logger logger = LogManager.getLogger();
    private final List<XMLEventProcessor> collectors = new ArrayList<>();

    public void addCollector(XMLEventProcessor collector) {
        collectors.add(collector);
    }

    public void process(XMLEventReader reader) throws XMLStreamException {
        logger.info("Start processing");
        while (reader.hasNext()) {
            var event = reader.nextEvent();
            for (var collector: collectors) {
                collector.process(event);
            }
        }
        logger.info("finish processing");
    }
}
