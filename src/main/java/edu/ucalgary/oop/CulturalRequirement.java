package edu.ucalgary.oop;

public class CulturalRequirement {

    private String requirementType;

    private String selectedOption;

    public CulturalRequirement(String requirementType, String selectedOption) {
        this.requirementType = requirementType;
        this.selectedOption = selectedOption;
    }

    public String getRequirementType() {
        return requirementType;
    }

    public void setRequirementType(String requiremenentType) {
        this.requirementType = requiremenentType;
    }
    
    public String getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(String selectedOption) {
        this.selectedOption = selectedOption;
    }

}
