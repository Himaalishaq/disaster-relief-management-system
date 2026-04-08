package edu.ucalgary.oop;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import java.util.Scanner;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;



public class DisasterVictimCLI {

    private Scanner scanner;
    private DisasterVictimRepo repo;
    private FamilyRelationRepo familyRelationRepo;
    private CulturalOptions culturalOptions;
    private ArrayList<DisasterVictim> disasterVictims;
    
    public DisasterVictimCLI(Scanner scanner, CulturalOptions culturalOptions) {
        this.scanner = scanner;
        this.culturalOptions = culturalOptions;
        this.repo = new DisasterVictimRepo();
        this.disasterVictims = repo.getDisasterVictims();
        this.familyRelationRepo = new FamilyRelationRepo();
    }
    
    public void displayVictims() {
        boolean prog_running = true;
        while (prog_running) {
            System.out.println("\n---Disaster Victims---");
            System.out.println("1 - View all Disaster Victims");
            System.out.println("2 - Add a Disaster Victim");
            System.out.println("3 - Modify a Disaster Victim");
            System.out.println("4 - Delete a Disaster Victim");
            System.out.println("5 - Manage Cultural Requirements for  a Disaster Victim");
            System.out.println("6 - Manage Family Relations for  a Disaster Victim");
            System.out.println("7 - Manage Medical Records for  a Disaster Victim");
            System.out.println("8 - Return to Main Menu");
            

            int choice = getIntInput("Please enter choice (1 - 8): ", 1, 8);

            if (choice == 1 ) {
                viewVictims();
            } else if (choice == 2) {
                addDisasterVictim();
            } else if (choice == 3) {
                modifyDisasteryVictim();
            } else if (choice == 4) {
                deleteDisasterVictim();
            } else if (choice == 5) {
                manageCulturalRequirements();
            } else if (choice == 6) {
                manageFamilyRelations();
            } else if (choice == 7) {
                manageMedicalRecords();
            } else if (choice == 8) {
                System.out.println("Returning to Main Menu...");
                prog_running = false;
            }
            
        }
        
    }

    private void viewVictims() {
        System.out.println("\n---All Disaster Victims---");
        System.out.println();

        disasterVictims = repo.getDisasterVictims();
        if (disasterVictims.isEmpty()) {
            System.out.println("No Disaster Victims Found");
            return;

        }
        int i;
        String ageInformation;
        for (i = 0; i < disasterVictims.size(); i++) {
            DisasterVictim victim  = disasterVictims.get(i);
            
            if (victim.getDateOfBirth() != null) {
                ageInformation = "DOB: " + victim.getDateOfBirth();
            } else if (victim.getApproximateAge() != null) {
                ageInformation = "Approximate Age: " + victim.getApproximateAge();
            } else {
                ageInformation = "Age information is not available";
            }
            
            String comments; 
            if (victim.getComments() != null) {
                comments = victim.getComments();
            } else {
                comments = "none";
            }
            System.out.println((i + 1) + "." + victim.getFirstName() + " " + victim.getLastName());
            System.out.println("ID: " + victim.getPersonId());
            System.out.println("Gender: " + victim.getGender());
            System.out.println(ageInformation);
            System.out.println("Comments: " + comments);
            System.out.println();
        }
    }

