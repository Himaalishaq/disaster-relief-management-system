package edu.ucalgary.oop;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import java.util.Scanner;



import java.util.ArrayList;



public class SupplyCLI {
    private Scanner scanner;
    private boolean expiredSupplyLabel;
    private SupplyRepo supplyRepo;
    private DisasterVictimRepo disasterVictimRepo;

   
    
    public SupplyCLI(Scanner scanner) {
        this.scanner = scanner;
        this.expiredSupplyLabel = false;
        this.supplyRepo = new SupplyRepo();
        this.disasterVictimRepo = new DisasterVictimRepo();
    }

    public void display() {
        boolean prog_running = true;
        if (!expiredSupplyLabel) {
            showExpiredLabel();
            expiredSupplyLabel = true;
        }
        while (prog_running) {
            System.out.println("\n---Supply Menu---");
            System.out.println("1 - View All Supplies");
            System.out.println("2 - Add a New Supply Item");
            System.out.println("3 - Allocate a Supply Item");
            System.out.println("4 - Return to Main Menu");

            int choice = getIntInput("Enter Choice from 1 to 4: ", 1, 4);

            if (choice == 1) {
                viewSupplies();
            
            } else if (choice == 2) {
                addSupply();
            } else if (choice == 3) {
                allocateSupply();
            } else if (choice == 4) {
                prog_running = false;
            }

        }
    }
    

    private void showExpiredLabel() {
        ArrayList<Supply> expiredSupplies = supplyRepo.getExpiredSupplies();
        if (expiredSupplies.isEmpty()) {
            System.out.println("No expired items");
            return;
        }
        int i;
        for (i = 0;  i < expiredSupplies.size(); i++) {
            Supply supply = expiredSupplies.get(i);
            System.out.println(" " + supply.getType() + " (ID: " + supply.getSupplyId() + ")" + "has been expired since " + supply.getExpirationDate());
        }
    }

    private void viewSupplies() {
        String supplyStatus;
        String expiryInformation;
        ArrayList<Supply> supplies = supplyRepo.getSupplies();
        if (supplies.isEmpty()) {
            System.out.println("No Supplies Found");
            return;
        }
        System.out.println("All Supplies: ");
        System.out.println();
        int i;
        for (i = 0; i < supplies.size(); i++) {
            Supply supply = supplies.get(i);
            if (supply.getAllocatedId() == null) {
                supplyStatus = "Available";

            } else {
                supplyStatus = "Allocated to Disaster Victim ID: " + supply.getAllocatedId();
            }
            if ((supply.isPerishable()) && (supply.getExpirationDate() != null)) {
                if (supply.isExpired()) {
                    expiryInformation = "Expired on date: " + supply.getExpirationDate();
                
                } else {
                    expiryInformation = "Expires on date: " + supply.getExpirationDate();
                }
            } else if (supply.isPerishable()) {
                expiryInformation = "No expiration date set";
            
            } else {
                expiryInformation = "Not perishable";
            }
            System.out.println((i + 1) + " " + supply.getType());
            System.out.println("    ID: " + supply.getSupplyId());
            System.out.println("    Status: " + supplyStatus);
            System.out.println("    " + expiryInformation);
            System.out.println();


        }

    }

    private void addSupply() {
        System.out.println("\nAdd New Supply");
        System.out.print("Enter Supply Type (e.g food, medicine, blanket): ");
        String type = scanner.nextLine().trim();

        if (type.isEmpty()) {
            System.out.println("Supply can't be empty");
            return;
        }
        Supply newSupply = new Supply(type, 1);

        if (newSupply.isPerishable()) {
            System.out.println("This supply type is perishable");
            LocalDate expirationDate = getDateInput("Enter the expiry date (YYYY-MM-DD): ");
            newSupply.setExpirationDate(expirationDate);
        } else {
            System.out.println("This supply type is not perishable. No expiry date needed.");
        }
        supplyRepo.addSupply(newSupply);
        ActionLogger logger = ActionLogger.getInstance();
        logger.logAdded("supply " + newSupply.getSupplyId() + " | Type: " + newSupply.getType());
        System.out.println("Supply: " + type + " added successfully");
    }

    private void allocateSupply() {
        String expiryInformation;
        System.out.println("\nAllocate Supply to Disaster Victim");
        ArrayList<Supply> available = supplyRepo.getSupplies();

        if (available.isEmpty()) {
            System.out.println("\nNo available supplies to allocate ");
            return;
        }
        System.out.println("\nAvailable Supplies: ");
        int i;
        
        for (i = 0; i < available.size(); i++) {
            Supply supply = available.get(i);
            if ((supply.isPerishable()) && (supply.getExpirationDate() != null)) {
                expiryInformation = " Expires: " + supply.getExpirationDate();
            } else {
                expiryInformation = "No Expiry Information";
            }
            System.out.println((i+1) + "." + supply.getType() + " (ID: " + supply.getSupplyId() + ")" + expiryInformation );


        }
        int choice = getIntInput("Select choice of supply: ", 1, available.size()) -1;
        Supply chosenSupply = available.get(choice);
        ArrayList<DisasterVictim>disasterVictims = disasterVictimRepo.getDisasterVictims();
        if (disasterVictims.isEmpty()) {
            System.out.println("No disaster victims found to allocate to");
            return;
        }
        System.out.println("\nSelect disaster victims to allocate to: ");
        int x;
        for (x = 0; x < disasterVictims.size(); x++) {
            DisasterVictim disasterVictim = disasterVictims.get(x);
            System.out.println((x+1) + "." + disasterVictim.getFirstName() + " " + disasterVictim.getLastName() + "(ID: " + disasterVictim.getPersonId() + ")" );
        }
        int disasterVictimChoice = getIntInput("Choose disaster victim number to allocate to: ", 1, disasterVictims.size()) -1;
        DisasterVictim selectedVictim = disasterVictims.get(disasterVictimChoice);
        System.out.println("\nAllocating: "  + chosenSupply.getType() + " to " + selectedVictim.getFirstName() + " " + selectedVictim.getLastName());
        supplyRepo.allocateSupply(chosenSupply.getSupplyId(), selectedVictim.getPersonId());

        ActionLogger logger = ActionLogger.getInstance();
        logger.logUpdated("supply " + chosenSupply.getSupplyId() + " | Type: " + chosenSupply.getType() + "...allocated to disaster victim " + selectedVictim.getFirstName() + " " + selectedVictim.getLastName() + " (ID: " + selectedVictim.getPersonId() + ")"); 
        System.out.println("Supply allocated successfully!");

            
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

    private LocalDate getDateInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return LocalDate.parse(input, DateTimeFormatter.ISO_LOCAL_DATE);
            
            } catch (DateTimeParseException e) {
                System.out.println("Invalid Date Format. (Correct Format =  YYYY-MM-DD)");
            }
        }
    }
}













    
    
    












