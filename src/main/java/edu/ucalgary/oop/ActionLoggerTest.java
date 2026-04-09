package edu.ucalgary.oop;


import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;




public class ActionLoggerTest {
    private static final Path LOG_PATH = Path.of("data", "action_log.txt");
    private String originalContents;
    private boolean fileOriginallyExisted;
    


    @Before
    public void setUp() throws IOException {
        Files.createDirectories(LOG_PATH.getParent());
        fileOriginallyExisted = Files.exists(LOG_PATH);
        if (fileOriginallyExisted) {
            originalContents = Files.readString(LOG_PATH, StandardCharsets.UTF_8);
        } else {
            originalContents = "";
        }

        Files.writeString(LOG_PATH, "", StandardCharsets.UTF_8);
    }



    @After
    public void delete() throws IOException {
        if (fileOriginallyExisted) {
            Files.writeString(LOG_PATH, originalContents, StandardCharsets.UTF_8);
        } else {
            Files.deleteIfExists(LOG_PATH);
        }
    }



    @Test
    public void testGetInstanceReturnsSameObject() {
        ActionLogger logger1 = ActionLogger.getInstance();
        ActionLogger logger2 = ActionLogger.getInstance();
        assertSame(logger1, logger2);
    }



    @Test
    public void testLogAddedWritesToFile() throws IOException {
        ActionLogger logger = ActionLogger.getInstance();
        logger.logAdded("Supply");
        String contents = Files.readString(LOG_PATH, StandardCharsets.UTF_8);
        assertTrue(contents.contains("Added: Supply"));
    }


    @Test
    public void testLogUpdatedWritesToFile() throws IOException {
        ActionLogger logger = ActionLogger.getInstance();
        logger.logUpdated("Location A");
        String contents = Files.readString(LOG_PATH, StandardCharsets.UTF_8);
        assertTrue(contents.contains("Updated: Location A"));
    }






    @Test
    public void testLogSoftDeletedWritesToFile() throws IOException {
        ActionLogger logger = ActionLogger.getInstance();
        logger.logSoftDeleted("Disaster Victim 1");
        String contents = Files.readString(LOG_PATH, StandardCharsets.UTF_8);
        assertTrue(contents.contains("Soft Deleted: Disaster Victim 1"));
    }


    @Test
    public void testLogHardDeletedWritesToFile() throws IOException {
        ActionLogger logger = ActionLogger.getInstance();
        logger.logHardDeleted("Disaster Victim 2");
        String contents = Files.readString(LOG_PATH, StandardCharsets.UTF_8);
        assertTrue(contents.contains("Hard Deleted: Disaster Victim 2"));
    }




    
}
