package org.ajani2001.lab1;

import org.apache.commons.cli.*;
import org.apache.commons.compress.compressors.CompressorException;
import org.apache.commons.compress.compressors.CompressorStreamFactory;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException, CompressorException, XMLStreamException {
        var options = new Options();
        options.addRequiredOption("T", "target-xml", true, "Path to the xml file");

        var parser = new DefaultParser();
        CommandLine cmd;
        try {
            cmd = parser.parse(options, args);
        } catch (MissingOptionException e) {
            new HelpFormatter().printHelp("lab1", options);
            return;
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }

        var filePath = cmd.getOptionValue("target-xml");
        var fileInputStream = new FileInputStream(filePath);
        var bufferedInputStream = new BufferedInputStream(fileInputStream);
        var compressorInputStream = new CompressorStreamFactory().createCompressorInputStream(CompressorStreamFactory.BZIP2, bufferedInputStream);
        var xmlFileReader = new InputStreamReader(compressorInputStream, StandardCharsets.UTF_8);
        var xmlEventReader = XMLInputFactory.newFactory().createXMLEventReader(xmlFileReader);

        var xmlDocumentProcessor = new XMLDocumentProcessor();
        var userEditingCollector = new UserEditingCollector();
        var nodeKeyCollector = new NodeKeyCollector();
        xmlDocumentProcessor.addCollector(userEditingCollector);
        xmlDocumentProcessor.addCollector(nodeKeyCollector);
        xmlDocumentProcessor.process(xmlEventReader);

        //userEditingCollector.getResults().entrySet().stream().sorted(Comparator.comparingInt((Map.Entry<String, Integer> entryA) -> entryA.getValue()).reversed().thenComparing(Map.Entry::getKey)).forEach(entry -> System.out.printf("%s: %d%s", entry.getKey(), entry.getValue(), System.lineSeparator()));
        nodeKeyCollector.getResults().forEach((key, value) -> System.out.printf("%s: %d%s", key, value, System.lineSeparator()));
    }
}
