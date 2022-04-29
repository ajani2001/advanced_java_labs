package org.ajani2001.lab2;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import org.apache.commons.cli.*;
import org.apache.commons.compress.compressors.CompressorStreamFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.stream.XMLInputFactory;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class Main {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        var options = new Options();
        options.addRequiredOption("c", "compressed-xml", true, "Path to the bz2-compressed xml file");

        var parser = new DefaultParser();
        CommandLine cmd;
        try {
            cmd = parser.parse(options, args);
        } catch (MissingOptionException e) {
            new HelpFormatter().printHelp("lab1", options);
            return;
        } catch (ParseException e) {
            logger.fatal("Cannot parse cmd args", e);
            System.out.println("An error has occurred");
            return;
        }

        var filePath = cmd.getOptionValue("compressed-xml");
        logger.info("Creating a reader for file {}...", filePath);
        try(var fileInputStream = new FileInputStream(filePath)) {
            var bufferedInputStream = new BufferedInputStream(fileInputStream);
            var compressorInputStream = new CompressorStreamFactory().createCompressorInputStream(CompressorStreamFactory.BZIP2, bufferedInputStream);
            var xmlFileReader = new InputStreamReader(compressorInputStream, StandardCharsets.UTF_8);
            var xmlEventReader = XMLInputFactory.newFactory().createXMLEventReader(xmlFileReader);
            logger.info("Created.");

            JAXBContext context = JAXBContext.newInstance("a");


            var xmlDocumentProcessor = new XMLDocumentProcessor();
            var userEditingCollector = new UserEditingCollector();
            var nodeKeyCollector = new NodeKeyCollector();
//            xmlDocumentProcessor.addCollector(userEditingCollector);
//            xmlDocumentProcessor.addCollector(nodeKeyCollector);
//            xmlDocumentProcessor.process(xmlEventReader);

            System.out.println("Commit count:");
            System.out.println(userEditingCollector.getResults());

            System.out.println();
            System.out.println("Tag count:");
            System.out.println(nodeKeyCollector.getResults());
        } catch (FileNotFoundException e) {
            System.out.printf("Error: file \"%s\" is not found", filePath);
        } catch (Exception e) {
            logger.fatal("Error while processing", e);
            System.out.println("An error has occurred");
        }
    }
}
