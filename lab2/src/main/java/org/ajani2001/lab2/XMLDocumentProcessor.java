package org.ajani2001.lab2;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import org.ajani2001.Node;
import org.ajani2001.lab2.node_processors.NodeProcessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.util.ArrayList;
import java.util.List;

public class XMLDocumentProcessor {
    private static final Logger logger = LogManager.getLogger();
    private final List<NodeProcessor> nodeProcessors = new ArrayList<>();

    public void addNodeProcessor(NodeProcessor nodeProcessor) {
        nodeProcessors.add(nodeProcessor);
    }

    public void process(XMLStreamReader reader, JAXBContext context) throws XMLStreamException, JAXBException {
        logger.info("Start processing");
        while (reader.hasNext()) {
            if(reader.getEventType() == XMLStreamConstants.START_ELEMENT && reader.getName().getLocalPart().equals("node")) {
                var unmarshaller = context.createUnmarshaller();
                var node = (Node) unmarshaller.unmarshal(reader);
                nodeProcessors.forEach(nodeProcessor -> nodeProcessor.process(node));
            } else {
                reader.next();
            }
        }
        logger.info("finish processing");
    }
}
