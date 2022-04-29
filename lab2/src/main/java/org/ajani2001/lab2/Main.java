package org.ajani2001.lab2;

import jakarta.xml.bind.JAXBContext;
import org.ajani2001.Node;
import org.ajani2001.lab2.dao.NodeDao;
import org.ajani2001.lab2.node_processors.NodeCollector;
import org.ajani2001.lab2.node_processors.NodeKeyCollector;
import org.ajani2001.lab2.node_processors.UserEditingCollector;
import org.apache.commons.cli.*;
import org.apache.commons.compress.compressors.CompressorStreamFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.stream.XMLInputFactory;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.DriverManager;

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
            var xmlStreamReader = XMLInputFactory.newDefaultFactory().createXMLStreamReader(xmlFileReader);
            var namespaceAdapter = new NamespaceAdapder(xmlStreamReader);
            logger.info("Created.");

            JAXBContext context = JAXBContext.newInstance("org.ajani2001");
            var xmlDocumentProcessor = new XMLDocumentProcessor();
            var userEditingCollector = new UserEditingCollector();
            var nodeKeyCollector = new NodeKeyCollector();
            var nodeCollector = new NodeCollector();
            xmlDocumentProcessor.addNodeProcessor(userEditingCollector);
            xmlDocumentProcessor.addNodeProcessor(nodeKeyCollector);
            xmlDocumentProcessor.addNodeProcessor(nodeCollector);
            xmlDocumentProcessor.process(namespaceAdapter, context);

            var connection = DriverManager.getConnection("jdbc:postgresql://localhost:5435/postgres", "admin", "admin");
            var dbInitializer = new DbInitializer(connection);
            dbInitializer.initialize();
            var nodeDao = new NodeDao(connection);
            for (Node node : nodeCollector.getCollectedNodes()) {
                nodeDao.saveNode(node);
            }

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
