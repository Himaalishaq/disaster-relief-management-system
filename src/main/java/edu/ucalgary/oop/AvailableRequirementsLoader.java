package edu.ucalgary.oop;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;





public class AvailableRequirementsLoader {
    public static CulturalOptions load() throws IOException, ClassNotFoundException {
        Path path = Path.of(System.getProperty("user.dir"), "src", "main", "resources", "available_requirements.ser");
        System.out.println("Looking for file at: " + path);
        if (!Files.exists(path)) {
            throw new IOException("Couldn't find file at: " + path);
        }

            try (ObjectInputStream input = new ObjectInputStream(Files.newInputStream(path))) {
                Object obj = input.readObject();
                if (obj instanceof CulturalOptions) {
                    return (CulturalOptions) obj;
                } else {
                    throw new IOException("Data in file is not valid");

                }
            }
    }
}
        

































        





















