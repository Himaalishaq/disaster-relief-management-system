package edu.ucalgary.oop;


import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;


import java.time.LocalDate;


public class SkillTest {
    private Skill skill;

    

    @Before
    public void setUp() {
        skill = new Skill("first-aid", Skill.CATEGORY_MEDICAL, "intermediate");
    }



    @Test
    public void testConstructorSetsSkillName() {
        assertEquals("first-aid", skill.getSkillName());
    }

    @Test
    public void testConstructorSetsCategory() {
        assertEquals("medical", skill.getCategory());
    }


    @Test
    public void testConstructorSetsProficiencyLevel() {
        assertEquals("intermediate", skill.getProficiencyLevel());
    }

    @Test
    public void testConstructorLowercasesCategory() {
        Skill s = new Skill("first-aid", "MEDICAL", "intermediate");
        assertEquals("medical", s.getCategory());
    }


    @Test
    public void testConstructorLowercasesProficiencyLevel() {
        Skill s = new Skill("first-aid", Skill.CATEGORY_MEDICAL, "INTERMEDIATE");
        assertEquals("intermediate", s.getProficiencyLevel());
    }

    @Test
    public void testConstructorAllowsLanguageCategory() {
        Skill s = new Skill("arabic", Skill.CATEGORY_LANGUAGE, "beginner");
        assertEquals("language", s.getCategory());
    }


    public void testConstructorAllowsTradeCategory() {
        Skill s = new Skill("plumbing", Skill.CATEGORY_TRADE, "beginner");
        assertEquals("trade", s.getCategory());
    }



     @Test(expected = IllegalArgumentException.class)
    public void testConstructorBlankSkillNameThrowsException() {
        new Skill("   ", Skill.CATEGORY_MEDICAL, "advanced");
    }   

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNullSkillNameThrowsException() {
        new Skill(null, Skill.CATEGORY_MEDICAL, "advanced");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNullCategoryThrowsException() {
        new Skill("first-aid", null, "beginner");
    }





    @Test
    public void testSetAndGetSkillId() {
        skill.setSkillId(12);
        assertEquals(12, skill.getSkillId());
    }

    @Test
    public void testSetAndGetDisasterVictimSkillId() {
        skill.setDisasterVictimSkillId(13);
        assertEquals(13, skill.getDisasterVictimSkillId());
    }


    @Test
    public void testSetAndGetSkillName() {
        skill.setSkillName("nurse");
        assertEquals("nurse", skill.getSkillName());
    }


    @Test
    public void testSetAndGetProficiencyLevel() {
        skill.setProficiencyLevel("beginner");
        assertEquals("beginner", skill.getProficiencyLevel());
    }
    @Test(expected = IllegalArgumentException.class)
    public void testSetProficiencyLevelInvalidValueThrowsException() {
        skill.setProficiencyLevel("expert");
    }

  
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNullProficiencyThrowsException() {
        new Skill("first-aid", Skill.CATEGORY_MEDICAL, null);
    }



    @Test(expected = IllegalArgumentException.class)
    public void testSetProficiencyLevelNullThrowsException() {
        skill.setProficiencyLevel(null);
    }









}
