package edu.ucalgary.oop;
import java.util.Scanner;


public class MainMenuCLI {
    private Scanner scanner;
    private CulturalOptions culturalOptions;
    
    public MainMenuCLI(CulturalOptions culturalOptions) {
        this.scanner = new Scanner(System.in);
        this.culturalOptions = culturalOptions;
    }

    public void start() {
        System.out.println("Welcome to the Disaster Relief System!");
        boolean prog_running = true;

        while (prog_running) {

            printMainMenu();

            int choice = getIntInput("Please enter choice (1 to 6): ", 1, 6);

            if (choice == 1) {
                DisasterVictimCLI disasterVictimCLI = new DisasterVictimCLI(scanner, culturalOptions);
                disasterVictimCLI.displayVictims();

            } else if (choice == 2) {
                SupplyCLI supplyCLI = new SupplyCLI(scanner);
                supplyCLI.display();

            } else if (choice == 3) {
                InquiryCLI inquiryCLI = new InquiryCLI(scanner);
                inquiryCLI.display();

            } else if (choice == 4) {
                LocationCLI locationCLI = new LocationCLI(scanner);
                locationCLI.show();

            } else if (choice == 5) {
                SkillCLI skillCLI = new SkillCLI(scanner);
                skillCLI.display();
            } else if (choice == 6) {
                System.out.println("Exiting the Disaster Relief System....");
                prog_running = false;
            }
        }
        scanner.close();
    }
    private void printMainMenu() {
        System.out.println("\n---Main Menu---");
        System.out.println(" 1 - Disaster Victims");
        System.out.println(" 2 - Supplies");
        System.out.println(" 3 - Inquiries ");
        System.out.println(" 4 - Locations");
        System.out.println(" 5 - Skills and Volunteers");
        System.out.println(" 6 - Exit");
        System.out.println();
        
        
    }
    private int getIntInput(String prompt, int min, int max) {
        int val;
        while (true) {
            System.out.print(prompt);
            try {
                val = Integer.parseInt(scanner.nextLine().trim());
                if ((val >= min) && (val <=max)) {
                    return val;
                }
                System.out.println("Please enter a number between " + min + " and " + max );
            
            } catch (NumberFormatException e) {
                System.out.println("Invalid Input. Please try again");
            }
        }
    }

}