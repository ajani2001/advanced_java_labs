package org.ajani2001.lab2;

import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.util.StreamReaderDelegate;

public class NamespaceAdapder extends StreamReaderDelegate {
    public NamespaceAdapder(XMLStreamReader reader) {
        super(reader);
    }

    @Override
    public String getNamespaceURI() {
        return "http://openstreetmap.org/osm/0.6";
    }
}
