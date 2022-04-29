package org.ajani2001.lab2;

import jakarta.xml.bind.JAXBContext;
import org.ajani2001.Node;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import java.util.ArrayList;
import java.util.List;

public class XMLDocumentProcessor {
    private static final Logger logger = LogManager.getLogger();
    private final List<Node> nodes = new ArrayList<>();

    public void process(XMLEventReader reader, JAXBContext context) throws XMLStreamException {
        logger.info("Start processing");
        while (reader.hasNext()) {
            var nextEvent = reader.peek();
            if(nextEvent.isStartElement() && nextEvent.asStartElement().getName().getLocalPart().equals("node")) {
                var unmarshaller = context.createUnmarshaller();
                var node = unmarshaller.unmarshal(reader, Node.class).getValue();
                nodes.add(node);
            }
        }
        logger.info("finish processing");
    }

    public List<Node> getNodes() {
        return nodes;
    }
}
