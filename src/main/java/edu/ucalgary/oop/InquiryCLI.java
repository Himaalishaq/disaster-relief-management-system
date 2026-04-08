package edu.ucalgary.oop;


import java.util.Scanner;
import java.time.LocalDate;
import java.util.ArrayList;



public class InquiryCLI {
    private InquirerRepo inquirerRepo;
    private DisasterVictimRepo disasterVictimRepo;
    private LocationRepo locationRepo;
    private Scanner scanner;

    public InquiryCLI(Scanner scanner) {
        this.inquirerRepo = new InquirerRepo();
        this.disasterVictimRepo = new DisasterVictimRepo();
        this.locationRepo = new LocationRepo();
        this.scanner = scanner;

    }
    public void display() {
        boolean prog_running = true;
        while (prog_running) {
            System.out.println("---Inquiry Menu---");
            System.out.println("1 - View All Inquiries");
            System.out.println("2 - Add a New Inquiry");
            System.out.println("3 - Search Inquiry by Name");
            System.out.println("4 - Return to Main Menu");
            System.out.println();


            int choice = getIntInput("Enter Choice (1 to 4): ", 1, 4);

            if (choice == 1) {
                viewInquiries();
            
            } else if (choice == 2) {
                addInquiry();
            } else if (choice == 3) {
                searchInquiries();
            } else if (choice == 4) {
                prog_running = false;
            }

        }
    
        
    }
    private void viewInquiries() {
        ArrayList<ReliefService> inquiries = inquirerRepo.getInquiries();

        if (inquiries.isEmpty()) {
            System.out.println("No Inquiries Found");
            return;
        }
        System.out.println("All Inquiries: ");
        int i;
        String subjName;
        String locationName;
        for (i = 0; i < inquiries.size(); i++) {
            ReliefService inquiry = inquiries.get(i);
            String inquirerName = inquiry.getInquirer().getFirstName() + " " + inquiry.getInquirer().getLastName();



            if (inquiry.getMissingPerson() == null) {
                subjName = "Unknown";

            } else {
                subjName = inquiry.getMissingPerson().getFirstName() + " " + inquiry.getMissingPerson().getLastName();
            }

            if (inquiry.getLastKnownLocation() != null) {
                locationName = inquiry.getLastKnownLocation().getName();
            } else {
                locationName = "Not specified";
            }
            System.out.println((i + 1) + " Inquiry by: " + inquirerName);
            System.out.println("    Subject: " + subjName);
            System.out.println("    Date: " + inquiry.getDateOfInquiry());
            System.out.println("    Details: " + inquiry.getInfoProvided());
            System.out.println("    Location (Last known): " + locationName);

            System.out.println();


        }

    }
    private void addInquiry() {
        System.out.println("\nAdd New Inquiry");
        System.out.println("Select Existing Enquirer or Add New Inquirer? ");
        System.out.println(" 1 - Select Existing Inquirer ");
        System.out.println(" 2 - Add New Inquirer ");

        int choice = getIntInput("Enter Choice ", 1, 2);
        int inquirerId = -1;
        Inquirer inquirer = null;

        if (choice == 1) {
            ArrayList<Inquirer> inquirers = inquirerRepo.getInquirers();
            if (inquirers.isEmpty()) {
                System.out.println("No existing inquirers found. Please add a new one!");
                choice = 2;
            } else {
                System.out.println("\nExisting inquirers: ");
                int i;
                for (i = 0; i <inquirers.size(); i++) {
                    Inquirer inquirer2 = inquirers.get(i);
                    System.out.println((i+1) + " " + inquirer2.getFirstName() + " " + inquirer2.getLastName());

                }
                int selection = getIntInput("Select Inquirer: ", 1, inquirers.size()) -1;
                inquirer = inquirers.get(selection);
                inquirerId = inquirerRepo.getInquirerId(inquirer);
            }

        } else if (choice ==2) {
                System.out.print("Please enter inquirer first name: ");
                String firstName = scanner.nextLine().trim();
                System.out.print("Please enter inquirer last name: ");
                String lastName = scanner.nextLine().trim();
                System.out.print("Please enter any additional information (press Enter to skip): ");
                String info = scanner.nextLine().trim();
                if (info.isEmpty()) {
                    info = null;
                }

                
                inquirer = new Inquirer(firstName, lastName, null, info);
                inquirerId = inquirerRepo.addInquirer(inquirer);
                System.out.println("Inquirer Added: " + firstName + " " + lastName);

            }
        if (inquirerId == -1) {
            System.out.println("Couldn't find/create inquirer. Cancelled");
            return;
        }
        System.out.print("Enter inquiry details: ");
        String details = scanner.nextLine().trim();
        if (details.isEmpty()) {
            System.out.println("Inquiry details can't be empty. Cancelled");
            return;
        }
        System.out.println("\nIs the subject disaster victim known?");
        System.out.println(" 1 - Yes: Select from list of disaster victims");
        System.out.println(" 2 - No: Subject is unknown");

        int subjChoice = getIntInput("Enter choice: ", 1, 2);
        String subjName = "Uknown";
        int subjectPersonId = -1;

        if (subjChoice == 1) {
            ArrayList<DisasterVictim> disasterVictims = disasterVictimRepo.getDisasterVictims();
            if (disasterVictims.isEmpty()) {
                System.out.println("No disaster victims found. Subject will be set to Uknown,");

            } else {
                System.out.println("\nSelect the subject disaster victim: ");
                int i;
                for (i = 0; i < disasterVictims.size(); i++) {
                    DisasterVictim disasterVictim = disasterVictims.get(i);
                    System.out.println((i+1) + " " + disasterVictim.getFirstName() + " " + disasterVictim.getLastName());
                }
                int dvs = getIntInput("Select victim: ", 1, disasterVictims.size()) -1;
                DisasterVictim selecteDisasterVictim = disasterVictims.get(dvs);
                subjName = selecteDisasterVictim.getFirstName() + " " + selecteDisasterVictim.getLastName();

                subjectPersonId = selecteDisasterVictim.getPersonId();


            }
        }

        inquirerRepo.addInquiry(inquirerId, subjectPersonId, details);
        ActionLogger logger = ActionLogger.getInstance();
        logger.logAdded("inquiry | Inquirer: " + inquirer.getFirstName() + " " + inquirer.getLastName() + " | Subject: " + subjName + " | Details of Inquiry: " + details);

        System.out.println("Inquiry addedd successfully!");
        System.out.println();

    }



    private void searchInquiries() {
        System.out.print("\nEnter subject name to search for: ");
        String searchName = scanner.nextLine().trim();

        if (searchName.isEmpty()) {
            System.out.println("Seach name can't be empty. Cancelled");
            return;
        }
        ArrayList<ReliefService> results = inquirerRepo.searchInquiriesBySubjectName(searchName);
        System.out.println("Seach Results for: " + searchName + "...");

        if (results.isEmpty()) {
            System.out.println("No inquiries found that match that name");
            return;
        }

        int i;
        for (i = 0; i < results.size(); i++) {
            ReliefService inquiry = results.get(i);
            String inquirerName = inquiry.getInquirer().getLastName();
            String subjName;
            if (inquiry.getMissingPerson() == null) {
                subjName = "Unknown";

            } else {
                subjName = inquiry.getMissingPerson().getFirstName() + " " + inquiry.getMissingPerson().getLastName();
            }

            System.out.println((i + 1) + " Inquiry by: " + inquirerName);
            System.out.println("    Subject: " + subjName);
            System.out.println("    Date: " + inquiry.getDateOfInquiry());
            System.out.println("    Details: " + inquiry.getInfoProvided());

            System.out.println();


            
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
