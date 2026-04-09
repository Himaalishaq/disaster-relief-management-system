package edu.ucalgary.oop;


import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;


import java.time.LocalDate;


public class LocationTest {

    private Location location;
    private DisasterVictim victimOne;
    private DisasterVictim victimTwo;
    private DisasterVictim victimThree;
    private DisasterVictim victimFour;


    private Supply supplyOne;
    private Supply supplyTwo;
    private Supply supplyThree;
    private Supply supplyFour;

    @Before
    public void setUp() {
        location = new Location("Shelter A", "University Station");
        victimOne = new DisasterVictim("Himaal", LocalDate.of(2026, 4, 7));
        victimTwo = new DisasterVictim("Sara", LocalDate.of(2026, 4, 7));
        victimThree = new DisasterVictim("Dila", LocalDate.of(2026, 4, 7));
        victimFour = new DisasterVictim("Sally", LocalDate.of(2026, 4, 7));

        supplyOne = new Supply("blanket", 1);
        supplyTwo = new Supply("medicine", 1);
        supplyThree = new Supply("jacket", 1);
        supplyFour = new Supply("water bottles", 1);

    }

    @Test
    public void testConstructorSetsName() {
        assertEquals("Shelter A", location.getName());
    }

    @Test
    public void testConstructorSetsAddress() {
        assertEquals("University Station", location.getAddress());
    }


    @Test
    public void testConstructorInitializesEmptyOccupants() {
        assertEquals(0, location.getOccupants().length);
    }


    @Test
    public void testConstructorInitializesEmptySupplies() {
        assertEquals(0, location.getSupplies().length);
    }


    @Test
    public void testSetAndGetName() {
        location.setName("Shelter B");
        assertEquals("Shelter B", location.getName());
    }

    @Test
    public void testSetAndGetAddress() {
        location.setAddress("Crowfoot Station");
        assertEquals("Crowfoot Station", location.getAddress());
    }

    @Test
    public void testSetNameAllowsNull() {
        location.setName(null);
        assertNull(location.getName());
    }
    @Test
    public void testSetAddressAllowsNull() {
        location.setAddress(null);
        assertNull(location.getAddress());
    }

    @Test
    public void testSetAndGetLocationId() {
        location.setLocationId(13);
        assertEquals(13, location.getLocationId());
    }

    @Test
    public void testSetOccupants() {
        DisasterVictim[] victims = {victimOne, victimTwo};
        location.setOccupants(victims);
        assertEquals(2, location.getOccupants().length);
        assertSame(victimOne, location.getOccupants()[0]);
        assertSame(victimTwo, location.getOccupants()[1]);

    }

    @Test
    public void testSetSupplies() {
        Supply[] supplies = {supplyOne, supplyTwo};
        location.setSupplies(supplies);
        assertEquals(2, location.getSupplies().length);
        assertSame(supplyOne, location.getSupplies()[0]);
        assertSame(supplyTwo, location.getSupplies()[1]);

    }


    @Test
    public void testSetOccupantsAsNullGivesEmptyArray() {
        location.setOccupants(null);
        assertEquals(0, location.getOccupants().length);
    }

    @Test
    public void testSetSuppliesAsNullGivesEmptyArray() {
        location.setSupplies(null);
        assertEquals(0, location.getSupplies().length);
    }


    @Test
    public void testAddOccupant() {
        location.addOccupant(victimOne);
        assertEquals(1, location.getOccupants().length);
        assertSame(victimOne, location.getOccupants()[0]);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testAddNullOccupantThrowsException() {
        location.addOccupant(null);
    }

    @Test
    public void testRemoveOccupant() {
        location.addOccupant(victimOne);
        location.removeOccupant(victimOne);
        assertEquals(0, location.getOccupants().length);

    }

    @Test
    public void testAddMultipleOccupantsPereservesOrder(){
        location.addOccupant(victimOne);
        location.addOccupant(victimTwo);
        location.addOccupant(victimThree);
        location.addOccupant(victimFour);
        assertEquals(4, location.getOccupants().length);
        assertSame(victimOne, location.getOccupants()[0]);
        assertSame(victimTwo, location.getOccupants()[1]);
        assertSame(victimThree, location.getOccupants()[2]);
        assertSame(victimFour, location.getOccupants()[3]);

    }


    @Test
    public void testRemoveOccupantShiftsArrayCorrectly() {
        location.addOccupant(victimOne);
        location.addOccupant(victimTwo);
        location.addOccupant(victimThree);
        location.addOccupant(victimFour);

        location.removeOccupant(victimThree);

        assertEquals(3, location.getOccupants().length);
        assertSame(victimOne, location.getOccupants()[0]);
        assertSame(victimTwo, location.getOccupants()[1]);
        assertSame(victimFour, location.getOccupants()[2]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveNullOccupantThrowsException() {
        location.removeOccupant(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveOccupantNotFoundThrowsException() {
        location.removeOccupant(victimOne);
    }
   
    

    @Test
    public void testAddSupply() {
        location.addSupply(supplyOne);
        assertEquals(1, location.getSupplies().length);
        assertSame(supplyOne, location.getSupplies()[0]);
    }



    @Test
    public void testAddMultipleSuppliesPereservesOrder(){
        location.addSupply(supplyOne);
        location.addSupply(supplyTwo);
        location.addSupply(supplyThree);
        location.addSupply(supplyFour);
        assertEquals(4, location.getSupplies().length);
        assertSame(supplyOne, location.getSupplies()[0]);
        assertSame(supplyTwo, location.getSupplies()[1]);
        assertSame(supplyThree, location.getSupplies()[2]);
        assertSame(supplyFour, location.getSupplies()[3]);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNullSupplyThrowsException() {
        location.addSupply(null);
    }


    @Test
    public void testRemoveSupply() {
        location.addSupply(supplyOne);
        location.removeSupply(supplyOne);

        assertEquals(0, location.getSupplies().length);
    }

    @Test
    public void testRemoveSupplyShiftsArrayCorrectly() {
        location.addSupply(supplyOne);
        location.addSupply(supplyTwo);
        location.addSupply(supplyThree);
        location.addSupply(supplyFour);

        location.removeSupply(supplyThree);

        assertEquals(3, location.getSupplies().length);
        assertSame(supplyOne, location.getSupplies()[0]);
        assertSame(supplyTwo, location.getSupplies()[1]);
        assertSame(supplyFour, location.getSupplies()[2]);
    }



    @Test(expected = IllegalArgumentException.class)
    public void testRemoveNullSupplyThrowsException() {
        location.removeSupply(null);
    }










}
