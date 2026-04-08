/*
Copyright Ann Barcomb and Khawla Shnaikat, 2024-2025
Licensed under GPL v3
See LICENSE.txt for more information.
*/

package edu.ucalgary.oop;
import java.util.ArrayList;
import java.time.LocalDate;

public class DisasterVictim {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth; 
    private FamilyRelation[] familyConnections; 
    private MedicalRecord[] medicalRecords; 
    private Supply[] personalBelongings;
    private final LocalDate ENTRY_DATE; 
    private String gender;
    private String comments;
    private Integer approximateAge;
    private int personId;

    private ArrayList<CulturalRequirement> culturalRequirements = new ArrayList<>();

    public ArrayList<CulturalRequirement> getCulturalRequirements() {
        return culturalRequirements;
    }

    public void setCulturalRequirements(ArrayList<CulturalRequirement> requirements) {
        this.culturalRequirements = requirements;
    }

    public void AddCulturalRequirements(CulturalRequirement requirement) {
        if (requirement == null) {
            throw new IllegalArgumentException("The requirement can't be null");
        }
        int  i;
        for (i = 0; i < culturalRequirements.size(); i++) {
            CulturalRequirement existingRequirement = culturalRequirements.get(i);
            if (existingRequirement.getRequirementType().equals(requirement.getRequirementType())) {
                culturalRequirements.set(i, requirement);
                return;
            }
        }
        culturalRequirements.add(requirement);
    }

    public void removeCulturalRequirement(String requirementType) {
        CulturalRequirement toRemove = null;
        for (int i = 0; i < culturalRequirements.size(); i++ ) {
            CulturalRequirement cultural_req = culturalRequirements.get(i);
            if (cultural_req.getRequirementType().equals(requirementType)) {
                toRemove = cultural_req;
                break;
            }
        }
    
        if (toRemove == null) {
            throw new IllegalArgumentException("Requirement type not found: " + requirementType);

        }
        culturalRequirements.remove(toRemove);
    }








