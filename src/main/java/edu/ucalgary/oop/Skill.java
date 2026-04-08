package edu.ucalgary.oop;

public class Skill {
    public static final String CATEGORY_MEDICAL = "medical";
    public static final String CATEGORY_TRADE = "trade";
    public static final String CATEGORY_LANGUAGE = "language";

    public static final String[] PROFICIENCY_LVLS = {"beginner", "intermediate", "advanced"};
    private int skillId;
    private int disasterVictimSkillId;
    private String category;
    private String proficiencyLevel;
    private String skillName;

    public Skill(String skillName, String category, String proficiencyLevel) throws IllegalArgumentException {
        if ((skillName == null) || (skillName.trim().isEmpty())) {
            throw new IllegalArgumentException ("Skill name can't be empty");
        
        }
        if (!isValidCategory(category)) {
            throw new IllegalArgumentException( "Invalid proficiency. Has to be: beginner, intermediate, or advanced");

        }
        this.skillName = skillName.trim();
        this.category = category.trim().toLowerCase();
        this.proficiencyLevel = proficiencyLevel.trim().toLowerCase();
    }
    private boolean isValidCategory(String category) {
        if (category == null) {
            return false;
        }
        String lower = category.trim().toLowerCase();
        if (lower.equals(CATEGORY_MEDICAL)) {
            return true;
        }
        if (lower.equals(CATEGORY_LANGUAGE)) {
            return true;
        }
        if (lower.equals(CATEGORY_TRADE)) {
            return true;

        }
        return false;
    }
    private boolean isValidProficiency(String proficiency) {
        if (proficiency == null) {
            return false;
        }
        String lower_p = proficiency.trim().toLowerCase();

        int i;
        for (i = 0; i < PROFICIENCY_LVLS.length; i++) {
            if (lower_p.equals(PROFICIENCY_LVLS[i])) {
                return true;
            }
        }
        return false;
    }

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    public int getDisasterVictimSkillId() {
        return disasterVictimSkillId;
    }

    public void setDisasterVictimSkillId(int disasterVictimSkill) {
        this.disasterVictimSkillId = disasterVictimSkill;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }





    public String getProficiencyLevel() {
        return proficiencyLevel;
    }

    public String getCategory() {
        return category;
    }

    public void setProficiencyLevel(String proficiencyLevel) {
        this.proficiencyLevel = proficiencyLevel;
    }







    
}
