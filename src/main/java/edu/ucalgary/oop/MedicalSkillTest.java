package edu.ucalgary.oop;


import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;


import java.time.LocalDate;



public class MedicalSkillTest {
    private MedicalSkill medicalSkill;
    private LocalDate expiryDate;
    

    @Before
    public void setUp() {
        expiryDate = LocalDate.of(2028, 12, 31);
        medicalSkill = new MedicalSkill("first-aid","intermediate", "CPR", expiryDate);
    }


    @Test
    public void testConstructorSetsSkillName() {
        assertEquals("first-aid", medicalSkill.getSkillName());
    }

    @Test
    public void testConstructorSetsCategoryToMedical() {
        assertEquals(Skill.CATEGORY_MEDICAL, medicalSkill.getCategory());
    }

    @Test
    public void testConstructorSetsProficiencyLevel() {
        assertEquals("intermediate", medicalSkill.getProficiencyLevel());
    }

    @Test
    public void testConstructorSetsCertificationInfo() {
        assertEquals("CPR", medicalSkill.getCertificationInfo());
    }

    @Test
    public void testConstructorSetsCertificationExpirationDate() {
        assertEquals(expiryDate, medicalSkill.getCertificationExpirationDate());
    }



    @Test
    public void testExpiryDateCanBePastDate() {
        LocalDate pastDate = LocalDate.of(2025, 1, 1);
        MedicalSkill skill = new MedicalSkill("nursing", "advanced", "DO", pastDate);
        assertEquals(pastDate, skill.getCertificationExpirationDate());
    }





    @Test(expected = IllegalArgumentException.class)
    public void testInvalidMedicalTypeThrowsException() {
        new MedicalSkill("dentist", "beginner", "diploma", expiryDate);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullSkillNameThrowsException() {
        new MedicalSkill(null, "advanced", "CPR", expiryDate);
    }



    @Test
    public void testMixedCaseMedicalTypeIsAccepted() {
        MedicalSkill skill = new MedicalSkill("Doctor", "beginner", "diploma", expiryDate);
        assertEquals("Doctor", skill.getSkillName());
        assertEquals("beginner", skill.getProficiencyLevel());
        assertEquals(Skill.CATEGORY_MEDICAL, skill.getCategory());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptySkillNameThrowsException() {
        new MedicalSkill("   ", "intermediate", "CPR", expiryDate);
    }

    @Test
    public void testAndGetCertificationInfo() {
        medicalSkill.setCertificationInfo("First Aid");
        assertEquals("First Aid", medicalSkill.getCertificationInfo());
    }

    @Test
    public void testSetCertificationInfoAllowsNull() {
        medicalSkill.setCertificationInfo(null);
        assertNull(medicalSkill.getCertificationInfo());
    }


    @Test
    public void testSetAndGetCertificationExpirationDate() {
        LocalDate newDate = LocalDate.of(2028, 12, 1);
        medicalSkill.setCertificationExpirationDate(newDate);
        assertEquals(newDate, medicalSkill.getCertificationExpirationDate());
    }


    @Test
    public void testSetCertificationExpirationDateAllowsNull() {
        medicalSkill.setCertificationExpirationDate(null);
        assertNull(medicalSkill.getCertificationExpirationDate());
    }





    @Test
    public void testSetAndGetSkillId() {
        medicalSkill.setSkillId(13);
        assertEquals(13, medicalSkill.getSkillId());
    }
    @Test
    public void testSetAndGetSkillName() {
        medicalSkill.setSkillName("doctor");
        assertEquals("doctor", medicalSkill.getSkillName());
    }

    
    @Test
    public void testSetAndGetProficiencyLevel() {
        medicalSkill.setProficiencyLevel("intermediate");
        assertEquals("intermediate", medicalSkill.getProficiencyLevel());
    }


    @Test
    public void testSetAndGetDisasterVictimSkillId() {
        medicalSkill.setDisasterVictimSkillId(2);
        assertEquals(2, medicalSkill.getDisasterVictimSkillId());
    }

    @Test(expected = NullPointerException.class)
    public void testNullProficiencyThrowsNullPointerException() {
        new MedicalSkill("doctor", null, "Masters", expiryDate);
    }
}