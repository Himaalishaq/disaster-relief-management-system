package edu.ucalgary.oop;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import java.util.Scanner;

import javax.swing.Action;

import java.util.ArrayList;




public class SkillCLI {
    private Scanner scanner;
    private SkillRepo skillRepo;
    private DisasterVictimRepo disasterVictimRepo;

    public SkillCLI(Scanner scanner) {
        this.skillRepo = new SkillRepo();
        this.disasterVictimRepo = new   DisasterVictimRepo();
        this.scanner = scanner;
    }

    public void display() {
        boolean prog_running = true;

        while (prog_running) {
            System.out.println("\n---Skill & Volunteer Registry---");
            System.out.println("1 - View skills for a disaster victim");
            System.out.println("2 - Add a skill to a disaster victim");
            System.out.println("3 - Remove a skill from a disaster victim");
            System.out.println("4 - Search a disaster victim by skill category");
            System.out.println("5- Return to main menu");
            System.out.println();


            int choice = getIntInput("Enter Choice (1 to 5): ", 1, 5);

            if (choice == 1) {
                viewSkillsForDisasterVictim();
            
            } else if (choice == 2) {
                addSkillToDisasterVictim();
            } else if (choice == 3) {
                removeSkillFromDisasterVictim();
            } else if (choice == 4) {
                SearchByCategory();
            } else if (choice ==5 ) {
                prog_running = false;
            }

        }
    
    }
    private void viewSkillsForDisasterVictim() {
        DisasterVictim disasterVictim = chooseVictim();
        if (disasterVictim == null) {
            return;
        }
        ArrayList<Skill> skills = skillRepo.getSkillsForDisasterVictims(disasterVictim.getPersonId());
        System.out.println(" Skills for " + disasterVictim.getFirstName() + " " + disasterVictim.getLastName() + ": ");
        System.out.println();
        if (skills.isEmpty()) {
            System.out.println("No skills registered for this victim");
            return;
        }
        int i;
        for (i = 0;  i < skills.size(); i++) {
            Skill skill = skills.get(i);
            
            System.out.println((i + 1) + "." + skill.getSkillName());
            System.out.println("  Category: " + skill.getCategory());
            System.out.println("  Proficiency: " + skill.getProficiencyLevel());
            
            if (skill instanceof MedicalSkill) {
                MedicalSkill medSkill = (MedicalSkill) skill;
                if (medSkill.getCertificationInfo() != null) {
                    System.out.println("  Certification: " + medSkill.getCertificationInfo());
                }
                if (medSkill.getCertificationExpirationDate() != null) {
                    System.out.println("  Expiry Date: " + medSkill.getCertificationExpirationDate());
                }
            } else if (skill instanceof LanguageSkill) {
                LanguageSkill langSkill = (LanguageSkill) skill;
                System.out.println("  Capabilities: " + langSkill.getStringCapabilities());
            }
            System.out.println();
        }
    }

    private void addSkillToDisasterVictim() {
        DisasterVictim disasterVictim = chooseVictim();
        if (disasterVictim == null) {
            return;

        }
        System.out.println("\nSelect skill category: ");
        System.out.println(" 1 - Medical Skills ");
        System.out.println(" 2 - Language Skills ");
        System.out.println(" 3 - Trade Skills");

        int choice = getIntInput("Enter choice (1 to 3): ", 1, 3);

        Skill newSkill = null;

        if (choice ==1) {
            newSkill = buildMedicalSkills(disasterVictim);
        } else if (choice == 2) {
            newSkill = buildLanguageSkills(disasterVictim);

        } else if (choice == 3) {
            newSkill = buildTradeSkills(disasterVictim);
        }


        if (newSkill == null) {
            System.out.println("Cancelled. No new skill to create");
            return;
        }

        ArrayList<Skill> existingSkills = skillRepo.getSkillsForDisasterVictims(disasterVictim.getPersonId());
        int x;
        for (x = 0; x < existingSkills.size(); x++) {
            Skill existing = existingSkills.get(x);
            if (existing.getSkillName().equalsIgnoreCase(newSkill.getSkillName())) {
                System.out.println("This disaster victim already has skill : " + newSkill.getSkillName() + " registered.");
                return;
            }
        }
        skillRepo.addSkillToDisasterVictim(disasterVictim.getPersonId(), newSkill);

        ActionLogger logger = ActionLogger.getInstance();
        logger.logUpdated("Added skill for disaster victim:  " + disasterVictim.getFirstName() + " " + disasterVictim.getLastName() + " ( " + disasterVictim.getPersonId() + ")"  + " | Skill: " + newSkill.getSkillName() + " | Category: " + newSkill.getCategory() + " | Proficiency: " + newSkill.getProficiencyLevel());


        System.out.println("Skill: " + newSkill.getSkillName() + " added successfully!");

    }