    private void addDisasterVictim() {
        System.out.println("\nAdd a New Disaster Victim");
        System.out.print("First Name: ");
        String firstName = scanner.nextLine().trim();

        if (firstName.isEmpty()) {
            System.out.println("First name of individual can't empty");
            return;
        }
        System.out.print("Last Name: ");
        String lastName = scanner.nextLine().trim();
        
        System.out.println("\n Can exact (not approximate) Date of Birth for individual be recorded? ");
        System.out.println(" 1 - Yes : Enter exact DOB");
        System.out.println(" 2 - No : Enter approximate age");

        int choice = getIntInput("Enter choice: ", 1, 2);
        DisasterVictim addedVictim;


        if (choice == 1) {
            LocalDate dob = getDateInput("Enter date of birth of individual (YYYY-MM-DD): ");
            addedVictim = new DisasterVictim(firstName, LocalDate.now(), dob);
        } else {
            addedVictim = new DisasterVictim(firstName, LocalDate.now());
            int approxAge = getIntInput("Enter approximate age of individual: ", 0, 150);
            addedVictim.setApproximateAge(approxAge);
        }

        System.out.println("\nSelect gender of individual: ");
        System.out.println(" 1 - Man");
        System.out.println(" 2 - Woman");
        System.out.println(" 3 - Boy");
        System.out.println(" 4 - Girl");
        System.out.println(" 5 - Please specify");

        int g_choice = getIntInput("Enter choice (1 - 5): ", 1, 5);
        String gender = "please specify";
        if (g_choice == 1) {
            gender = "man";
        } else if (g_choice == 2) {
            gender = "woman";
        } else if (g_choice == 3) {
            gender = "boy";
        } else if (g_choice == 4) {
            gender = "girl";
        } else if (g_choice == 5) {
            gender = "please specify";
            gender = "please specify";
            gender = scanner.nextLine().trim();

            if (gender.isEmpty()){
                gender = "Please specify";
            }
        }
        addedVictim.setGender(gender);

        System.out.print("Please enter any additional comments (press Enter to skip): ");
        String comments = scanner.nextLine().trim();;
        if (!comments.isEmpty()) {
            addedVictim.setComments(comments);
        }
        if (!lastName.isEmpty()){
            addedVictim.setLastName(lastName);
        }
        repo.addDisasterVictim(addedVictim);
        disasterVictims = repo.getDisasterVictims();
        System.out.println("Disaster Victim:  " + firstName + " " + lastName + " added successfully!");
    }


    private void modifyDisasteryVictim() {
        viewVictims();
        if (disasterVictims.isEmpty()) {
            return;
        }
        int i = getIntInput("Select index of which disaster victim is to be modified: ", 1, disasterVictims.size()) -1;
        DisasterVictim dv = disasterVictims.get(i);
        System.out.println("\nModifying disaster victim: " + dv.getFirstName() + " " + dv.getLastName());
        System.out.println(" 1 - Modify first name");
        System.out.println(" 2 - Modify last name");
        System.out.println(" 3 - Modify gender");
        System.out.println(" 4 - Modify comments");
        System.out.println(" 5 - Modify approximate age");
        System.out.println(" 6 - Replace approximate age with actual date of birth");
        System.out.println(" 7 - No modifications. Cancelled");

        int m_choice = getIntInput("Choice (1-7): ", 1, 7);
        if (m_choice == 1) {
            System.out.print("New first name: ");
            String newFirstName = scanner.nextLine().trim();
            if (newFirstName.isEmpty()) {
                System.out.println("First name can't be empty. Cancelled");
                return;
            }
            dv.setFirstName(newFirstName);
        } else if (m_choice == 2) {
            System.out.print("New last name: ");
            String newLastName = scanner.nextLine().trim();
            if (newLastName.isEmpty()){
                System.out.println("Last name can't be empty. Cancelled");
                return;
            }
            dv.setLastName(newLastName);


        } else if (m_choice == 3) {
            System.out.println("\nSelect new gender: ");
            System.out.println(" 1 - Man");
            System.out.println(" 2 - Woman");
            System.out.println(" 3 - Boy");
            System.out.println(" 4 - Girl");
            System.out.println(" 5 - Please specify");

            int g_choice = getIntInput("Enter choice (1 - 5): ", 1, 5);
            String new_gender = "please specify";
            if (g_choice == 1) {
                new_gender = "man";
            } else if (g_choice == 2) {
                new_gender = "woman";
            } else if (g_choice == 3) {
                new_gender = "boy";
            } else if (g_choice == 4) {
                new_gender = "girl";
            } else if (g_choice == 5) {
                new_gender = "please specify";
                new_gender = scanner.nextLine().trim();

                if (new_gender.isEmpty()){
                new_gender = "Please specify";
                }
            }
            dv.setGender(new_gender);
        
        } else if (m_choice == 4) {
            System.out.println("New comments (press Enter to clear comments): ");
            String newComments = scanner.nextLine().trim();
            if (newComments.isEmpty()) {
                dv.setComments(null);
            } else {
                dv.setComments(newComments);
            }
    



        } else if (m_choice == 5) {
            if (dv.getDateOfBirth() != null) {
                System.out.println("Can't set an approximate age if individual already has a set date of birth.");
                return;
            }
            int newAge = getIntInput("Enter new approximate age: ", 0, 150);
            dv.setApproximateAge(newAge);
        } else if (m_choice == 6) {
            if (dv.getDateOfBirth() != null) {
                System.out.println("This individial already has a date of birth. Cancelled");
                return;


            }
            LocalDate newDob = getDateInput("Enter date of birth to set (YYYY-MM-DD): ");
            dv.setDateOfBirth(newDob);
            dv.setApproximateAge(null);

        } else if (m_choice == 7) {
            System.out.println(" 7 - No modifications. Cancelled");
            return;

        }
        repo.updateDisasterVictim(dv);

        disasterVictims = repo.getDisasterVictims();

        System.out.println("Disaster Victim modified successfully!");
    }


