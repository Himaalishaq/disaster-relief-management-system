package edu.ucalgary.oop;

import java.time.LocalDate;

public class TradeSkill  extends Skill {
    public static final String[] VALID_TYPES = {"carpentry", "plumbing", "electricity"};


    public TradeSkill(String skillName, String proficiencyLevel) throws IllegalArgumentException {
        super (skillName, Skill.CATEGORY_TRADE, proficiencyLevel);

        if (!isValidTradeType(skillName)) {
            throw new IllegalArgumentException("Invalid trade skill type. Has to be: carpentry, plumbing, or electricity");

        }
    
    }

    private boolean isValidTradeType(String skillName) {
        if (skillName == null) {
            return false;
        }
        String lower_t = skillName.trim().toLowerCase();
        int i;
        for (i = 0; i < VALID_TYPES.length; i++) {
            if (lower_t.equals(VALID_TYPES[i])) {
                return true;
            }
        }
        return false;
    }
    
}
