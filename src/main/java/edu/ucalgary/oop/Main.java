package edu.ucalgary.oop;

import java.util.ArrayList;


public class Main {
    public static void main(String[] args) {
        try{
            CulturalOptions culturalOptions = AvailableRequirementsLoader.load();
            
            MainMenuCLI app = new MainMenuCLI(culturalOptions);
            app.start();
        } catch (Exception e) {
            System.out.println("Could not load file: available_requirements.ser");
            e.printStackTrace();
            return;
        }
    

        
    }
}










