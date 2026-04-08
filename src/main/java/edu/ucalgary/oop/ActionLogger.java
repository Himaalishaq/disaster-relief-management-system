package edu.ucalgary.oop;

import java.io.PrintWriter;
import java.io.FileWriter;
import java.time.LocalDate;



public class ActionLogger {

    private ActionLogger() {
    
    }

    private static ActionLogger instance = null;
    
    private static final String LOG_FILE_PATH = "data/action_log.txt";

    public static ActionLogger getInstance() {
        if (instance == null) {
            instance = new ActionLogger();
        }
        return instance;
    }

    public void logAdded(String addedEntity) {
        String date = LocalDate.now().toString();
        String logEntry = "[" + date + "] Added: " + addedEntity;
        writeToFile(logEntry);
    }

    public void logUpdated(String updatedEntity) {
        String date = LocalDate.now().toString();
        String logEntry = "[" + date + "] Updated: " + updatedEntity;
        writeToFile(logEntry);

    }
    
    public void logSoftDeleted(String softDeletedEntity) {
        String date = LocalDate.now().toString();
        String logEntry = "[" + date + "] Soft Deleted: " + softDeletedEntity;
        writeToFile(logEntry);

    }

    public void logHardDeleted(String hardDeletedEntity) {
        String date = LocalDate.now().toString();
        String logEntry = "[" + date + "] Hard Deleted: " + hardDeletedEntity;
        writeToFile(logEntry);

    }

    private void writeToFile(String logEntry) {
        try {
            FileWriter fw = new FileWriter(LOG_FILE_PATH, true);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(logEntry);
            pw.close();
            fw.close();  
            
        } catch (Exception e) {
            System.out.println("Error writing to the log file  " + e.getMessage());
        }
    }
}