    private MedicalSkill buildMedicalSkills(DisasterVictim disasterVictim) {
        System.out.println("\n Select medical skill type: ");
        String[] m_types = MedicalSkill.VALID_TYPES;
        int i;
        for (i = 0; i < m_types.length; i++) {
            System.out.println((i+1) + ". " + m_types[i]);

        }
        int typeChoice = getIntInput("Enter type choice: ", 1, m_types.length) - 1;
        String skillName = m_types[typeChoice];

        String proficiency = chooseProficiency();

        System.out.println("Certification Information (press Enter to skip);");
        String certificationInfo = scanner.nextLine().trim();
        if (certificationInfo.isEmpty()) {
            certificationInfo = null;
        }
        System.out.println("Does this certification have an expiration date (Yes or No)?");
        System.out.println(" 1 - Yes");
        System.out.println(" 2 - No");

        int expiryChoice = getIntInput("Enter choice: ", 1, 2);
        LocalDate expiryDate = null;
        if (expiryChoice == 1) {
            expiryDate = getDateInput("Please enter expiration date (YYYY-MM-DD): ");
        }
        try {
            return new MedicalSkill(skillName, proficiency, certificationInfo, expiryDate);


        } catch (IllegalArgumentException e) {
            System.out.println("Error creating skill");
            return null;
        }


    }
    private LanguageSkill buildLanguageSkills(DisasterVictim disasterVictim) {
        System.out.println("\nEnter language name: ");
        String lang = scanner.nextLine().trim();
        if (lang.isEmpty()) {
            System.out.println("Language name cannot be blank.");
            return null;
        }
        String proficiency = chooseProficiency();
        System.out.println("\nSelect Language Capabilities: ");
        System.out.println(" 1 - Read/Write only");
        System.out.println(" 2 - Speak/Listen only");
        System.out.println(" 3 - Read/Write and Speak/Listen");

        int capabilitiesChoice = getIntInput("Enter choice (1 to 3): ", 1, 3);
        ArrayList<String> capabilities = new ArrayList<>();

        if (capabilitiesChoice == 1) {
            capabilities.add(LanguageSkill.READ_WRITE_CAPABILITY);
        }
        else if (capabilitiesChoice == 2) {
            capabilities.add(LanguageSkill.SPEAK_LISTEN_CAPABILITY);
        
        } else if (capabilitiesChoice == 3) {
            capabilities.add(LanguageSkill.READ_WRITE_CAPABILITY);
            capabilities.add(LanguageSkill.SPEAK_LISTEN_CAPABILITY);

        }
        try {
            return new LanguageSkill(lang, proficiency, capabilities);

        } catch (IllegalArgumentException e) {
            System.out.println("Error creating skill");
            return null;
        }

    }
    private TradeSkill buildTradeSkills(DisasterVictim disasterVictim) {
        System.out.println("\n Select trade skill type: ");
        String[] t_types = TradeSkill.VALID_TYPES;
        int i;
        for (i = 0; i < t_types.length; i++) {
            System.out.println((i+1) + ". " + t_types[i]);

        }
        int typeChoice = getIntInput("Enter type choice: ", 1, t_types.length) - 1;
        String skillName = t_types[typeChoice];

        String proficiency = chooseProficiency();


        try {
            return new TradeSkill(skillName, proficiency);


        } catch (IllegalArgumentException e) {
            System.out.println("Error creating skill");
            return null;
        }


    }
    private void removeSkillFromDisasterVictim() {
        DisasterVictim disasterVictim = chooseVictim();
        if (disasterVictim == null) {
            return;
        }

        ArrayList<Skill> skills = skillRepo.getSkillsForDisasterVictims(disasterVictim.getPersonId());
        if (skills.isEmpty()) {
            System.out.println("This victim has no skills. Nothing to remove.");
            return;
        }
        System.out.println("\nSkills for " + disasterVictim.getFirstName() + " " + disasterVictim.getLastName() + " are: ");
        int k;
        for (k = 0; k <skills.size(); k++) {
            Skill skill = skills.get(k);
            System.out.println((k + 1) + ". " + skill.getSkillName());
            System.out.println("Category: " + skill.getCategory());
            System.out.println("Proficiency Level: " + skill.getProficiencyLevel());
            System.out.println();
            System.out.println();

        }
        int skillChoice = getIntInput("Select skill to remove: ", 1, skills.size()) -1;
        Skill skillToRemove = skills.get(skillChoice);


        skillRepo.removeSkillFromDisasterVictim(skillToRemove.getDisasterVictimSkillId());
        ActionLogger logger = ActionLogger.getInstance();
        logger.logUpdated("Removed skill for disaster victim:  " + disasterVictim.getFirstName() + " " + disasterVictim.getLastName() + " ( " + disasterVictim.getPersonId() + ")"  + " | Skill: " + skillToRemove.getSkillName());

        System.out.println("Skill  removed successfully!");

        
    }
    private void SearchByCategory() {
       
        System.out.println("\nSelect category to search: ");
        System.out.println(" 1 - Medical");
        System.out.println(" 2 - Language");
        System.out.println(" 3 - Trade");

        int choice = getIntInput("Enter choice: ", 1, 3);
        String cat = null;
        if (choice ==1) {
            cat = Skill.CATEGORY_MEDICAL;
        } else if (choice == 2) {
            cat = Skill.CATEGORY_LANGUAGE;

        } else if (choice == 3) {
            cat = Skill.CATEGORY_TRADE;
        }
        ArrayList<DisasterVictim> disasterVictims = skillRepo.getDisasterVictimsByCategory(cat);
        System.out.println("\n Disaster victims with " + cat + "skills");

        if (disasterVictims.isEmpty()) {
            System.out.println("No disaster victims found with " + cat + "skills.");
            return;
        }
        int i;
        int x;
        for (i = 0; i < disasterVictims.size(); i++) {
            DisasterVictim disasterVictim = disasterVictims.get(i);

            System.out.println((i+1) + ". " + disasterVictim.getFirstName() + " " + disasterVictim.getLastName());
            ArrayList<Skill> skills = skillRepo.getSkillsForDisasterVictims(disasterVictim.getPersonId());
            for (x = 0; x < skills.size(); x++) {
                Skill skill = skills.get(x);
                if (skill.getCategory().equals(cat)) {
                    System.out.println("    " + skill.getSkillName() + "(Proficiency Level: " + skill.getProficiencyLevel() + ")");

                }
            }
            System.out.println();
        }
        
    }
    private DisasterVictim chooseVictim() {
        int i;
        ArrayList<DisasterVictim> disasterVictims = disasterVictimRepo.getDisasterVictims();
        if (disasterVictims.isEmpty()) {
            System.out.println("No disaster victims found");
            return null;

        }
        System.out.println("\nSelect a disaster victim:");
        System.out.println();

        for (i = 0; i < disasterVictims.size(); i++) {
            DisasterVictim disasterVictim = disasterVictims.get(i);
            System.out.println((i+1) + ". " + disasterVictim.getFirstName()+ " " + disasterVictim.getLastName());

        }
        int v_choice = getIntInput("Enter number for disaster victim: ", 1, disasterVictims.size()) -1;
        return disasterVictims.get(v_choice);


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

    private String chooseProficiency() {
        System.out.println("\nSelect proficiency level:");
        String[] lvls = Skill.PROFICIENCY_LVLS;
        int x;
        for (x = 0; x<lvls.length; x++){
            System.out.println((x+1) + ". " + lvls[x]);
        }
        int choice = getIntInput("Enter choice: ", 1, lvls.length) -1;
        return lvls[choice];

    }
    


}