    private void deleteDisasterVictim() {
        viewVictims();
        if (disasterVictims.isEmpty()) {
            System.out.println("No disaster victims");
            return;

        }
        int i = getIntInput("Select index of which disaster victim is to be deleted: ", 1, disasterVictims.size()) -1;
        DisasterVictim dv = disasterVictims.get(i);

        System.out.println("\nDeleting Disaster Victim: " + dv.getFirstName() + " " + dv.getLastName());
        System.out.println("How would you like to delete " + dv.getFirstName() + " " + dv.getLastName() + "?");
        System.out.println(" 1 - Soft Delete (Victim's data persists in database, but hidden in user interface)");
        System.out.println(" 2 - Hard Delete (Victim, along with their related data, is all permanently removed");
        int d_choice = getIntInput("Choice of deletion: ", 1, 2);

        if (d_choice == 1) {
            String confirm = getStringInput("Please confirm the action of SOFT deleting data for: " + dv.getFirstName() + " " + dv.getLastName() + "(yes/no):", new String[]{"yes", "no"});
            if (confirm.equals("yes")) {
                repo.softDeleteDisasterVictim(dv.getPersonId());
                disasterVictims = repo.getDisasterVictims();

                ActionLogger logger = ActionLogger.getInstance();
                logger.logSoftDeleted("Disaster victim " + dv.getPersonId() + " | Name: " + dv.getFirstName() + " " + dv.getLastName());
                System.out.println("Disaster victim soft deleted succesffuly!");
            } else {
                System.out.println("Cancelled");
            }

        } else if (d_choice == 2) {
            String confirm = getStringInput("Please confirm the action of HARD deleting data for: " + dv.getFirstName() + " " + dv.getLastName() + "(yes/no):", new String[]{"yes", "no"});
            if (confirm.equals("yes")) {
 

                repo.hardDeletedisasterVictim(dv.getPersonId());
                disasterVictims = repo.getDisasterVictims();

                ActionLogger logger = ActionLogger.getInstance();
                logger.logSoftDeleted("Disaster victim " + dv.getPersonId() + " | Name: " + dv.getFirstName() + " " + dv.getLastName());
                System.out.println("Disaster victim hard deleted succesffuly!");
            } else {
                System.out.println("Cancelled");
            }
        }

    }


    private void manageCulturalRequirements() {
        viewVictims();
        if (disasterVictims.isEmpty()) {
            return;
        }
        int i = getIntInput("Select index of disaster victim: ", 1, disasterVictims.size()) -1;
        DisasterVictim dv = disasterVictims.get(i);

        System.out.println("\nManaging cultural & religious requirements for disaster victim: " + dv.getFirstName() + " " + dv.getLastName());
        System.out.println(" 1 - View current cultural & religious requirements");
        System.out.println(" 2 - Add  or update a cultural or religious requirement");
        System.out.println(" 3 - Remove a cultural or religious requirements");
        System.out.println(" 4 - No cultural or religious requirements to manage. Cancel");


        int r_choice = getIntInput("Choice: ", 1, 4);
        if (r_choice == 1) {
            viewCulturalRequirements(dv);
        } else if (r_choice == 2) {
            addCulturalRequirement(dv);
        } else if (r_choice == 3) {
            removeCulturalRequirement(dv);
        } else if (r_choice == 4) {
            System.out.println(" 4 - No cultural or religious requirements to manage. Cancel");

        }


    }

