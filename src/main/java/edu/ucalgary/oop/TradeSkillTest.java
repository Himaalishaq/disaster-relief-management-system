package edu.ucalgary.oop;


import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;


import java.time.LocalDate;



public class TradeSkillTest {
    private TradeSkill tradeSkill;
    

    

    @Before
    public void setUp() {
        tradeSkill = new TradeSkill("carpentry", "advanced");
    }



    @Test
    public void testConstructorSetsSkillName() {
        assertEquals("carpentry", tradeSkill.getSkillName());
    }

    @Test
    public void testConstructorSetsCategoryToTrade() {
        assertEquals(Skill.CATEGORY_TRADE, tradeSkill.getCategory());
    }

    @Test
    public void testConstructorSetsProficiencyLevel() {
        assertEquals("advanced", tradeSkill.getProficiencyLevel());
    }


    @Test
    public void testMixedCaseTradeTypeIsAccepted() {
        TradeSkill skill = new TradeSkill("Plumbing", "intermediate");
        assertEquals("Plumbing", skill.getSkillName());
        assertEquals("intermediate", skill.getProficiencyLevel());
        assertEquals(Skill.CATEGORY_TRADE, skill.getCategory());
    }



    @Test(expected = IllegalArgumentException.class)
    public void testInvalidTradeTypeThrowsException() {
        new TradeSkill("cleaning", "advanced");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptySkillNameThrowsException() {
        new TradeSkill("   ", "advanced");
    }


    public void testNullSkillNameThrowsException() {
        new TradeSkill(null, "advanced");
    }


    @Test
    public void testSetAndGetSkillId() {
        tradeSkill.setSkillId(4);
        assertEquals(4, tradeSkill.getSkillId());
    }


    @Test
    public void testSkillIdDefaultsToZero() {
        TradeSkill skill = new TradeSkill("plumbing", "intermediate");
        assertEquals(0, skill.getSkillId());
    }


    @Test
    public void testSetAndGetDisasterVictimSkillId() {
        tradeSkill.setDisasterVictimSkillId(20);
        assertEquals(20, tradeSkill.getDisasterVictimSkillId());
    }


    @Test
    public void testSetAndGetSkillName() {
        tradeSkill.setSkillName("carpentry");
        assertEquals("carpentry", tradeSkill.getSkillName());
    }

    @Test
    public void testSetAndGetProficiencyLevel() {
        tradeSkill.setProficiencyLevel("advanced");
        assertEquals("advanced", tradeSkill.getProficiencyLevel());
    }


    @Test(expected = IllegalArgumentException.class)
    public void testConstructorInvalidProficiencyThrowsException() {
        new TradeSkill("carpentry", "novice");
    }


    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNullProficiencyThrowsException() {
        new TradeSkill("carpentry", null);
    }












}
