package edu.ucalgary.oop;


import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;

import java.time.LocalDate;



public class LanguageSkillTest {
    private LanguageSkill languageSkill;
    private ArrayList<String> capabilities;



    @Before
    public void setUp() {
        capabilities = new ArrayList<>();
        capabilities.add(LanguageSkill.READ_WRITE_CAPABILITY);
        capabilities.add(LanguageSkill.SPEAK_LISTEN_CAPABILITY);

        languageSkill = new LanguageSkill("Arabic", "intermediate", capabilities);
    }


    @Test
    public void testConstructorSetsSkillName() {
        assertEquals("Arabic", languageSkill.getSkillName());
    }
    @Test
    public void testConstructorSetsProficiencyLevel() {
        assertEquals("intermediate", languageSkill.getProficiencyLevel());
    }



    @Test
    public void testConstructorSetsCategoryToLanguage() {
        assertEquals(Skill.CATEGORY_LANGUAGE, languageSkill.getCategory());
    }

    public void testConstructorSetsCapabilities() {
        assertEquals(2, languageSkill.getCapabilities().size());
        assertEquals(LanguageSkill.READ_WRITE_CAPABILITY, languageSkill.getCapabilities().get(0));
        assertEquals(LanguageSkill.SPEAK_LISTEN_CAPABILITY, languageSkill.getCapabilities().get(1));
    }

    @Test
    public void testGetStringCapabilitiesWithTwoCapabilities() {
        assertEquals("read/write, speak/listen", languageSkill.getStringCapabilities());
    }



    @Test
    public void testGetStringCapabilitiesWithOneCapability() {
        ArrayList<String> oneCapability = new ArrayList<>();
        oneCapability.add(LanguageSkill.READ_WRITE_CAPABILITY);

        LanguageSkill skill = new LanguageSkill("English", "advanced", oneCapability);
        assertEquals("read/write", skill.getStringCapabilities());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNullCapabilitiesThrowsException() {
        new LanguageSkill("French", "beginner", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorEmptyCapabilitiesThrowsException() {
        new LanguageSkill("French", "beginner", new ArrayList<String>());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNullCapabilityInsideListThrowsException() {
        ArrayList<String> caps = new ArrayList<>();
        caps.add(null);
        new LanguageSkill("Greek", "intemediate", caps);
    }



    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNullLanguageThrowsException() {
        new LanguageSkill(null, "intermediate", capabilities);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testConstructorBlankLanguageThrowsException() {
        new LanguageSkill("   ", "intermediate", capabilities);
    }



    @Test(expected = IllegalArgumentException.class)
    public void testConstructorInvalidProficiencyThrowsException() {
        new LanguageSkill("Urdu", "novice", capabilities);
    }





    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNullProficiencyThrowsException() {
        new LanguageSkill("Urdu", null, capabilities);
    }

    @Test
    public void testSetAndGetCapabilities() {
        ArrayList<String> newCaps = new ArrayList<>();
        newCaps.add(LanguageSkill.SPEAK_LISTEN_CAPABILITY);
        languageSkill.setCapabilities(newCaps);
        assertEquals(1, languageSkill.getCapabilities().size());
        assertEquals(LanguageSkill.SPEAK_LISTEN_CAPABILITY, languageSkill.getCapabilities().get(0));
    }

    @Test
    public void testSetAndGetSkillId() {
        languageSkill.setSkillId(1);
        assertEquals(1, languageSkill.getSkillId());
    }

    @Test
    public void testSetAndGetDisasterVictimSkillId() {
        languageSkill.setDisasterVictimSkillId(2);
        assertEquals(2, languageSkill.getDisasterVictimSkillId());
    } 
    

    @Test
    public void testSetAndGetSkillName() {
        languageSkill.setSkillName("French");
        assertEquals("French", languageSkill.getSkillName());
    }
    @Test(expected = IllegalArgumentException.class)
    public void testSetSkillNameNullThrowsException() {
        languageSkill.setSkillName(null);
    }

    @Test
    public void testSetAndGetProficiencyLevel() {
        languageSkill.setProficiencyLevel("advanced");
        assertEquals("advanced", languageSkill.getProficiencyLevel());
    }
    @Test(expected = IllegalArgumentException.class)
    public void testSetProficiencyLevelNullThrowsException() {
        languageSkill.setProficiencyLevel(null);
    }

}