    private void viewCulturalRequirements(DisasterVictim victim) {
        ArrayList<CulturalRequirement> r = victim.getCulturalRequirements();


        if (r.isEmpty()) {
            System.out.println("No cultural requirements set for this disaster victim");
            return;
        }

        System.out.println("\nCurrent cultural requirements");
        int  i = 0;
        while (i < r.size()) {
            CulturalRequirement requirement = r.get(i);
            System.out.println((i+1) + ". " + requirement.getRequirementType() + ":" + requirement.getSelectedOption());
            i++;
        }
    }

    private void addCulturalRequirement(DisasterVictim victim) {
        HashMap<String, Set<String>> accomodations = culturalOptions.getAccommodations();
        if (accomodations.isEmpty()) {
            System.out.println("No cultural requirements types available");
            return;

        }
        ArrayList<String> types = new ArrayList<>(accomodations.keySet());
        System.out.println("Available requirement types: ");
        int i;
        for (i = 0; i < types.size(); i++) {
            System.out.println((i+1) + ". " + types.get(i));

        }
        int type = getIntInput("Choose type: ", 1, types.size()) -1;
        String chosenType = types.get(type);
        ArrayList<String> options = new ArrayList<>(accomodations.get(chosenType));

        System.out.println("Available options for " + chosenType + ": ");
        int x;
        for (x = 0; x < options.size(); x++) {
            System.out.println((x+1) + ". " + options.get(x));

        }

        int option = getIntInput("Choose option: ", 1, types.size()) -1;
        String chosenOption = options.get(option);


        CulturalRequirement cr = new CulturalRequirement(chosenType, chosenOption);

        victim.AddCulturalRequirements(cr);
        DisasterVictimRepo disasterVictimRepo = new DisasterVictimRepo();
        disasterVictimRepo.saveCulturalRequirements(victim);
        
        ActionLogger logger = ActionLogger.getInstance();
        logger.logUpdated("Cultural requirement(s) for disaster victim " + victim.getPersonId() + " | " + chosenType + ": " + chosenOption);


        System.out.println("Cultural requirement added succesfully!");


        

    }


    private void removeCulturalRequirement(DisasterVictim victim) {
        ArrayList<CulturalRequirement> requirements = victim.getCulturalRequirements();
        if (requirements.isEmpty()) {
            System.out.println("No cultural requirements to remove");
            return;

        }
        viewCulturalRequirements(victim);
        int i = getIntInput("Choose cultural requirement to remove: ", 1, requirements.size()) -1;
        String typeToRemove = requirements.get(i).getRequirementType();
        victim.removeCulturalRequirement(typeToRemove);

        DisasterVictimRepo disasterVictimRepo = new DisasterVictimRepo();
        disasterVictimRepo.saveCulturalRequirements(victim);
        ActionLogger logger = ActionLogger.getInstance();
        logger.logUpdated("Removed cultural requirements for disaster victim " + victim.getPersonId() + " | type: " + typeToRemove);
        System.out.println("Requirement removed succesfully!");

    }

    private void manageFamilyRelations() {
        viewVictims();
        if (disasterVictims.isEmpty()) {
            System.out.println("no disaster victims");
            return;
        }
        int i = getIntInput("Choose disaster victim number: ", 1, disasterVictims.size()) -1;
        DisasterVictim victim = disasterVictims.get(i);

        System.out.println("\nManaging family relations for " + victim.getFirstName() + " " + victim.getLastName());

        System.out.println(" 1 - View current family relations");
        System.out.println(" 2 - Add a family relation");
        System.out.println(" 3 - Remove a family relation");
        System.out.println(" 4 - Cancel");

        int r_choice = getIntInput("Choice: ", 1, 4);
        if (r_choice == 1) {
            viewFamilyRelation(victim);
        } else if (r_choice == 2) {
            addFamilyRelation(victim);
        } else if (r_choice == 3) {
            removeFamilyRelation(victim);
        } else if (r_choice == 4) {
            System.out.println("Cancelled");

        }


    }

