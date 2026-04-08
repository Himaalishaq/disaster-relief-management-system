package edu.ucalgary.oop;

import java.util.ArrayList;

public class LanguageSkill  extends Skill{
    public static final String READ_WRITE_CAPABILITY = "read/write";
    public static final String SPEAK_LISTEN_CAPABILITY = "speak/listen";

    private ArrayList<String> capabilities;

    public LanguageSkill(String language, String proficiencyLevel, ArrayList<String> capabilities) throws IllegalArgumentException {
        super(language, Skill.CATEGORY_LANGUAGE, proficiencyLevel);

        if ((capabilities == null ) || (capabilities.isEmpty())) {
            throw new IllegalArgumentException("Need to select atleast one lanuage capability");

        }
        int i;
        for (i = 0; i <capabilities.size(); i++) {
            String cpbls = capabilities.get(i);
            if (!isValidCapability(cpbls)) {
                throw new IllegalArgumentException("Invalid capability. Has to be read/write or speak/listen");

            }
        }
    
        this.capabilities = capabilities;
    }

    private boolean isValidCapability(String capability) {
        if (capability == null) {
            return false;
        }
        String lower_c = capability.trim().toLowerCase();
        if (lower_c.equals(READ_WRITE_CAPABILITY) || lower_c.equals(SPEAK_LISTEN_CAPABILITY)) {
            return true;
        } else {
        return false;
        }
    }

    public String getStringCapabilities() {
        String result = "";
        int i;
        for (i = 0; i < capabilities.size(); i++) {
            result += capabilities.get(i);
            if (i < capabilities.size() -1) {
                result += ", ";

            }
        }
        return result;
    }





    public ArrayList<String> getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(ArrayList<String> capabilities) {
        this.capabilities = capabilities;
    }

}




        
    
