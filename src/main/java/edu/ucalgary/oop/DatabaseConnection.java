package edu.ucalgary.oop;

import java.io.IOException;
import java.io.InputStream;

import java.nio.file.Files;
import java.nio.file.Path;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.Properties;


public class DatabaseConnection {
    private static final String PROPERTIES_FILE = "db.properties";

    public static Connection getConnection() throws SQLException, IOException {
        Properties properties = new Properties();


        Path path = Path.of("src", "main", "resources", PROPERTIES_FILE);

        try (InputStream input = Files.newInputStream(path)) {
            properties.load(input);
        }

        String url = properties.getProperty("db.url");
        String user = properties.getProperty("db.user");
        String password = properties.getProperty("db.password");
        return DriverManager.getConnection(url, user, password);

    }
    
}