    private void viewFamilyRelation(DisasterVictim victim) {
        ArrayList<FamilyRelation> relations = familyRelationRepo.getRelations(victim.getPersonId());

        System.out.println("\nFamily relations for: " + victim.getFirstName()+ " " + victim.getLastName());
        if (relations.isEmpty()) {
            System.out.println("No family relations found");
            return;

        }
        int i;
        for (i = 0; i < relations.size(); i++) {
            FamilyRelation relation = relations.get(i);
            String person1Name = relation.getPersonOne().getFirstName() + " " + relation.getPersonOne().getLastName();
            String person2Name = relation.getPersonTwo().getFirstName() + " " + relation.getPersonTwo().getLastName();
            System.out.println((i+1) + ". " + person1Name + " is the " + relation.getRelationshipTo() + " of " + person2Name);
        }


    
    }
    private void addFamilyRelation(DisasterVictim victim) {
        ArrayList<DisasterVictim> otherVictims = new ArrayList<>();
        int i;
        for (i = 0; i < disasterVictims.size(); i++) {
            DisasterVictim o = disasterVictims.get(i);
            if (o.getPersonId() != victim.getPersonId()) {
                otherVictims.add(o);
            }
        }
        if (otherVictims.isEmpty()) {
            System.out.println("No other victims available. Can't create a relationship");
            return;
        }
        System.out.println("\nSelect the related victim: ");
        int x;
        for (x = 0; x < otherVictims.size(); x++) {
            DisasterVictim o = otherVictims.get(x);
            System.out.println((x+1) + "." + o.getFirstName() + " " + o.getLastName());
        }

        int other_i = getIntInput("Choose disaster victim: ", 1, otherVictims.size()) -1;
        DisasterVictim relatedDisasterVictim = otherVictims.get(other_i);

        boolean relationExists = familyRelationRepo.relationExists(victim.getPersonId(), relatedDisasterVictim.getPersonId());
        if (relationExists) {
            System.out.println("There is already an established relation between these two disaster victims");
            return;

        }
        System.out.println("Enter a relationship type (e.g. parent, sibling, spouse, etc): ");
        String rsType = scanner.nextLine().trim();
        if (rsType.isEmpty()) {
            System.out.println("Relationship type can't be empty. Cancelled");
            return;
        }
        familyRelationRepo.addFamilyRelation(victim.getPersonId(), relatedDisasterVictim.getPersonId(), rsType);

        ActionLogger logger = ActionLogger.getInstance();
        logger.logAdded("Family relation |  " + victim.getFirstName() + " " + victim.getLastName() + "is the " + rsType + "of " + relatedDisasterVictim.getFirstName() + " " + relatedDisasterVictim.getLastName());

        System.out.println("Family relation added succesfuly!");
    }