    public Integer getApproximateAge() {
        return approximateAge;
    }
    public void setApproximateAge(Integer approximateAge) {
        this.approximateAge = approximateAge;    
    }


    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }





    public DisasterVictim(String firstName, LocalDate ENTRY_DATE) throws IllegalArgumentException {
        if (ENTRY_DATE == null) {
            throw new IllegalArgumentException("Entry date cannot be null");
        }
        this.firstName = firstName;
        this.ENTRY_DATE = ENTRY_DATE;
        this.familyConnections = new FamilyRelation[0];
        this.medicalRecords = new MedicalRecord[0];
        this.personalBelongings = new Supply[0];
    }

    public DisasterVictim(String firstName, LocalDate ENTRY_DATE, LocalDate dateOfBirth) throws IllegalArgumentException {
        this(firstName, ENTRY_DATE);
        setDateOfBirth(dateOfBirth);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) throws IllegalArgumentException {
        if (dateOfBirth == null) {
            throw new IllegalArgumentException("Date of birth cannot be null");
        }

        // Check if the date is in the future
        if (dateOfBirth.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Date of birth cannot be in the future");
        }

        this.dateOfBirth = dateOfBirth;
    }

    public FamilyRelation[] getFamilyConnections() {
        return familyConnections;
    }

    public MedicalRecord[] getMedicalRecords() {
        return medicalRecords;
    }

    public Supply[] getPersonalBelongings() {
        return this.personalBelongings;
    }

    public void setFamilyConnections(FamilyRelation[] connections) {
        // Using clone() for defensive copying: creates a new array with the same elements
        // This prevents external code from modifying the internal array structure
        // Note: This is a SHALLOW copy - the FamilyRelation objects themselves are shared
        this.familyConnections = connections != null ? connections.clone() : new FamilyRelation[0];
    }

    public void setMedicalRecords(MedicalRecord[] records) {
        // Using clone() for defensive copying: creates a new array with the same elements
        // This prevents external code from modifying the internal array structure
        // Note: This is a SHALLOW copy - the MedicalRecord objects themselves are shared
        this.medicalRecords = records != null ? records.clone() : new MedicalRecord[0];
    }

    public void setPersonalBelongings(Supply[] belongings) {
        // Using clone() for defensive copying: creates a new array with the same elements
        // This prevents external code from modifying the internal array structure
        // Note: This is a SHALLOW copy - the Supply objects themselves are shared
        this.personalBelongings = belongings != null ? belongings.clone() : new Supply[0];
    }

    public void addPersonalBelonging(Supply supply) {
        if (supply == null) {
            throw new IllegalArgumentException("Supply cannot be null");
        }

        if (this.personalBelongings == null) {
            Supply tmpSupply[] = { supply };
            this.setPersonalBelongings(tmpSupply);
            return;
        }

        // Create an array one larger than the previous array
        int newLength = this.personalBelongings.length + 1;
        Supply tmpPersonalBelongings[] = new Supply[newLength];

        // Copy all the items in the current array to the new array
        int i;
        for (i=0; i < personalBelongings.length; i++) {
            tmpPersonalBelongings[i] = this.personalBelongings[i];
        }

        // Add the new element at the end of the new array
        tmpPersonalBelongings[i] = supply;

        // Replace the original array with the new array
        this.personalBelongings = tmpPersonalBelongings;
    }

    public void removePersonalBelonging(Supply unwantedSupply) throws IllegalArgumentException {
        if (unwantedSupply == null) {
            throw new IllegalArgumentException("Supply to remove cannot be null");
        }
        
        // Find the supply - must use equals() for proper comparison
        int index = -1;
        for (int i = 0; i < personalBelongings.length; i++) {
            if (personalBelongings[i].equals(unwantedSupply)) {
                index = i;
                break;
            }
        }
        
        // If not found, throw exception
        if (index == -1) {
            throw new IllegalArgumentException("Supply not found in personal belongings");
        }
        
        // When a personal belonging is removed, it is destroyed (not returned to supply). 
        // We create a new array without the item.
        Supply[] updatedBelongings = new Supply[personalBelongings.length - 1];
        int newIndex = 0;
        for (int i = 0; i < personalBelongings.length; i++) {
            if (i != index) {
                updatedBelongings[newIndex] = personalBelongings[i];
                newIndex++;
            }
        }
        
        this.personalBelongings = updatedBelongings;
    }

    public void removeFamilyConnection(FamilyRelation exRelation) throws IllegalArgumentException {
        if (exRelation == null) {
            throw new IllegalArgumentException("Family relation to remove cannot be null");
        }
        
        int index = -1;
        for (int i = 0; i < familyConnections.length; i++) {
            if (familyConnections[i].equals(exRelation)) {
                index = i;
                break;
            }
        }
        
        if (index == -1) {
            throw new IllegalArgumentException("Family relation not found");
        }
        
        FamilyRelation[] updatedConnections = new FamilyRelation[familyConnections.length - 1];
        int newIndex = 0;
        for (int i = 0; i < familyConnections.length; i++) {
            if (i != index) {
                updatedConnections[newIndex] = familyConnections[i];
                newIndex++;
            }
        }
        
        this.familyConnections = updatedConnections;
    }

    public void addFamilyConnection(FamilyRelation record) {
        if (record == null) {
            throw new IllegalArgumentException("Family relation cannot be null");
        }
        
        FamilyRelation[] newConnections = new FamilyRelation[familyConnections.length + 1];
        System.arraycopy(familyConnections, 0, newConnections, 0, familyConnections.length);
        newConnections[familyConnections.length] = record;
        this.familyConnections = newConnections;
    }

    public void addMedicalRecord(MedicalRecord record) {
        if (record == null) {
            throw new IllegalArgumentException("Medical record cannot be null");
        }
        
        MedicalRecord[] newRecords = new MedicalRecord[medicalRecords.length + 1];
        System.arraycopy(medicalRecords, 0, newRecords, 0, medicalRecords.length);
        newRecords[medicalRecords.length] = record;
        this.medicalRecords = newRecords;
    }

    public LocalDate getEntryDate() {
        return ENTRY_DATE;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) throws IllegalArgumentException {
        if (gender == null || gender.trim().isEmpty()) {
            this.gender = "Please specify";
            return;
        }
        String value = gender.trim().toLowerCase();



     
        if (value.equalsIgnoreCase("man") || value.equalsIgnoreCase("male")) {
            this.gender = "Man";
        
        } else if (value.equalsIgnoreCase("woman") || value.equalsIgnoreCase("female")) {
            this.gender = "Woman";
        
        } else if (value.equalsIgnoreCase("boy")) {
            this.gender = "Boy";
    
        } else if (value.equalsIgnoreCase("girl")) {
            this.gender = "Girl";
        }else {
            this.gender = value;
        }

        
    }
}

