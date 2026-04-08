package edu.ucalgary.oop;

import java.util.Scanner;
import java.util.ArrayList;
    

public class LocationCLI {
    private LocationRepo locationRepo;
    private Scanner scanner;

    public LocationCLI(Scanner scanner) {
        this.scanner = scanner;
        this.locationRepo = new LocationRepo();

    }

    public void show() {
        boolean prog_running = true;

        while (prog_running) {
            System.out.println("\n---Location Menu---");
            System.out.println("1 - View Locations");
            System.out.println("2 - View Disaster Victims at a Location");
            System.out.println("3 - View Supplies at a Location");
            System.out.println("4 - Add a New Location");
            System.out.println("5 - Update a Location");
            System.out.println("6 - Return to Main Menu");
            System.out.println();


            int choice = getIntInput("Enter Choice (1 to 6): ", 1, 6);

            if (choice == 1) {
                viewLocations();
            
            } else if (choice == 2) {
                viewDisasterVictimsAtLocation();
            } else if (choice == 3) {
                viewDisasterSuppliesAtLocation();
            } else if (choice == 4) {
                addLocation();
            } else if (choice == 5) {
                updateLocation();
            } else if (choice == 6) {
                prog_running = false;
            }

        }
      
    }


    
    private void viewLocations() {
        ArrayList<Location> locations = locationRepo.getLocations();
        
        if (locations.isEmpty()) {
            System.out.println("No Locations Found");
            return;
        
        } else {
            System.out.println("\n---All Locations---: ");
            for (int i = 0; i < locations.size(); i++) {
                Location location = locations.get(i);

                System.out.println((i+1) + ".   " + location.getName());
                System.out.println("    Address: " + location.getAddress());
                System.out.println("    ID: " + location.getLocationId());
                System.out.println();

            }
        }
    }



    private void viewDisasterVictimsAtLocation() {
        ArrayList<Location> locations = locationRepo.getLocations();

        if (locations.isEmpty()) {
            System.out.println("No locations found");
            return;
        }
        System.out.println("Select a Location");
        System.out.println();

        int i;
        for (i = 0; i < locations.size(); i++) {
            Location location = locations.get(i);
            System.out.println(( i + 1) + "." + location.getName());
        }
        int choice = getIntInput("Enter location number: ", 1, locations.size()) -1;
        Location chosenLocation = locations.get(choice);
        ArrayList<DisasterVictim> disasterVictims = locationRepo.getDisasterVictimsAtLocation(chosenLocation.getLocationId());
        
        if (disasterVictims.isEmpty()) {
            System.out.println("No disaster victims at this location");
            return;
        }
        System.out.println("Disaster victims at " + chosenLocation.getName());
        int x;
        for (x = 0; x < disasterVictims.size(); x++) {
            DisasterVictim disasterVictim = disasterVictims.get(x);
            System.out.println((x+1) + "." + disasterVictim.getFirstName() + " " + disasterVictim.getLastName());
            System.out.println("    Gender: " + disasterVictim.getGender());
            System.out.println(); 
        }

    }

    private void viewDisasterSuppliesAtLocation() {
        ArrayList<Location> locations = locationRepo.getLocations();

        if (locations.isEmpty()) {
            System.out.println("No locations found");
            return;
        }
        System.out.println("\n Select a Location");
        int i;
        for (i = 0; i < locations.size(); i++) {
            Location location = locations.get(i);
            System.out.println(( i + 1) + "." + location.getName());
        }
        int choice = getIntInput("Enter location number: ", 1, locations.size()) -1;
        Location chosenLocation = locations.get(choice);
        ArrayList<Supply> supplies = locationRepo.getSuppliesAtLocation(chosenLocation.getLocationId());
        
        if (supplies.isEmpty()) {
            System.out.println("No supplies at this location");
            return;
        }
        System.out.println("\n Supplies " + chosenLocation.getName());
        int x;
        String supply_status;
        String expiryInformation;
        for (x = 0; x < supplies.size(); x++) {
            Supply supply = supplies.get(x);

            if (supply.getAllocatedId() == null) {
                supply_status = "Available to allocate";
            } else {
                supply_status = "Allocated to disaster victim: " + supply.getAllocatedId();
            }
            if ((supply.isPerishable()) && (supply.getExpirationDate() != null)) {
                if (supply.isExpired()) {
                    expiryInformation = "Expired on " + supply.getExpirationDate();
                } else {
                    expiryInformation = "Expires on: " + supply.getExpirationDate();
                }
            } else {
                expiryInformation = "Non-perishable (No expiration date)";
            }


            System.out.println((i+1) + "." + supply.getType());
            System.out.println("    ID: " + supply.getSupplyId());
            System.out.println("    Status of Supply: " + supply_status); 
            System.out.println("    " + expiryInformation);
            System.out.println();
        }

    }

