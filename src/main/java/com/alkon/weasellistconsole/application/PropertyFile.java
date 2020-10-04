package com.alkon.weasellistconsole.application;

import com.alkon.weasellistconsole.application.model.User;
import com.alkon.weasellistconsole.application.repo.MongoWrapper;
import com.alkon.weasellistconsole.cli.commands.Mongo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import static com.alkon.weasellistconsole.cli.Constants.*;

public class PropertyFile {

    private static final String PROPERTIES_FILE_PATH = "C:\\weasellist";
    private static final String PROPERTIES_FILE_NAME = "weasellist.properties";
    private static final String PROPERTY_SEPARATOR = "::";

    private static final File PROPERTIES_FOLDER = new File(PROPERTIES_FILE_PATH);
    private static final File PROPERTIES_FILE = new File(PROPERTIES_FOLDER, PROPERTIES_FILE_NAME);

    public static final String MONGO_URL = "mongoUrl";
    public static final String CACHED_USER = "cachedUser";
    public static final String CACHED_PASS = "cachedPassword";

    public static void readProperties() throws IOException {
        // If there is no previous file, create a new one with the mongo command
        if (!PROPERTIES_FOLDER.exists()) {
            PROPERTIES_FOLDER.mkdir();
            new Mongo().execute(null);
        } else if (!PROPERTIES_FILE.exists()) {
            Files.createFile(PROPERTIES_FILE.toPath());
            new Mongo().execute(null);
        }

        // Read line by line and add properties to the ApplicationContext
        for (String line : Files.readAllLines(PROPERTIES_FILE.toPath())) {
            String[] property = line.split(PROPERTY_SEPARATOR);
            ApplicationContext.setParam(property[0], property[1]);
        }
    }

    public static void writeProperty(String name, String value) throws IOException {
        // If property already in file remove it
        if (ApplicationContext.getParam(name) != null) {
            removeProperty(name);
        }

        String property = name + PROPERTY_SEPARATOR + value + "\n";
        Files.write(PROPERTIES_FILE.toPath(), property.getBytes(), StandardOpenOption.APPEND);
        ApplicationContext.setParam(name, value); // Store the new property in the context too
    }

    public static void removeProperty(String name) throws IOException {
        List<String> lines = Files.readAllLines(PROPERTIES_FILE.toPath());
        lines.removeIf(line -> line.split(PROPERTY_SEPARATOR)[0].equals(name));

        Files.write(PROPERTIES_FILE.toPath(), lines);
    }

}
