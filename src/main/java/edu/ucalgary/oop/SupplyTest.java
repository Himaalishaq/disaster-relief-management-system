package edu.ucalgary.oop;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;


import java.time.LocalDate;


public class SupplyTest {
    private Supply perishableSupply;
    private Supply nonPerishableSupply;






    @Before
    public void setUp() {
        perishableSupply = new Supply("food", 5);
        nonPerishableSupply = new Supply("blanket", 5);
    }
    

    @Test
    public void testConstructorSetsType() {
        assertEquals("food", perishableSupply.getType());
    }

    @Test
    public void testConstructorSetsQuantity() {
        assertEquals(5, perishableSupply.getQuantity());
    }


    @Test
    public void testConstructorSetsPerishableTrueForFoodItem() {
        assertTrue(perishableSupply.isPerishable());
    }

    @Test
    public void testConstructorSetsPerishableFalseForBlanket() {
        assertFalse(nonPerishableSupply.isPerishable());
    }

    @Test
    public void testConstructorRecognizesPerishableTypeWithUpperCase() {
        Supply supply = new Supply("Medicine", 1);
        assertTrue(supply.isPerishable());

    }

    @Test
    public void testConstructorAllowsNullType() {
        Supply supply = new Supply(null, 1);
        assertNull(supply.getType());
        assertFalse(supply.isPerishable());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNegativeQuantityThrowsException() {
        new Supply("blanket", -2);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testSetQuantityNegativeThrowsException() {
        perishableSupply.setQuantity(-5);
    }


    @Test
    public void testSetTypeAllowsNull() {
        nonPerishableSupply.setType(null);
        assertNull(nonPerishableSupply.getType());
    }

    @Test
    public void testSetAndGetQuantity() {
        perishableSupply.setQuantity(19);
        assertEquals(19, perishableSupply.getQuantity());
    }
    @Test
    public void testSetQuantityAllowsNoItems() {
        perishableSupply.setQuantity(0);
        assertEquals(0, perishableSupply.getQuantity());
    }



    public void testSetAndGetType() {
        nonPerishableSupply.setType("jacket");
        assertEquals("jacket", nonPerishableSupply.getType());
    }



    public void testDefaultExpirationDateIsNull() {
        assertNull(perishableSupply.getExpirationDate());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetExpirationDateForNonPerishableThrowsException() {
        nonPerishableSupply.setExpirationDate(LocalDate.now().plusDays(1));
    }


    @Test
    public void testSetTypeRecalculatesPerishableToTrue() {
        Supply s = new Supply("blanket", 1);
        s.setType("medicine");
        assertTrue(s.isPerishable());
    }

    @Test
    public void testSetTypeRecalculatesPerishableToFalse() {
        Supply s = new Supply("water", 1);
        s.setExpirationDate(LocalDate.now().plusDays(2));

        s.setType("blanket");

        assertFalse(s.isPerishable());
        assertNull(s.getExpirationDate());
    }   



    @Test
    public void testIsExpiredFalseWhenExpirationDateIsNull() {
        assertFalse(perishableSupply.isExpired());
    }
    
     @Test
    public void testIsExpiredFalseForNonPerishableSupply() {
        assertFalse(nonPerishableSupply.isExpired());
    }
    @Test
    public void testIsExpiredTrueForPastDate() {
        perishableSupply.setExpirationDate(LocalDate.now().minusDays(1));
        assertTrue(perishableSupply.isExpired());
    }

    @Test
    public void testIsExpiredFalseForFutureDate() {
        perishableSupply.setExpirationDate(LocalDate.now().plusDays(5));
        assertFalse(perishableSupply.isExpired());
    }

    @Test
    public void testSetAndGetSupplyId() {
        perishableSupply.setSupplyId(20);
        assertEquals(20, perishableSupply.getSupplyId());
    }

    public void testSetAndGetAllocatedId() {
        perishableSupply.setAllocatedId(10);
        assertEquals(Integer.valueOf(10), perishableSupply.getAllocatedId());
    }

    @Test
    public void testSetAllocatedIdAllowsNull() {
        perishableSupply.setAllocatedId(null);
        assertNull(perishableSupply.getAllocatedId());
    }











    
}