    private void addLocation() {
        System.out.println("\n Add a New Location ");
        System.out.println("Enter Location Name: ");
        String locationName = scanner.nextLine().trim();
        
        if (locationName.isEmpty()) {
            System.out.println("Location Name can't be Empty");
            return;
        }

        System.out.println("\n Add Location Address ");
        String locationAddress = scanner.nextLine().trim();           
        if (locationAddress.isEmpty()) {
            System.out.println("Location Address can't be Empty");
            return;
        }

        Location newLocation = new Location(locationName, locationAddress);
        locationRepo.addLocation(newLocation);
        ActionLogger logger = ActionLogger.getInstance();
        logger.logAdded("location " + newLocation.getLocationId() + " | Name: " + locationName + " | Address: " + locationAddress);
        System.out.println("Location Added Successfully!");


    }

    private void updateLocation() {
        ArrayList<Location> locations = locationRepo.getLocations();
        if (locations.isEmpty()) {
            System.out.println("No locations found ");
            return;
        }
        System.out.println("\n Select Location to Update ");
        int i;
        for (i =0; i < locations.size(); i++) {
            Location location = locations.get(i);

            System.out.println((i+1) + "." + location.getName());
            System.out.println("    Address: " + location.getAddress());
            System.out.println();


        }
        int choice = getIntInput("Enter Location Number (1 to 3): ", 1, locations.size()) -1;
        Location chosenLocation = locations.get(choice);

        System.out.println("\nUpdating: " + chosenLocation.getName());
        System.out.println("1 - Update Location Name");
        System.out.println("2 - Update Location Address");
        System.out.println("3 - Cancel");

        int updateChoice = getIntInput("Enter Choice Number (1 to 3): ", 1, 3);
        if (updateChoice == 1) {
            System.out.print("Enter new location name: ");
            String newLocationName = scanner.nextLine().trim();
            if (newLocationName.isEmpty()) {
                System.out.println("Name can't be empty. Cancelled");
                return;
            }
            String oldLocationName = chosenLocation.getName();
            chosenLocation.setName(newLocationName);
            locationRepo.updateLocation(chosenLocation);

            ActionLogger logger = ActionLogger.getInstance();
            logger.logUpdated("location " + chosenLocation.getLocationId() + " | Name: " + oldLocationName + " ->" + newLocationName);
            System.out.println("Location name updated successfully!");
        } else if (updateChoice == 2) {
            System.out.print("Enter new location address: ");
            String newLocationAddress = scanner.nextLine().trim();
            if (newLocationAddress.isEmpty()) {
                System.out.println("Address can't be empty. Cancelled");
                return;
            }
            String oldAddressName = chosenLocation.getAddress();
            chosenLocation.setAddress(newLocationAddress);
            locationRepo.updateLocation(chosenLocation);

            ActionLogger logger = ActionLogger.getInstance();
            logger.logUpdated("location " + chosenLocation.getLocationId() + " | Address: " + oldAddressName + " ->" + newLocationAddress);
            System.out.println("Location address updated successfully!");
        } else if (updateChoice == 3) {
            System.out.println("Cancelled");
        }


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





























































