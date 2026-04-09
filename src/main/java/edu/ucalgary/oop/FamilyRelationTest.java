package edu.ucalgary.oop;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;


import java.time.LocalDate;



public class FamilyRelationTest {
    private DisasterVictim personOne;
    private DisasterVictim personTwo;
    private FamilyRelation relation;

    @Before
    public void setUp() {
        personOne = new DisasterVictim("Himaal", LocalDate.of(2026, 4, 7));
        personTwo = new DisasterVictim("Ali", LocalDate.of(2026, 4, 7));
        relation = new FamilyRelation(personOne, "sibling", personTwo);
    }



    @Test
    public void testConstructorSetsRelationshipTo() {
        assertEquals("sibling", relation.getRelationshipTo());
    }

    @Test
    public void testConstructorSetsPersonOne() {
        assertSame(personOne, relation.getPersonOne());
    }

    @Test
    public void testConstructorSetsPersonTwo() {
        assertSame(personTwo, relation.getPersonTwo());
    }



    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNullPersonTwoThrowsException() {
        new FamilyRelation(personOne, "sibling", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNullPersonOneThrowsException() {
        new FamilyRelation(null, "sibling", personTwo);

    }

    @Test
    public void testSetPersonOne() {
        DisasterVictim newPersonOne = new DisasterVictim("Dila", LocalDate.of(2026, 4, 8));
        relation.setPersonOne(newPersonOne);
        assertSame(newPersonOne, relation.getPersonOne());
    }

    @Test
    public void testSetPersonTwo() {
        DisasterVictim newPersonTwo = new DisasterVictim("Tom", LocalDate.of(2026, 4, 8));
        relation.setPersonTwo(newPersonTwo);
        assertSame(newPersonTwo, relation.getPersonTwo());
    }



    @Test(expected = IllegalArgumentException.class)
    public void testSetPersonOneNullThrowsException() {
        relation.setPersonOne(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetPersonTwoNullThrowsException() {
        relation.setPersonTwo(null);
    }

    @Test
    public void testSetsRelationshipTo() {
        relation.setRelationshipTo("daughter");
        assertEquals("daughter", relation.getRelationshipTo());
    }



    
}
