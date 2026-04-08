package edu.ucalgary.oop;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;


import java.time.LocalDate;


public class DisasterVictimTest {
    private DisasterVictim victim;
    private LocalDate entryDate;

    @Before
    public void setUp() {
    entryDate = LocalDate.of(2026, 4, 7);
    victim = new DisasterVictim("Dila", entryDate);
    }

    @Test
    public void testConstructorSetsFirstName() {
        assertEquals("Dila", victim.getFirstName());
    }

    @Test
    public void testSetsAndGetsLastName() {
        victim.setLastName("Ishaq");
        assertEquals("Ishaq", victim.getLastName());
    }   

    @Test
    public void testConstructorSetsEntryDate() {
        assertEquals(entryDate, victim.getEntryDate());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullEntryDateThrowsException() {
        new DisasterVictim("Dila", null);
    
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFutureDateOfBirthThrowsException() {
        LocalDate futureDate = LocalDate.now().plusDays(1);
        victim.setDateOfBirth(futureDate);
    }


    public void testSetApproximateAge() {
        victim.setApproximateAge(19);
        assertEquals(Integer.valueOf(19), victim.getApproximateAge());
    }


    @Test
    public void testSetValidDateOfBirth() {
        LocalDate dob = LocalDate.of(2005, 7, 15);
        victim.setDateOfBirth(dob);
        assertEquals(dob, victim.getDateOfBirth());
    }

    @Test
    public void testSetGenderWoman() {
        victim.setGender("girl");
        assertEquals("girl", victim.getGender());
    }

    @Test
    public void testInvalidGenderDefaultToPleaseSpecify() {
        victim.setGender("unknown");
        assertEquals("Please specify", victim.getGender());
    }

    @Test
    public void testSetComments() {
        victim.setComments(" this individual needs to adhere to a strict diet plan");
        assertEquals(" this individual needs to adhere to a strict diet plan", victim.getComments());
    }

    @Test
    public void testAddCulturalRequirement() {
        CulturalRequirement req = new CulturalRequirement("dietary restrictions", "halal");
        victim.AddCulturalRequirements(req);
        assertEquals(1, victim.getCulturalRequirements().size());
    }         


    @Test
    public void testDuplicateCulturalRequirementReplaces() {
        CulturalRequirement req1 = new CulturalRequirement("dietary restrictions", "halal");
        CulturalRequirement req2 = new CulturalRequirement("dietary restrictions", "kosher");
        victim.AddCulturalRequirements(req1);
        victim.AddCulturalRequirements(req2);
        assertEquals(1, victim.getCulturalRequirements().size());
        assertEquals("kosher", victim.getCulturalRequirements().get(0).getSelectedOption());
    }

    public void testRemoveNonExistentRequirementThrowsException() {
        victim.removeCulturalRequirement("dietary restrictions");
    }


    public void testRemoveCulturalRequirement() {
        CulturalRequirement req = new CulturalRequirement("dietary restrictions", "halal");
        victim.AddCulturalRequirements(req);
        victim.removeCulturalRequirement("dietary restrictions");
        assertEquals(0, victim.getCulturalRequirements().size());
    }



    @Test
    public void testAddPersonalBelonging() {
        Supply supply = new Supply("toothbrushes", 3);
        victim.addPersonalBelonging(supply);
        assertEquals(3, victim.getPersonalBelongings().length);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNullBelongingThrowsException() {
        victim.addPersonalBelonging(null);
    }

}