package edu.ucalgary.oop;


import java.time.LocalDate;



public class MedicalSkill extends Skill {
    public static final String[] VALID_TYPES = {"first-aid", "counseling", "nursing", "doctor"};

    private String certificationInfo;
    private LocalDate certificationExpirationDate;

    public MedicalSkill(String skillName, String proficiencyLevel, String certificationInfo, LocalDate certificationExpirationDate) throws IllegalArgumentException {
        super (skillName, Skill.CATEGORY_MEDICAL, proficiencyLevel);

        if (!isValidMedicalType(skillName)) {
            throw new IllegalArgumentException("Invalid medical skill type. Has to be: first-aid, counseling, nursing, or doctor");

        }
        this.certificationInfo = certificationInfo;
        this.certificationExpirationDate = certificationExpirationDate;
    }

    private boolean isValidMedicalType(String skillName) {
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

    public String getCertificationInfo(){
        return certificationInfo;
    }

    public void setCertificationInfo(String certificationInfo) {
        this.certificationInfo = certificationInfo;
    }

    public LocalDate getCertificationExpirationDate(){
        return certificationExpirationDate;
    }   

    public void setCertificationExpirationDate(LocalDate certificationExpirationDate) {
        this.certificationExpirationDate = certificationExpirationDate;
    }





        
}
