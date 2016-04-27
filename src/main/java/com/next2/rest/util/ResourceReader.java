package com.next2.rest.util;

import org.apache.log4j.Logger;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class ResourceReader {

    final static Logger log = Logger.getLogger(ResourceReader.class);

    public String readFromResources(String fileLocation) {
        ClassLoader classLoader = getClass().getClassLoader();
        File file;
        String filePath = null;

        try {
            file = new File(classLoader.getResource(fileLocation).getFile());
            filePath = file.getAbsolutePath();
        } catch (NullPointerException e) {
            log.error("Unable to find the file: " + fileLocation);
            log.error(e);
            System.exit(1);
        }

        try {
            String fileAsString = readFile(filePath, StandardCharsets.UTF_8);
            log.debug("The content of the file:\n" + fileAsString);
            return fileAsString;
        } catch (NullPointerException | IOException e) {
            log.error("Unable to read file" + filePath);
        }
        return null;
    }

    public Properties getProperties(String propertyFileName) throws NullPointerException{
        Properties prop = new Properties();
        try {
            prop.load(ResourceReader.class.getClassLoader().getResourceAsStream(propertyFileName));
        } catch (NullPointerException | IOException e) {
            log.error("Unable to find the properties file: " + propertyFileName);
            log.error(e);
            System.exit(1);
        }
        return prop;
    }

    static String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    public static void recordForUnitTests(String string) {
        String filename = null;
        String directory = "src/test/resources/responses/";
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();

        for (StackTraceElement element : stackTraceElements) {
            if(element.toString().contains("com.next2.rest.api")) {
                String className = element.getClassName();
                String methodName = element.getMethodName().replaceAll("[<>]", "");
                filename = className + "." + methodName;
            }
        }
        PrintWriter writer;

        try {
            writer = new PrintWriter(directory + filename + ".json", "UTF-8");
            writer.write(string);
            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}