    private void removeFamilyRelation(DisasterVictim victim) {
        ArrayList<FamilyRelation> relations = familyRelationRepo.getRelations(victim.getPersonId());

        if (relations.isEmpty()) {
            System.out.println("No family relations. Nothing to remove");
            return;
        }
        viewFamilyRelation(victim);

        int idx = getIntInput("Choose relation number to remove: ", 1, relations.size()) -1;
        FamilyRelation relationToRemove = relations.get(idx);
        familyRelationRepo.removeFamilyRelation(relationToRemove.getPersonOne().getPersonId(), relationToRemove.getPersonTwo().getPersonId());
        
        ActionLogger logger = ActionLogger.getInstance();
        logger.logAdded("Family relation |  " + relationToRemove.getPersonOne().getFirstName() + " " + relationToRemove.getPersonOne().getLastName() + " and " + relationToRemove.getPersonTwo().getFirstName() + " " + relationToRemove.getPersonTwo().getLastName());


        System.out.println("Family relation removed succesfuly!");

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


    private String getStringInput(String prompt, String[] validStrings) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim().toLowerCase();
            int i;
            for (i = 0; i < validStrings.length; i++) {
                if (input.equals(validStrings[i])) {
                    return input;
                }
            }
            System.out.print("Invalid input. Valid string options: ");
            int x;
            for (x = 0; x < validStrings.length; x++) {
                System.out.print(validStrings[x]);
                if (x < validStrings.length -1 ) {
                    System.out.println(",");
                }
            }
            System.out.println();
        }
    }
    private void manageMedicalRecords() {
        viewVictims();;
        if (disasterVictims.isEmpty()){
            System.out.println("No disaster victims");
            return;
        }
        int idx = getIntInput("Choose disaster victim number: ", 1, disasterVictims.size()) -1;
        DisasterVictim dv = disasterVictims.get(idx);
        System.out.println("\nManaging medical records for: " + dv.getFirstName() + " " + dv.getLastName());
        System.out.println(" 1 - View medical records");
        System.out.println(" 2 - Add a new medical records");
        System.out.println(" 3 - Cancel");

        int m_choice = getIntInput("Choice: ", 1, 3);
        if (m_choice == 1) {
            viewMedicalRecords(dv);
        
        } else if (m_choice == 2) {
            addMedicalRecord(dv);

        } else if (m_choice == 3) {
            System.out.println("Cancelled");
        }

    }

    private void viewMedicalRecords(DisasterVictim victim) {
        MedicalRecordRepo medicalRecordRepo = new MedicalRecordRepo();
        ArrayList<MedicalRecord> records = medicalRecordRepo.getMedicalRecords(victim.getPersonId());

        System.out.println("Medical Records for " + victim.getFirstName() + " " + victim.getLastName() + ": ");
        if (records.isEmpty()) {
            System.out.println("No medical records for this disaster victim found");
            return;
        }
        int j;
        for (j = 0; j < records.size(); j++) {
            MedicalRecord record = records.get(j);
            String locationName;
            if (record.getLocation() == null) {
                locationName = "Unknown";

            } else {
                locationName = record.getLocation().getName();
            }

            System.out.println((j+1) + ".    Date: " + record.getDateOfTreatment());
            System.out.println("      Treatment Details: " + record.getTreatmentDetails());
            System.out.println("      Location: " + locationName);
            System.out.println();

        }

    }
    private void addMedicalRecord(DisasterVictim victim) {

        System.out.println("\nAdd Medical Record");
        System.out.print("Enter Medical Treatment Details: ");
        String treatmentDetails = scanner.nextLine().trim();

        if (treatmentDetails.isEmpty()) {
            System.out.println("treatment details can't be empty");
            return;
        }
        LocalDate treatmentDate = getDateInput("Enter treatment date (YYYY-MM-DD): ");
        LocationRepo locationRepo = new LocationRepo();
        ArrayList<Location> locations = locationRepo.getLocations();
        Location chosenLocation = null;

        if (locations.isEmpty()) {
            System.out.println("No locations found");
        
        } else {
            System.out.println("\nSelect treatment location: ");
            int i;
            for (i = 0; i < locations.size(); i++) {
                System.out.println((i+1) + ". " + locations.get(i).getName());
            }
            System.out.println(locations.size() + 1 +"." + " No location");
            

            int l_choice = getIntInput("Choose location: ", 1, locations.size() + 1);
            if (l_choice < locations.size() || l_choice == locations.size()) {
                chosenLocation = locations.get(l_choice - 1);
            }

        }
        try {
            MedicalRecord newRecord = new MedicalRecord(chosenLocation, treatmentDetails, treatmentDate);
            MedicalRecordRepo medicalRecordRepo = new MedicalRecordRepo();
            medicalRecordRepo.addMedicalRecord(victim.getPersonId(), newRecord);


            ActionLogger logger = ActionLogger.getInstance();
           
            logger.logAdded("Medical record for disaster victim " + victim.getPersonId() + " | Name: " + victim.getFirstName() + " " + victim.getLastName() + " | Treatments: " + treatmentDetails + " | Date: " + treatmentDate);
            System.out.println("Medical record added successfully!");

        } catch (IllegalArgumentException e) {
            System.out.println("Error adding record");

        }

    }




            

}
    


